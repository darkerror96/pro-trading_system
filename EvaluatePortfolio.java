package edu.sollers.javaprog.tradingsystem;
/**
 * @author Karanveer
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * EvaluatePortfolio extends TradingSystemServlet.
 * 
 * Its get method displays an html page showing the open positions for the user
 * in session
 */
@WebServlet("/EvaluatePortfolio")
public class EvaluatePortfolio extends TradingSystemServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EvaluatePortfolio() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Integer userId = getUserId(request);
	
	ArrayList<Position> positions = getOpenPositionsForAccount(userId);
	
	response.setContentType("text/html");
	PrintWriter writer = response.getWriter();
	writer.println("<!DOCTYPE html>");
	writer.println("<html>");
	writer.println("<head>");
	writer.println("<title>" + "Sollers Trading System - Evaluated Portfolio" + "</title></head>");
	writer.println(getCSS());
	writer.println("<body>");
	writer.println("<h2>Evaluated Portfolio</h2>");
	
	if (positions.isEmpty()) {
	    writer.println("<p>No open positions to evaluate for account.</p>");
	}
	else {
	    
	    // build table
	    writer.println("<div class=\"rTable\">");
	    writer.println("<div class=\"rTableRow\">");
	    writer.println("<div class=\"rTableHead\"><strong>Symbol</strong></div>");
	    writer.println("<div class=\"rTableHead\"><strong>Description</strong></div>");
	    writer.println("<div class=\"rTableHead\"><strong>Side</strong></div>");
	    writer.println("<div class=\"rTableHead\"><strong>Quantity</strong></div>");
	    writer.println("<div class=\"rTableHead\"><strong>Trade Price</strong></div>");
	    writer.println("<div class=\"rTableHead\"><strong>Current Price</strong></div>");
	    writer.println("<div class=\"rTableHead\"><strong>Unrealized P&amp;L</strong></div>");
	    writer.println("</div>");

	    for (Position p: positions) {
		Stock stock = getStock(p.getSymbol());

		// position.side * position.size * stock.last
		double currentPrice = stock.getLast();

		// position.size * (position.price - stock.last)
		double unrealizedPL = p.getSide() * p.getSize() * (currentPrice - p.getPrice());

		writer.println("<div class=\"rTableRow\">");
		writer.println("<div class=\"rTableCell\">" + p.getSymbol() + "</div>");
		writer.println("<div class=\"rTableCell\">" + stock.getFullName() + "</div>");
		writer.println("<div class=\"rTableCell\">" + ((p.getSide() == 1) ? 'L' : 'S') + "</div>");
		writer.println("<div class=\"rTableCell\">" + p.getSize() + "</div>");
		writer.println("<div class=\"rTableCell\">" + p.getPrice() + "</div>");
		writer.println("<div class=\"rTableCell\">" + currentPrice + "</div>");
		writer.println("<div class=\"rTableCell\">" + unrealizedPL + "</div>");
		writer.println("</div>");
	    }
	    writer.println("</div>");
	} // end else
	
	writer.println("<br><br>");
	writer.println("<ul class=\"navList\">");
	writer.println("<li class=\"navItem\"><a href=\"account_home.jsp\">Home</a></li>");
	writer.println("</ul");
	
	writer.println("</body>");
	writer.println("</html>");
    }

    /**
     * Default pose method
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
    }

}
