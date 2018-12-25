package edu.sollers.javaprog.tradingsystem;
/**
 * @author Karanveer
 */

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AddAddress extends TradingSystemServlet.
 * 
 * It is used to process the form submission post request from add_address.jsp
 */
@WebServlet("/AddAddress")
public class AddAddress extends TradingSystemServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default get method
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
	response.getWriter().append("Served at: ").append(request.getContextPath());
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
     * <li>Set userId as session attribute and redirect to add banking information.
     * </ol>
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
	// 1) Get user id from session
	Integer userId = getUserId(request);
	
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
	
	// create address object
	Address address = new Address(street1, street2, city, state, zip);
	
	int addressId = 0 ;
	int mailingAddressId = 0;
	
	try {
	    
	    // Check if the address entered already exists
	    Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery(Address.getAllSelectClause() + address.getComparisonWhereClause());
	    if (rs.next()) {
		
		// address already exists in the table
		// set this account's address_id to the id of this address.
		addressId = rs.getInt("id");
		if (updateAddressIdForAccount(addressId, userId) == 1) 
		    System.out.println("Address already exists in db. Therefore, set this account's address_id to: " + addressId);
	    }
	    else {
		
		// create address row in address table with these parameters
		stmt.executeUpdate(address.getInsertStatement());

		// get address_id of the new row
		rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
		rs.next();
		int lastInsertId = rs.getInt(1);

		// set this account's address_id foreign key to lastInsertId to establish relationship
		addressId = lastInsertId;
		if (updateAddressIdForAccount(addressId, userId) == 1) 
		    System.out.println("Address created in db. Therefore, set this account's address_id to: " + addressId);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}

	// determine if mailing address equals billing
	boolean sameAddress = request.getParameter("sameMailing") != null;

	if (sameAddress) {
	    
	    // set this account's mailing_address_id foreign key to lastInsertId also
	    mailingAddressId = addressId;
	    if (updateMailingAddressIdForAccount(mailingAddressId, userId) == 1) 
		System.out.println("Mailing address same as Address. Therefore, set this account's mailing_address_id to: " + mailingAddressId);
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
	    
	    // Create mailingAddress object from fields
	    Address mailingAddress = new Address(street1, street2, city, state, zip);

	    try {

		// Check if the address entered already exists
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(Address.getAllSelectClause() + mailingAddress.getComparisonWhereClause());
		if (rs.next()) {
		    
		    // address already exists in the table
		    // set this account's address_id to the id of this address.
		    mailingAddressId = rs.getInt("id");
		    if(updateMailingAddressIdForAccount(mailingAddressId, userId) == 1)
			System.out.println("Mailing address already exists in table. Therefore, set this account's mailing_address_id to: " + mailingAddressId);
		}
		else {
		    
		    // create address row in address table with these parameters
		    stmt.executeUpdate(mailingAddress.getInsertStatement());

		    // get address_id of the new row
		    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
		    rs.next();
		    int lastInsertId = rs.getInt(1);

		    // set this account's mailing_address_id foreign key to lastInsertId to establish relationship
		    mailingAddressId = lastInsertId;
		    if (updateMailingAddressIdForAccount(mailingAddressId, userId) == 1)
			System.out.println("Mailing address created in table. Therefore, set this account's mailing_address_id to: " + mailingAddressId);
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
	
	
	// redirect to next page
	response.sendRedirect("/TradingSystem/add_bank_info.jsp");
    }
    
    
    private int updateAddressIdForAccount(int address_id, Integer userId) {
	int affectedRow = 0;
	try {
	    Statement stmt = conn.createStatement();
	    affectedRow = stmt.executeUpdate("UPDATE accounts SET address_id =" + address_id + " WHERE id=" + userId + ";");
	    stmt.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return affectedRow;
    }
    
    private int updateMailingAddressIdForAccount(int mailing_address_id, Integer userId) {
	int affectedRow = 0;
	try {
	    Statement stmt = conn.createStatement();
	    affectedRow = stmt.executeUpdate("UPDATE accounts SET mailing_address_id =" + mailing_address_id + " WHERE id=" + userId + ";");
	    stmt.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return affectedRow;
    }

}
