/**
 * 
 */
package edu.sollers.javaprog.springtrading.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.sollers.javaprog.springtrading.model.Login;
import edu.sollers.javaprog.springtrading.model.LoginRepository;

/**
 * 
 * @author rutpatel
 *
 */
@Controller
@RequestMapping("/AddUser")
public class AddUserController {

	@Autowired
	private LoginRepository loginRepository;

	@GetMapping
	public ModelAndView doGet() {
		return new ModelAndView("add_user");
	}

	@PostMapping
	public ModelAndView doPost(@RequestParam String uname, @RequestParam String password, HttpSession session) {
		ModelAndView mv = null;

		// Check for uniqueness
		boolean uniqueUsername = (loginRepository.findByuname(uname) == null) ? true : false;

		if (!uniqueUsername) {
			String errorMessage = "Username " + uname + " is not available";
			mv = new ModelAndView("add_user");
			mv.addObject("errorMessage", errorMessage);
		} else {

			Login login = new Login(uname, password);
			login = loginRepository.save(login);

			session.setAttribute("userId", login.getId());

			mv = new ModelAndView("add_account_info");
		}
		return mv;
	}
}
