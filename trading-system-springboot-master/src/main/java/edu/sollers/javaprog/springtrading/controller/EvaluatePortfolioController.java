/**
 * 
 */
package edu.sollers.javaprog.springtrading.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.sollers.javaprog.springtrading.model.Account;
import edu.sollers.javaprog.springtrading.model.AccountRepository;
import edu.sollers.javaprog.springtrading.model.Position;
import edu.sollers.javaprog.springtrading.model.PositionRepository;
import edu.sollers.javaprog.springtrading.model.Stock;
import edu.sollers.javaprog.springtrading.model.StockRepository;


/**
 * @author Karanveer
 *
 */
@RequestMapping("/EvaluatePortfolio")
@Controller
public class EvaluatePortfolioController {
	
	@Autowired
	private PositionRepository positionRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private StockRepository stockRepository;
	
	@GetMapping
	public ModelAndView doGet(HttpSession session) {
		ModelAndView mv = null;
		
		Integer userId = (Integer) session.getAttribute(("userId"));
		Account account = accountRepository.findById(userId).get();
		
		List<Position> positions = positionRepository.getPositionsByAccountAndIsOpen(account, true);
		
		// remove money position from list
		Position moneyPosition = positionRepository.findPositionBySymbolAndAccount("MONEY", account);
		positions.remove(moneyPosition);
		
		ArrayList<EvaluatedPosition> ep = new ArrayList<>();
		for (Position p: positions) {
			Stock s = stockRepository.findBySymbol(p.getSymbol());
			ep.add(new EvaluatedPosition(p, s));
		}
		
		mv = new ModelAndView("evaluate_portfolio");
		mv.addObject("positions", ep);
		return mv;
	}
	
	
	
	/**
	 * Inner class - used to create an enumerable object
	 * that contains the derived evaluation to be passed to the view.
	 * @author Karanveer
	 *
	 */
	 class EvaluatedPosition {

		private String symbol;
		private String description;
		private char side;
		private double size;
		private double price;
		private double last;
		private double unrealizedPL;

		/**
		 * @param position
		 * @param stock
		 * @param unrealizedPL
		 */
		public EvaluatedPosition(Position position, Stock stock) {
			symbol = stock.getSymbol();
			description = stock.getFullName();
			last = stock.getLast();
			size = position.getSize();
			side = (position.getSide() == 1) ? 'L' : 'S';
			price = position.getPrice();
			// Derived from position and stock
			this.unrealizedPL = side * size * (last - price);
		}

		/**
		 * @return the symbol
		 */
		public String getSymbol() {
			return symbol;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @return the side
		 */
		public char getSide() {
			return side;
		}

		/**
		 * @return the size
		 */
		public double getSize() {
			return size;
		}

		/**
		 * @return the price
		 */
		public double getPrice() {
			return price;
		}

		/**
		 * @return the last
		 */
		public double getLast() {
			return last;
		}

		/**
		 * @return the unrealizedPL
		 */
		public double getUnrealizedPL() {
			return unrealizedPL;
		}
	}
}
