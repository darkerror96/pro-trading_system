/**
 * 
 */
package edu.sollers.javaprog.springtrading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * This controller's get method routes to 
 * the view where a user can choose create a new order
 * or close an existing position, or return back to account home. 
 * 
 * @author Karanveer
 *
 */
@RequestMapping("/NewOrder")
@Controller
public class NewOrderController {
	
	@GetMapping
	public String doGet() {
		return "new_order";
	}
}
