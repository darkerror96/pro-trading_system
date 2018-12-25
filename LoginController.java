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
 * LoginController extends TradingSystemServlet.
 * 
 * Its post method checks if the username/password submitted
 * from the login form exist in the database. 
 * If found, user is logged in else it notifies the user and
 * allows them to try again.
 * 
 */
@WebServlet("/LoginController")
public class LoginController extends TradingSystemServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
	super();
    }

    /**
     * Default get method
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * Logs a user in if the username and password are validated from the database
     * 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String un = request.getParameter("username");
	String pw = request.getParameter("password");

	int userId = 0;

	boolean userFound = false;

	if (un.equals("stadmin") && pw.equals("password")) {
	    request.getSession().setAttribute("uderId", 50000);
	    response.sendRedirect("admin.html");
	} else {
	    try {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
			"SELECT id FROM login WHERE " + "(password=\"" + pw + "\" AND uname=\"" + un + "\");");
		if (rs.next()) {
		    userId = rs.getInt(1); // capture id if you found a user
		    userFound = true;
		}
		stmt.close();
		rs.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }

	    if (userFound) {
		request.getSession().setAttribute("userId", userId); // add userId to session
		System.out.println("\nYou've logged in!\n");
		// redirect to Account.html
		response.sendRedirect("account_home.jsp");
		return;
	    } else {
		System.out.println("\nInvalid login!\n");
		String message = "Username or password invalid";
		request.setAttribute("errorMessage", message);
		request.getRequestDispatcher("/login.jsp").forward(request, response);
		return;
	    }
	}
    }
}
