/**
 * 
 */
package edu.sollers.javaprog.springtrading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.sollers.javaprog.springtrading.model.Stock;
import edu.sollers.javaprog.springtrading.model.StockRepository;

/**
 * @author Karanveer
 *
 */
@RequestMapping("/ViewStocks")
@Controller
public class ViewStocksController {

	@Autowired
	private StockRepository stockRepository;
	
	@GetMapping
	public ModelAndView doGet() {
		ModelAndView mv = new ModelAndView("view_stocks");
		
		List<Stock> stocks = (List<Stock>) stockRepository.findAll();
		mv.addObject("stocks", stocks);
		
		return mv;
	}
}
