/**
 * 
 */
package edu.sollers.javaprog.springtrading.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.sollers.javaprog.springtrading.model.Account;
import edu.sollers.javaprog.springtrading.model.AccountRepository;
import edu.sollers.javaprog.springtrading.model.Position;
import edu.sollers.javaprog.springtrading.model.PositionRepository;

/**
 * @author Karanveer
 *
 */
@RequestMapping("/ClosePosition")
@Controller
public class ClosePositionController {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PositionRepository positionRepository;
	
	@GetMapping
	public ModelAndView doGet(HttpSession session) {
		ModelAndView mv = null;
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		Account account = accountRepository.findById(userId).get();
		List<Position> positions = positionRepository.getPositionsByAccountAndIsOpen(account, true);
				
		Position moneyPosition = positionRepository.findPositionBySymbolAndAccount("MONEY", account);
		boolean isRemoved = positions.remove(moneyPosition);
		System.out.println("Money position removed: " + isRemoved);
		
		mv = new ModelAndView("close_position");
		mv.addObject("positions", positions);
		
		return mv;
	}
	
	
	@PostMapping
	public ModelAndView doPost(@RequestParam int openPosition) {
		ModelAndView mv = new ModelAndView("create_order");
		
		// get position from database with corresponding id
		Position position = positionRepository.findById(openPosition).get();
		
		// add for autofilling create order form
		mv.addObject("position", position);
		
		return mv;
	}
}
