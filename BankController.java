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
 * Servlet implementation class BankController
 */
@WebServlet("/BankController")
public class BankController extends TradingSystemServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BankController() {}
        
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Storing Customer Input(Bank Info) values into Database and then Redirecting to add_cc_info.jsp on successful execution of SQL Query
	 * Database Design :- banks (Database Table Name)
	 * 	#	Name	 	Type	
	 *	1	id	 		int(11)-----Primary Key
	 *	2	owner		tinytext		
	 *	3	name		tinytext	
	 *	4	acc_num		tinytext		(NOTE: - Get Account No. as String because of leading zeroes)
	 *	5	routing_num	tinytext		(NOTE: - Get Routing No. as String because of leading zeroes)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Bank b = new Bank(getUserId(request),request.getParameter("b_owner"),request.getParameter("b_name"),request.getParameter("b_accno"),request.getParameter("b_rno"));
		
	    try
	    {
	    	Statement stmt = conn.createStatement();
	        stmt.executeUpdate(b.getInsertStatement());
	    	
	        System.out.println("Bank Info inserted into database...");
	        
	        stmt.close();
	        response.sendRedirect("add_cc_info.jsp");
	     }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
}
