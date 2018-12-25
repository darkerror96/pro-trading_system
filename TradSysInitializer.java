package edu.sollers.javaprog.tradingsystem;
/**
 * @author praka
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class Daemon
 */
@WebServlet(description = "This is the background process that creates common objects needed for the trading system", urlPatterns = { "/Daemon" })
public class TradSysInitializer extends TradingSystemServlet {
    private static final long serialVersionUID = 1L;
    private Vector<Order> marketOrders;
    private Hashtable<Integer, Order> pendingOrders;

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {
	super.init(config);
	System.out.println("Initializing Trading System");
	Order.setLastOrderId(getLastOrderId() + 1);
	System.out.println("New Orders will begin with Order Id 1");
	marketOrders = new Vector<Order>();
	pendingOrders = new Hashtable<Integer, Order>();
	ServletContext sc = getServletContext();
	sc.setAttribute("marketOrderQueue", marketOrders);
	sc.setAttribute("pendingOrderCollection", pendingOrders);
	System.out.println("Market Order Queue and Pending Order Collection added to context");
    }

    /**
     * Retrieves the max id from orders table
     * @return int
     */
    private int getLastOrderId() {
	int result = 0;
	try {
	    Statement stmt = conn.createStatement();
	    ResultSet rs   = stmt.executeQuery("SELECT MAX(id) FROM orders;");
	    if (rs.next()) {
		result = rs.getInt(1);
	    }
	    stmt.close();
	    rs.close();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return result;
    }
}
