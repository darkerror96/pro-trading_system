package edu.sollers.javaprog.tradingsystem;
/**
 * @author Karanveer
 */


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.sollers.javaprog.tradingsystem.Order.OrderType;
import edu.sollers.javaprog.tradingsystem.Order.PriceType;
import edu.sollers.javaprog.tradingsystem.Order.TimeInForce;

/**
 * CreateOrder extends TradingSystemServlet.
 * 
 * It is used to create an order. The get method
 * displays an html page where the user can choose
 * whether to create a new order or close an existing position.
 * 
 * The post method is used to process the submission of 
 * create_order.jsp and create an order.
 */
@WebServlet("/CreateOrder")
public class CreateOrder extends TradingSystemServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateOrder() {
	super();
    }
    

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Integer userId = getUserId(request);
	
	// check if account has a MONEY position
	Position moneyPosition = getMoneyPosition(userId);
	if (moneyPosition == null) {
	    request.setAttribute("errorMessage", "Must add funds before creating order");
	    request.getRequestDispatcher("account_home.jsp").forward(request, response);
	}
	
	response.setContentType("text/html");
	PrintWriter writer = response.getWriter();
	writer.println("<!DOCTYPE html>");
	writer.println("<html>");
	writer.println("<head>");
	writer.println("<title>" + "Sollers Trading System - Create Order" + "</title></head>");
	writer.println(getCSS());
	writer.println("<body>");
	writer.println("Current session user id: " + userId);
	writer.println("<h3><a href=\"create_order.jsp\">New Order</a></h3>");
	writer.println("<h3><a href=\"ClosePosition\">Close Existing Position</a></h3>");
	writer.println("<br><br>");
	writer.println("<ul class=\"navList\">");
	writer.println("<li class=\"navItem\"><a href=\"account_home.jsp\">Home</a></li>");
	writer.println("</ul");
	writer.println("</body>");
	writer.println("</html>");
    }
    

    /**
     * Algorithm:
     * 
     * For user in session
     * 
     * 1) Get form parameters
     * 
     * 2) Ensure that order is for a valid stock symbol
     * (stock symbol exists in stocks table)
     * 
     * 3) Ensure that symbol, side, and size are unique for this account's positions
     * 
     * 4) Ensure there are sufficient funds in account for buy/buy to cover order
     * 
     * 5) Ensure that order is not a duplicate order 
     * (side, symbol combination is unique for this account's positions)
     * 
     * 6) Ensure that value of an order to close a position is less than $25,000
     * 
     * If this is a limit buy order, limit price must be less than last price
     * If this is a limit sell order, limit price must be greater than last price
     * If this is a stop buy order, stop price must be higher than last price
     * If this is a stop sell order, stop price must be lower than last price
     * 
     * If price validation failse, user is alerted. 
     * 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// Get userid from session
	Integer userId = getUserId(request);
	
	// 1) Get form parameters
	OrderType orderType = OrderType.valueOf(request.getParameter("orderType"));
	PriceType priceType = PriceType.valueOf(request.getParameter("priceType"));
	TimeInForce timeInForce = TimeInForce.valueOf(request.getParameter("duration"));
	String symbol = request.getParameter("symbol");
	double size = Double.parseDouble(request.getParameter("quantity"));
	
	double stopLevel = 0;
	// if not market order, parse stopPrice value
	if (!priceType.equals(PriceType.MARKET)) {
	    stopLevel = Double.parseDouble(request.getParameter("stopPrice"));
	}
	
	if (userId == null) {
	    System.out.println("User id is null");
	}
	
	// Create order object from form parameters
	Order order = new Order(orderType, priceType, timeInForce, userId, symbol, size, stopLevel);
	
	ArrayList<String> errorMessage = new ArrayList<>();

	Stock stock = getStock(order.getSymbol());
	double orderCost = 0;
	
	// 2) Ensure that order is for a valid stock symbol
	if (stock == null) {
	    errorMessage.add("Invalid stock symbol");
	}
	else {
	    orderCost = stock.getLast() * order.getSize();

	    // 3) Ensure that symbol, side, and size are unique for this account's positions
	    ArrayList<Position> positions = getOpenPositionsForAccount(userId);

	    for (Position p: positions) {
		if (order.getSymbol().equals(p.getSymbol())) {

		    // get appropriate side according to order type
		    int orderSide = 0;
		    if (order.getOrderType() == OrderType.BUY || order.getOrderType() == OrderType.SELL_SHORT) {
			orderSide = (order.getOrderType() == OrderType.BUY) ? 1 : -1;
		    }

		    // compare order's side with existing positions's side
		    if (orderSide == p.getSide()) {
			errorMessage.add("Order matches existing position: order invalid"); 
			break;
		    }
		}
	    }
	    
	    // 4) Ensure there are sufficient funds in account for buy/buy to cover order
	    Position moneyPosition = getMoneyPosition(userId);
	    
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
	    try {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM orders WHERE account_id=\"" + userId + "\" AND order_status=\"PENDING\";");
		while (rs.next()) {
		    String existing_symbol = rs.getString("symbol");
		    OrderType existing_ot  = OrderType.valueOf(rs.getString("order_type"));
		    
		    if (existing_symbol.equals(order.getSymbol()) && existing_ot.equals(order.getOrderType())) {
			errorMessage.add("Existing open order of the same type for this symbol: order invalid");
			break;
		    }
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    }

	    // 6) If order is to close an existing position, ensure that total position cash value is less than 25000.
	    if (order.getOrderType() == OrderType.SELL || order.getOrderType() == OrderType.BUY_TO_COVER) {
		if (orderCost > 25000) {
		    errorMessage.add("Cannot close a position of value greater than $25000");
		}
	    }
	    
	    // add order to appropriate queue if no errors
	    if (errorMessage.isEmpty()) {		
		if (order.getPriceType() != PriceType.MARKET) {
		    addPendingOrder(order); // add to pendingOrder queue
		}
		else {
		    addMarketOrder(order); // add to market order queue 
		}
		
		// Add order to 'orders' table in database
		int updateQuery = insertOrderRecord(order);
		if (updateQuery == 1) {
		    System.out.println("Successfully inserted order into orders table");
		}
	    } // end if where error_message.isEmpty()
	} // end else where stock is not null
	
	if (!errorMessage.isEmpty()) {
	    request.setAttribute("errorMessage", errorMessage);
	    request.getRequestDispatcher("/create_order.jsp").forward(request, response);
	}
	else {
	    request.setAttribute("successMessage", "Order created successfully");
	    request.getRequestDispatcher("/account_home.jsp").forward(request, response);
	}
    }
    
    /**
     * Method to insert newly created order into database table
     * @param order
     * @return int value 0 if nothing was inserted or 1 if successfully inserted
     */
    private int insertOrderRecord(Order order) {
	int num = 0;
	try {
	    Statement stmt = conn.createStatement();
	     num = stmt.executeUpdate("INSERT INTO orders "
	     	+ "(order_type, price_type, time_in_force, order_status, account_id, symbol, size, stop_price, creation_date)"
		+ " VALUES ("
	     	+ "\"" + order.getOrderType() + "\", "
	     	+ "\"" + order.getPriceType() + "\", "
	     	+ "\"" + order.getTimeInForce() + "\", "
	     	+ "\"" + order.getStatus() +  "\", "
	     	+ order.getAccountId() + ", "
	     	+ "\"" + order.getSymbol() + "\", "
	     	+ order.getSize() + ", "
	     	+ order.getStopPrice() + ", "
	     	+ "\"" + new SimpleDateFormat("yyyy-MM-dd").format(order.getCreationDateTime()) + "\");"
		);
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}
	return num;
    }
    
    /**
     * Method to add order object to market order queue
     * @param order
     */
    private void addMarketOrder(Order order) {
	ServletContext sc = getServletContext();
	@SuppressWarnings("unchecked")
	Vector<Order> marketOrderQueue = (Vector<Order>)sc.getAttribute("marketOrderQueue");
	if(marketOrderQueue == null) {
	    System.out.println("Error: market order queue is null");
	}
	synchronized(Thread.currentThread()) {
	    marketOrderQueue.add(order);
	    System.out.println("\n" + order.toString() + "\nadded to market queue \n");
	}
    }

    /**
     * Method to add order object to pending order queue
     * @param order
     */
    private void addPendingOrder(Order order) {
	Hashtable<Integer, Order> pendingOrders = getPendingOrders();
	String poStatus = (pendingOrders == null) ? "null" : "not null";
	System.out.println("pendingOrders is " + poStatus);
	if (pendingOrders != null) {
	    pendingOrders.put(order.getOrderID(), order);
	    System.out.println("\n" + order.toString() + "\nadded to pending orders \n");
	}
    }
}
