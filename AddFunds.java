package edu.sollers.javaprog.tradingsystem;
/**
 * @author KK
 */

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddFunds
 */
@WebServlet(description = "This method will add funds to user account", urlPatterns = { "/AddFunds" })
public class AddFunds extends TradingSystemServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddFunds() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fundMethod = request.getParameter("fundSource");
		response.getWriter().print(" " + fundMethod);
		Integer fundAmt = Integer.parseInt(request.getParameter("amount"));
		response.getWriter().print(" " + fundAmt);
		Integer userId = getUserId(request);
		response.getWriter().print(" " + userId);
		System.out.println(fundMethod + " " + fundAmt + " " + userId);
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT side, size FROM positions WHERE account_id = " + userId 
					+ " AND symbol = 'MONEY';");
			if (rs.next()) {
				System.out.println("MONEY position exists");
				stmt.executeUpdate("UPDATE positions SET size = size + " + fundAmt + 
						" WHERE account_id = " + userId + " AND symbol = 'MONEY';");
			} 
			else {
				System.out.println("MONEY position exists");
				stmt.executeUpdate("INSERT INTO positions " +
			"(account_id, side, size, price, symbol, is_open, creation_date) " +
						"VALUES (" + userId + ", 1, " + fundAmt + ", 1.0, 'MONEY', 1, '" + fmt.format(new Date()) + "');");
			}
			stmt.close();
			response.sendRedirect("account_home.jsp");
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}
	}
}
