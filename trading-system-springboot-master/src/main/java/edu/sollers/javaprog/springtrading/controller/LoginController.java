/**
 * 
 */
package edu.sollers.javaprog.springtrading.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
public class LoginController {

	@Autowired
	private LoginRepository loginRepository;

	@GetMapping("/LoginController")
	public ModelAndView doGet(HttpSession session) {
		System.out.println("Inside get method");
		session.setAttribute("userId", null);
		System.out.println("Set session to 0");
		return new ModelAndView("login");
	}

	/**
	 * The post method processes the form parameters and sets the session if
	 * username/password is valid.
	 * 
	 * @param username
	 * @param password
	 * @param session
	 * @return redirect to either account_home, admin, or back to login
	 */
	@PostMapping("/LoginController")
	public ModelAndView doPost(@RequestParam String username, @RequestParam String password, HttpSession session) {
		System.out.println("Inside post method");
		String un = username;
		String pw = password;

		ModelAndView mv = null;
		boolean loginSuccessful = false;

		if (un.equals("stadmin") && pw.equals("password")) {
			session.setAttribute("userId", 50000);
			mv = new ModelAndView("admin");
		} else {

			Login n = loginRepository.findByuname(username);
			if (n != null) {
				System.out.println("n username = " + n.getUname() + "\nn id = " + n.getId());
				if (n.getPassword().equals(password)) {
					loginSuccessful = true;
				}
			}

			if (loginSuccessful) {
				session.setAttribute("userId", n.getId());
				System.out.println("\nYou've logged in!\n");
				mv = new ModelAndView("account_home");
			} else {
				System.out.println("\nInvalid login!\n");
				String message = "Username or password invalid";

				mv = new ModelAndView("login");
				mv.addObject("errorMessage", message);
			}
		}
		return mv;
	}
}
