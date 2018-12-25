package edu.sollers.javaprog.tradingsystem;
/**
 * @author Karanveer
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClosePosition extends TradingSystemServlet.
 * 
 * It is used to display open positions and allow user to select
 * the one to close as an order by redirecting to create_order.jsp
 */
@WebServlet("/ClosePosition")
public class ClosePosition extends TradingSystemServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClosePosition() {
	super();
    }
    
    /**
     * The get method:
     * 
     * 1) Gets all open positions
     * 
     * 2) Prints the html page showing positions with radio buttons to select one and submit.
     * 
     * If no position exists, the user is notified and a link is shown for create_order.jsp
     * 
     * If positions exist, then one of them must have been submitted to this servlet's post method.
     * @author Karanveer
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	// Get userid from session
	Integer userId = getUserId(request);
	ArrayList<Position> positions = getOpenPositionsForAccount(userId);

	// For this user_id, print list of positions
	response.setContentType("text/html");
	PrintWriter writer = response.getWriter();
	writer.println("<!DOCTYPE html>");
	writer.println("<html>");
	writer.println("<head>");
	writer.println("<title>" + "Sollers Trading System - Close Position" + "</title></head>");
	writer.println(getCSS()); // static getCSS() method in TradingSystemServlet
	writer.println("<body>");
	
	if (positions.isEmpty()) {
	    writer.println("<p>No positions found</p>");
	    writer.println("<br><br>");
	    writer.println("<ul class=\"navList\">");
	    writer.println("<li class=\"navItem\"><a href=\"create_order.jsp\">New Trade</a></li>");
	    writer.println("</ul>");
	}
	else {
	    writer.println("<h3>Open Positions</h3>");
	    writer.println("<form name=\"positionform\" method=\"post\">");
	    for (Position p: positions) {
		writer.println("<input type=\"radio\" name=\"openPosition\" value=\"" + p.getPositionId() + "\" required>");
		writer.println(p.toString() + "<br>");
	    }
	    writer.println("<br><input type=\"submit\" name=\"submit\" value=\"submit\">  <br>");
	    writer.println("</form>");
	}

	writer.println("</body>");
	writer.println("</html>");
    }

    /**
     * This post method receives the value of the 
     * chosen position's radio button which gives the
     * position id.
     * That id is selected from the database, its contents
     * set as attributes and redirected to the create_order.jsp page. 
     * @author Karanveer
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	// get value of radio button
	int positionId = Integer.parseInt(request.getParameter("openPosition"));
	
	try {
	    Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery(Position.getAllSelectClause() +  " WHERE id=\"" + positionId + "\";");
	    rs.next();
	    
	    // create position object from ResultSet
	    Position position = new Position(rs);
	    
	    // send position object as attribute to send to create_order.jsp
	    request.setAttribute("position", position);
	    request.setAttribute("symbol", position.getSymbol());
	    request.setAttribute("side", position.getSide());
	    request.setAttribute("size", position.getSize());
	    
	    request.getRequestDispatcher("/create_order.jsp").forward(request, response);
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
