package edu.sollers.javaprog.springtrading.controller;
/**
 * @author Karanveer
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.sollers.javaprog.springtrading.model.Account;
import edu.sollers.javaprog.springtrading.model.AccountRepository;
import edu.sollers.javaprog.springtrading.model.Address;
import edu.sollers.javaprog.springtrading.model.AddressRepository;


@RequestMapping("/AddAddress")
@Controller
public class AddAddressController {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private AccountRepository accountRepository;
	
	/**
	 * Get request handler redirects to view
	 * @return 
	 */
	@GetMapping
	public ModelAndView doGet() {
		return new ModelAndView("add_address");
	}


	/**
	 * Requires a session attribute "userId" to exist in current session
	 * 
	 * <br>
	 * The algorithm is:
	 * <ol>
	 * <li>Get session attribute "user_id"</li>
	 * <li>Get address form parameters <br> 
	 * 		<ul><li>if address already exists in table, set that address's id to account's address</li>
	 * 		<li>else create new address and set that address id to account's address</li>
	 * 		</ul>
	 * </li>
	 * <li>Check if mailing address is same as address
	 * 		<ul>
	 * 		<li>if so, set that address id to account's mailing address</li>
	 * 		<li>else get mailing address parameters
	 * 			<ul>
	 * 				<li>if mailing address already exists in table, set that address id to account's mailing address</li>
	 * 				<li>else create new address and set that address id to account's mailing address</li> 
	 * 			</ul>
	 * 		</li>
	 * 		</ul>
	 * </li>
	 * <li> Redirect to next view </li>
	 * </ol>
	 * 
	 * @param request The request object containing the form parameters
	 * @param session The session from which to get the user id
	 * @return redirects to add_bank_info view
	 */
	@PostMapping
	public ModelAndView doPost(HttpServletRequest request, HttpSession session) {
		
		// I'm using the HttpServletRequest parameter because
		// of the large number of form parameters. 

		// 1) Get userId from session and get corresponding account entity
		Integer userId = (Integer) session.getAttribute("userId");
		Account account = accountRepository.findById(userId).get();


		// 2) Process form parameters for address	
		String street1 = request.getParameter("line1");
		String street2 = (request.getParameter("line2").length() == 0) ? null : request.getParameter("line2"); // if empty string, set to null
		String city    = request.getParameter("city");
		String state   = request.getParameter("state");
		String zip     = request.getParameter("zip");
		// append zip4 to zip if it is is not empty
		if (request.getParameter("zip4").length() != 0) {
			zip += "-" + request.getParameter("zip4");
		}

		Address address = addressRepository.findByStreet1AndStreet2AndCityAndZip(street1, street2, city, zip);

		if (address != null) {
			account.setAddress(address);
			System.out.println("Address already exists in table. Set this account's address_id to: " + address.getId());

		}
		else {
			// add address to table
			address = new Address(street1, street2, city, state, zip);
			address = addressRepository.save(address);

			account.setAddress(address);
			System.out.println("Address created in table. Set this account's address_id to: " + address.getId());
		}


		// determine if mailing address equals billing
		boolean sameAddress = request.getParameter("sameMailing") != null;

		if (sameAddress) {
			// set account's mailing address to address
			account.setMailingAddress(address);
			System.out.println("Mailing address same as Address. Set mailing_address_id to: " + address.getId());
		}
		else {
			
			// process mailing address parameters
			String poBox = (request.getParameter("mPOBoxNum").length() == 0) ? null : request.getParameter("mPOBoxNum");
			if (poBox != null) {
				street1 = "PO Box " + poBox; // format street1 for PO Box number
				street2 = null;
			}
			else {
				street1 = request.getParameter("mLine1");
				street2 = (request.getParameter("mLine2").length() == 0) ? null : request.getParameter("mLine2"); // if empty string, set to null
			}
			city    = request.getParameter("mCity");
			state   = request.getParameter("mState");
			zip     = request.getParameter("mZip");
			// append zip4 to zip if it is is not empty
			if (request.getParameter("mZip4").length() != 0) {
				zip += "-" + request.getParameter("mZip4");
			}
			
			address = addressRepository.findByStreet1AndStreet2AndCityAndZip(street1, street2, city, zip);
			
			if (address != null) {
				account.setMailingAddress(address);
				System.out.println("Mailing address already exists in table. Set mailing_address_id to: " + address.getId());
			}
			else {
				// Create address entity
				address = new Address(street1, street2, city, state, zip);
				address = addressRepository.save(address);
				
				account.setMailingAddress(address);
				System.out.println("Mailing address created in table. Set mailing_address_id to: " + address.getId());
			}
		}
		
		// Update account
		// If the account object has an id, it will update
		// Else it will create
		accountRepository.save(account);
		
		// Redirect to next page
		return new ModelAndView("add_bank_info") ;
	}
}
