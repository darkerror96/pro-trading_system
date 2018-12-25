package edu.sollers.javaprog.tradingsystem;
/**
 * @author Karanveer
 */

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AddAccountInfo extends TradingSystemServlet.
 * 
 * It is used to process the form submission post request from add_account_info.jsp
 */
@WebServlet("/AddAccountInfo")
public class AddAccountInfo extends TradingSystemServlet {
    private static final long serialVersionUID = 1L;


    /**
     * Default get method
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)

     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * The post method processes the form input from add_account_info.jsp
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Integer userId = getUserId(request);

	System.out.println("User Id in session: " + userId);

	String firstname = request.getParameter("fName");
	String lastname = request.getParameter("lName");
	String birthdate = request.getParameter("dob");
	String social = request.getParameter("ssn");
	String email = request.getParameter("email");
	
	LocalDate dob = LocalDate.parse(birthdate);
	LocalDate now = LocalDate.now();
	
	if (now.minusYears(18).getYear() < dob.getYear()) {
	    deleteUserFromLoginTable(userId);
	    String message = "User is a minor";
	    request.setAttribute("errorMessage", message);
	    request.getRequestDispatcher("/add_user.jsp").forward(request, response);
	}
	else {
	    try {
		Statement stmt = conn.createStatement();
		int affectedrows = stmt.executeUpdate("INSERT INTO accounts (id, first_name, last_name, dob, ssn, email) VALUES (\"" + userId + "\", \""  + firstname + "\", \"" + lastname + "\", \"" + dob + "\", \"" + social + "\", \"" + email + "\");");
		if (affectedrows == 1)
		    System.out.print("Your record has been successfully inserted");
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    response.sendRedirect("/TradingSystem/add_address.jsp");
	}
    }
    
    
    /**
     * Deletes entry from login table corresponding to given user id
     * @param userId Integer
     */
    private void deleteUserFromLoginTable(Integer userId) {
	try {
	    Statement stmt = conn.createStatement();
	    int affectedRows = stmt.executeUpdate("DELETE FROM login WHERE id=\"" + userId + "\";");
	    if (affectedRows == 1) System.out.println("User id: " + userId + " deleted from login");
	    stmt.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

}
