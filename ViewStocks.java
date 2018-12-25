package edu.sollers.javaprog.tradingsystem;
/**
 * @author Karanveer
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ViewStocks extends TradingSystemServlet.
 * 
 * Its get method displays an html/css table
 * with all stock values from the database
 */
@WebServlet("/ViewStocks")
public class ViewStocks extends TradingSystemServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewStocks() {
	super();
	// TODO Auto-generated constructor stub
    }
    
    /**
     * Method to build array list of all stocks from stocks table
     * @return array list of all stocks
     * @author Karanveer
     */
    private ArrayList<Stock> getAllStocks() {
	ArrayList<Stock> stocks = new ArrayList<>();
	try {
	    Statement stmt = conn.createStatement();
	    ResultSet rs   = stmt.executeQuery(Stock.getSelectClause());
	    while (rs.next()) {
		Stock currentStock = new Stock(rs);
		stocks.add(currentStock);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return stocks;
    }

    /**
     * The get method prints prints all stocks from the stocks table
     * formatted as an html/css table. 
     * Refresh the page to get the latest values
     * @author Karanveer
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setContentType("text/html");
	PrintWriter writer = response.getWriter();
	writer.println("<!DOCTYPE html>");
	writer.println("<html>");
	writer.println("<head>");
	writer.println("<title>" + "Sollers Trading System - View Stocks" + "</title></head>");
	writer.println(getCSS());
	writer.println("<body>");
	writer.println("<h2>Stocks</h2>");


	writer.println("<div class=\"rTable\">");
	writer.println("<div class=\"rTableRow\">");
	writer.println("<div class=\"rTableHead\"><strong>Symbol</strong></div>");
	writer.println("<div class=\"rTableHead\"><strong>Description</strong></div>");
	writer.println("<div class=\"rTableHead\"><strong>Bid</strong></div>");
	writer.println("<div class=\"rTableHead\"><strong>Ask</strong></div>");
	writer.println("<div class=\"rTableHead\"><strong>Last</strong></div>");
	writer.println("</div>");
	
	ArrayList<Stock> stocks = getAllStocks();
	
	for (Stock s: stocks) {
	    writer.println("<div class=\"rTableRow\">");
	    writer.println("<div class=\"rTableCell\">" + s.getTicker() + "</div>");
	    writer.println("<div class=\"rTableCell\">" + s.getFullName() + "</div>");
	    writer.println("<div class=\"rTableCell\">$" + s.getBid() + "</div>");
	    writer.println("<div class=\"rTableCell\">$" + s.getAsk() + "</div>");
	    writer.println("<div class=\"rTableCell\">$" + s.getAsk() + "</div>");
	    writer.println("</div>");
	}
	
	writer.println("</div>");
	writer.println("<br><br>");
	writer.println("<ul class=\"navList\">");
	writer.println("<li class=\"navItem\"><a href=\"account_home.jsp\">Home</a></li>");
	writer.println("<li class=\"navItem\"><a href=\"CreateOrder\">Create Order</a></li>");
	writer.println("</ul");
	writer.println("</body>");
	writer.println("</html>");
    }

    /**
     * Default post method
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	doGet(request, response);
    }

}
