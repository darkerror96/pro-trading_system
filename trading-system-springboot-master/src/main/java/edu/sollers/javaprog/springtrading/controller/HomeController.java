package edu.sollers.javaprog.springtrading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * 
 * @author rutpatel
 *
 */
@Controller
public class HomeController {

	@GetMapping("/")
	public String index() {
		System.out.println("Inside HomeController");
		return "index";
	}
}
