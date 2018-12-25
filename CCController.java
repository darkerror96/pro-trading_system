package edu.sollers.javaprog.tradingsystem;


/**
 * 
 * @author rutpatel
 *
 */

import java.io.IOException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CCController
 */
@WebServlet("/CCController")
public class CCController extends TradingSystemServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CCController() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Storing Customer Input(Credit Card Info) values into Database and then Redirecting to another page on successful execution of SQL Query.
	 * Database Design :- credit_cards (Database Table Name)
	 * #	Name		Type	
	 * 1	id			int(11)---------Primary Key
	 * 2	owner		tinytext	
	 * 3	cc_type		tinytext	
	 * 4	number		tinytext			(NOTE: - Get Credit Card No. as String because of leading zeroes)
	 * 5	exp_month	tinyint(2)			
	 * 6	exp_year	smallint(4)			
	 * 7	cvv			tinytext
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CreditCard cc = new CreditCard(getUserId(request),request.getParameter("c_owner"),request.getParameter("c_type"),request.getParameter("c_ccno"),Integer.parseInt(request.getParameter("c_expm")),Integer.parseInt(request.getParameter("c_expy")),request.getParameter("c_cvvno"));
		
	    try
	    {
	    	Statement stmt = conn.createStatement();
	        stmt.executeUpdate(cc.getInsertStatement());
	        
	        System.out.println("Credit Card Info inserted into database...");
	        
	        stmt.close();
	        response.sendRedirect("account_home.jsp");
	     }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
}
