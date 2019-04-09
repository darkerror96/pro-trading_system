/**
 * 
 */
package edu.sollers.javaprog.springtrading.controller;

import java.time.LocalDate;

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
import edu.sollers.javaprog.springtrading.model.LoginRepository;

/**
 * 
 * @author rutpatel
 *
 */
@RequestMapping("/AddAccountInfo")
@Controller
public class AddAccountInfoController {

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private AccountRepository accountRepository;

	@GetMapping
	public ModelAndView doGet() {
		return new ModelAndView("add_account_info");
	}

	@PostMapping
	public ModelAndView doPost(@RequestParam String fName, @RequestParam String lName, @RequestParam String dob,
			@RequestParam String email, @RequestParam String ssn, HttpSession session) {

		ModelAndView mv = null;
		Integer userId = (Integer) session.getAttribute("userId");

		LocalDate birthDate = LocalDate.parse(dob);
		LocalDate now = LocalDate.now();

		if (now.minusYears(18).getYear() < birthDate.getYear()) {
			loginRepository.deleteById(userId);

			String message = "User is a minor";
			mv = new ModelAndView("add_user");
			mv.addObject("errorMessage", message);
		} else {
			Account account = new Account(userId, fName, lName, dob, ssn, email);
			account = accountRepository.save(account);

			mv = new ModelAndView("add_address");
		}
		return mv;
	}
}
