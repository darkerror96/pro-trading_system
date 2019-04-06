/**
 * 
 */
package edu.sollers.javaprog.springtrading.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Karanveer
 *
 */
@RequestMapping("/AccountHome")
@Controller
public class AccountHomeController {
	
	@GetMapping
	public ModelAndView doGet(HttpSession session) {
		ModelAndView mv = null;
		if (session.getAttribute("userId") != null) {
			mv = new ModelAndView("account_home");
		}
		else
			mv = new ModelAndView("login");
		
		return mv;
	}
}
