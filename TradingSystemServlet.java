/**
 * 
 */
package edu.sollers.javaprog.tradingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class TradingSystemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected Connection conn;
    
    /**
     * init. Initialized database connection
     */
    public void init(ServletConfig config) throws ServletException 
    {
	super.init(config);
	try {
	    Class.forName("org.mariadb.jdbc.Driver");

	    // conenction string
	    String url = "jdbc:mariadb://localhost:3306/sollerstrading";
	    conn = DriverManager.getConnection(url, "webuser", "Sollers@123");

	    System.out.println("\nConnection made\n\n");
	} catch (SQLException e) {
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
    }

    /**
     * destroy method closes db connection
     */
    public void destroy() {
	try {
	    conn.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * Gets the pending order queue 
     * @return Hashtable<Integer, Order>
     */
    protected Hashtable<Integer, Order> getPendingOrders(){
        ServletContext sc = getServletContext();
        @SuppressWarnings("unchecked")
        Hashtable<Integer,Order> pendingOrderQueue = (Hashtable<Integer,Order>)sc.getAttribute("pendingOrders");
        return pendingOrderQueue;
    }
    
    /**
     * Gets the user id from the session from request instance.
     * @param request The HttpServletRequest object
     * @return Integer object containing the user id or null if no user id in session
     */
    protected Integer getUserId(HttpServletRequest request) {
	return (Integer) request.getSession().getAttribute("userId");
    }
    
    /**
     * This method retrieves all open positions for a given user id
     * 
     * @param userId of user in session
     * @return ArrayList of all open positions or empty array list if no positions
     */
    protected ArrayList<Position> getOpenPositionsForAccount(int userId) {
	ArrayList<Position> positions = new ArrayList<>();
	try {
	    // Build array list of positions
	    Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery(Position.getAllSelectClause() + " WHERE account_id=\"" + userId + "\" AND is_open=\"1\";");
	    while (rs.next()) {
		String symbol = rs.getString(2);
		// MONEY position will NOT be included in array list
		if (symbol.equals("MONEY")) {
		    continue;
		}
		Position p = new Position(rs);
		positions.add(p);
		System.out.println(p.toString());
	    }
	    stmt.close();
	    rs.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return positions;
    }
    
    /**
     * This method retrieves the given account's funds
     * i.e the "MONEY" position for the account. 
     * 
     * @param userId int
     * @return Position object with for symbol "MONEY" or null
     */
    protected Position getMoneyPosition(int userId) {
	Position p = null;
	try {
	    Statement stmt = conn.createStatement();
	    ResultSet rs   = stmt.executeQuery(Position.getAllSelectClause() + " WHERE account_id=" + userId + " AND symbol=\"MONEY\";");
	    if (rs.next()) {
		p = new Position(rs);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return p;
    }
    
    /**
     * Method to retrieve the information for a stock from 
     * the database matching the input parameter ticker.
     * 
     * @param ticker String symbol for stock
     * @return stock object if it was found, else null
     * @author Karanveer
     */
    protected Stock getStock(String ticker) {
	Stock currentStock = null;
	try {
	    Statement stmt = conn.createStatement();
	    ResultSet rs   = stmt.executeQuery(Stock.getSelectClause() + " WHERE ticker=\"" + ticker + "\";");
	    if (rs.next()) {
		currentStock = new Stock(rs);
	    }
	    stmt.close();
	    rs.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return currentStock;
    }
    
    
    /**
     * Method that provides the CSS for displaying a table
     * Simply call this method to insert the style into HTML. 
     * It includes the 'style' opening and closing tags. 
     * 
     * @return style tag with contents as String
     * @author Karanveer
     */
    protected static String getCSS() {
	String style = "<style>";
	
	style += "	ul.navList {\r\n" + 
		"		list-style-type: none;\r\n" + 
		"		margin: 0;\r\n" + 
		"		padding: 0;\r\n" + 
		"		width: 200px;\r\n" + 
		"		background-color: #f1f1f1;\r\n" + 
		"		border: 1px solid #555;\r\n" + 
		"	}\r\n" + 
		"	\r\n" + 
		"	li.navItem a {\r\n" + 
		"		display: block;\r\n" + 
		"		color: #000;\r\n" + 
		"		padding: 8px 16px;\r\n" + 
		"		text-decoration: none;\r\n" + 
		"	}\r\n" + 
		"	\r\n" + 
		"	li.navItem {\r\n" + 
		"		text-align: center;\r\n" + 
		"		border-bottom: 1px solid #555;\r\n" + 
		"	}\r\n" + 
		"	\r\n" + 
		"	li.navItem:last-child {\r\n" + 
		"		border-bottom: none;\r\n" + 
		"	}\r\n" + 
		"	\r\n" + 
		"	li.navItem a.active {\r\n" + 
		"		background-color: #4CAF50;\r\n" + 
		"		color: white;\r\n" + 
		"	}\r\n" + 
		"	\r\n" + 
		"	li.navItem a:hover:not(.active) {\r\n" + 
		"	    background-color: #555;\r\n" + 
		"	    color: white;\r\n" + 
		"	}\r\n" + 
		"	\r\n" + 
		"	li.navItem a.logout {\r\n" + 
		"		float:right;\r\n" + 
		"	}";
	
	style += ".rTable {\r\n" + 
		"  display: table;\r\n" + 
		"  width: 100%;\r\n" + 
		"}\r\n" + 
		"\r\n" + 
		".rTableRow {\r\n" + 
		"  display: table-row;\r\n" + 
		"}\r\n" + 
		"\r\n" + 
		".rTableHeading {\r\n" + 
		"  display: table-header-group;\r\n" + 
		"  background-color: #ddd;\r\n" + 
		"}\r\n" + 
		"\r\n" + 
		".rTableCell,\r\n" + 
		".rTableHead {\r\n" + 
		"  display: table-cell;\r\n" + 
		"  padding: 3px 10px;\r\n" + 
		"  border: 1px solid #999999;\r\n" + 
		"}\r\n" + 
		"\r\n" + 
		".rTableHeading {\r\n" + 
		"  display: table-header-group;\r\n" + 
		"  background-color: #ddd;\r\n" + 
		"  font-weight: bold;\r\n" + 
		"}\r\n" + 
		"\r\n" + 
		".rTableFoot {\r\n" + 
		"  display: table-footer-group;\r\n" + 
		"  font-weight: bold;\r\n" + 
		"  background-color: #ddd;\r\n" + 
		"}\r\n" + 
		"\r\n" + 
		".rTableBody {\r\n" + 
		"  display: table-row-group;\r\n" + 
		"}\r\n";
	
	style += "</style>";
	return style;
    }


}
