/**
 * 
 */
package edu.sollers.javaprog.springtrading.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.sollers.javaprog.springtrading.config.TrdSysDaemon;
import edu.sollers.javaprog.springtrading.model.Account;
import edu.sollers.javaprog.springtrading.model.AccountRepository;
import edu.sollers.javaprog.springtrading.model.Order;
import edu.sollers.javaprog.springtrading.model.OrderRepository;
import edu.sollers.javaprog.springtrading.model.Position;
import edu.sollers.javaprog.springtrading.model.PositionRepository;
import edu.sollers.javaprog.springtrading.model.Stock;
import edu.sollers.javaprog.springtrading.model.StockRepository;
import edu.sollers.javaprog.springtrading.model.Order.OrderStatus;
import edu.sollers.javaprog.springtrading.model.Order.OrderType;
import edu.sollers.javaprog.springtrading.model.Order.PriceType;
import edu.sollers.javaprog.springtrading.model.Order.TimeInForce;

/**
 * @author Karanveer
 *
 */
@RequestMapping("/CreateOrder")
@Component
public class CreateOrderController {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private TrdSysDaemon trdSysDaemon;


	@GetMapping
	public ModelAndView doGet(@RequestParam(required = false) Position position) {
		ModelAndView mv = new ModelAndView("create_order");

		// check if a position object was passed with the request
		// If so, add them to 'mv' for the view to autofill the order values. 

		if (position != null) {
			System.out.println(position == null);
			mv.addObject("position", position);
		}

		return new ModelAndView("create_order");
	}

	@PostMapping
	public ModelAndView doPost(HttpServletRequest request, HttpSession session) {
		ModelAndView mv = null;


		// Get userid from session
		Integer userId = (Integer) session.getAttribute("userId");

		Account account = accountRepository.findById(userId).get();


		// 1) Get form parameters
		OrderType orderType = OrderType.valueOf(request.getParameter("orderType"));
		PriceType priceType = PriceType.valueOf(request.getParameter("priceType"));
		TimeInForce timeInForce = TimeInForce.valueOf(request.getParameter("duration"));
		String symbol = request.getParameter("symbol");
		Double size = Double.parseDouble(request.getParameter("quantity"));

		Double stopLevel = 0.0;
		// if not market order, parse stopPrice value
		if (!priceType.equals(PriceType.MARKET)) {
			stopLevel = Double.parseDouble(request.getParameter("stopPrice"));
		}

		// Create order object from form parameters
		Order order = new Order(orderType, priceType, timeInForce, account, symbol, size, stopLevel);

		ArrayList<String> errorMessage = new ArrayList<>();

		Stock stock = stockRepository.findBySymbol(order.getSymbol());
		double orderCost = 0;

		// 2) Ensure that order is for a valid stock symbol
		if (stock == null) {
			errorMessage.add("Invalid stock symbol");
		}
		else {
			orderCost = stock.getLast() * order.getSize();

			// 3) Ensure that symbol, side, and size are unique for this account's positions
			List<Position> positions = positionRepository.getPositionsByAccountAndIsOpen(account, true);

			for (Position p : positions) {
				if (order.getSymbol().equals(p.getSymbol())) {
					// get appropriate side according to order type
					int orderSide = 0;
					if (order.getOrderType() == OrderType.BUY)
						orderSide = 1;
					else if (order.getOrderType() == OrderType.SELL_SHORT)
						orderSide = -1;

					// compare order's side with existing positions's side
					if (orderSide == p.getSide()) {
						errorMessage.add("Order matches existing position: order invalid"); 
						break;
					}

				}
			}

			// 4) Ensure there are sufficient funds in account for buy/buy to cover order
			Position moneyPosition = positionRepository.findPositionBySymbolAndAccount("MONEY", account);


			if (order.getOrderType().equals(OrderType.BUY) || order.getOrderType().equals(OrderType.BUY_TO_COVER)) {
				if (moneyPosition == null) {
					errorMessage.add("No funds in account");
				}
				else if (moneyPosition.getSize() < orderCost) {
					errorMessage.add("Insufficient funds for order");
				}
			}


			// 5) Ensure that order is not a duplicate order 
			// If existing open order's symbol, ordertype match, then deny this order. 
			List<Order> accountOrders = orderRepository.getOrderByAccountAndOrderStatus(account, OrderStatus.PENDING);
			for(Order o: accountOrders) {
				if (o.getSymbol().equals(order.getSymbol()) && o.getOrderType().equals(order.getOrderType())) {
					errorMessage.add("Existing open order of the same type for this symbol: order invalid");
					break;
				}
			}


			// 6) If order is to close an existing position, ensure that total position cash value is less than 25000.
			if (order.getOrderType() == OrderType.SELL || order.getOrderType() == OrderType.BUY_TO_COVER) {
				if (orderCost > 25000) {
					errorMessage.add("Cannot close a position of value greater than $25000");
				}
			}

			// add order to appropriate queue if no errors
			if (errorMessage.isEmpty()) {

				// Add order to 'orders' table in database
				order = orderRepository.save(order);
				System.out.println("Saved order in table. Row id: " + order.getId());
				
				if (order.getPriceType() != PriceType.MARKET) {
					addPendingOrder(order); // add to pendingOrder queue
				}
				else {
					addMarketOrder(order); // add to market order queue 
				}


			}
		} // end else where stock is not null

		if (!errorMessage.isEmpty()) {
			mv = new ModelAndView("create_order"); // redirect back to create order
			mv.addObject("errorMessage", errorMessage);
		}
		else {
			mv = new ModelAndView("account_home");
			mv.addObject("successMessage", "Order created successfully");
		}
		return mv;
	}


	/**
	 * Method to add order object to market order queue
	 * @param order
	 */
	private void addMarketOrder(Order order) {
		Vector<Order> marketOrderQueue = trdSysDaemon.getMarketOrders();
		if(marketOrderQueue == null) {
			System.out.println("Error: market order queue is null");
		}
		synchronized(Thread.currentThread()) {
			marketOrderQueue.add(order);
			System.out.println("Market Order Queue: ");
			System.out.println(marketOrderQueue.toString() + "\n");
		}
	}

	/**
	 * Method to add order object to pending order queue
	 * @param order
	 */
	private void addPendingOrder(Order order) {
		Hashtable<Integer, Order> pendingOrders = trdSysDaemon.getPendingOrders();
		String poStatus = (pendingOrders == null) ? "null" : "not null";
		System.out.println("pendingOrders is " + poStatus);
		if (pendingOrders != null) {
			pendingOrders.put(order.getId(), order);
			System.out.println("Pending Order Hashtable: ");
			System.out.println(pendingOrders.toString() + "\n");
		}
	}



}
