/**
 * 
 */
package edu.sollers.javaprog.springtrading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author rutpatel
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
