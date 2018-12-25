package edu.sollers.javaprog.tradingsystem;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Application Lifecycle Listener implementation class TradSysDaemon
 *
 */
@WebListener
public class TradSysDaemon implements ServletContextListener {

    /**
     * Default constructor.
     */
    public TradSysDaemon() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
	// TODO Auto-generated method stub
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    @SuppressWarnings("unchecked")
    public void contextInitialized(ServletContextEvent arg0) {
	ServletContext sc = arg0.getServletContext();
	Thread th_xo = new Thread() {
	    public void run() {
		tradeExecutionEngine(sc);
	    }
	};
	th_xo.setDaemon(true);
	th_xo.start();
	//UpdateStock us = new UpdateStock(sc, conn);
	Thread th_us = new Thread() {
	    public void run() {
		updateStockEngine(sc);
	    }
	};
	th_us.setDaemon(true);
	th_us.start();
    }

    public void tradeExecutionEngine(ServletContext sc) {
	Connection conn = null;
	try {
	    Class.forName("org.mariadb.jdbc.Driver");
	    String url = "jdbc:mariadb://localhost:3306/sollerstrading";
	    // create a connection to the database
	    conn = DriverManager.getConnection(url, "webuser", "Sollers@123");
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
	while (true) {
	    try {
		Order o = getNextMarketOrder(sc);
		if (o != null) {
		    //System.out.println("Simulation of trade execution");
		    //System.out.println(o.getOrderID() + " " + o.getSize());
		    Statement stmt = null;
		    ResultSet rs = null;
		    Stock stock = YahooFinance.get(o.getSymbol());
		    double bid = stock.getQuote(true).getBid().doubleValue();
		    double ask = stock.getQuote().getAsk().doubleValue();
		    Date now = new Date(System.currentTimeMillis());
		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		    String dateText = df.format(now);
		    conn.setAutoCommit(false);
		    stmt = conn.createStatement();
		    String sqlString = "";
		    // positions table: symbol
		    if (o.getOrderType() == Order.OrderType.BUY | o.getOrderType() == Order.OrderType.SELL_SHORT) {
			sqlString = "insert into positions (account_id, side, size, price, symbol, is_open, creation_date)"
				+ " values (" + o.getAccountId() + ", "
				+ ((o.getOrderType() == Order.OrderType.BUY)?1:-1)
				+ ", " + o.getSize() + ", " 
				+ ((o.getOrderType() == Order.OrderType.BUY)?ask:bid)
				+ ", '" + o.getSymbol() + "'"
				+ ", 1, '" + dateText +"')";
		    } else {
			sqlString = "update positions set is_open=0 where account_id = " + o.getAccountId() + " and symbol = '" + o.getSymbol() + "'"; 
		    }
		    System.out.println(sqlString);
		    int rows = stmt.executeUpdate(sqlString);
		    // positions table: money
		    sqlString = "update positions set size = size - " 
			    + (((o.getOrderType() == Order.OrderType.SELL || o.getOrderType() == Order.OrderType.SELL_SHORT)?-1.0*bid:ask)*o.getSize())
			    + " where account_id = " + o.getAccountId()
			    + " and symbol = 'MONEY'";
		    System.out.println(sqlString);
		    rows = stmt.executeUpdate(sqlString);
		    // orders table
		    sqlString = "update orders set order_status = 'EXECUTED' where id=" + o.getOrderID();
		    System.out.println(sqlString);
		    rows = stmt.executeUpdate(sqlString);
		    conn.commit();
		}
		Thread.currentThread();
		Thread.sleep(1000);
	    } catch (Exception e) {
		System.out.println(e.getMessage());
	    }
	}
    }

    public Order getNextMarketOrder(ServletContext sc) {
	Order retVal = null;
	@SuppressWarnings("unchecked")
	Vector<Order> marketOrderQueue = (Vector<Order>) sc.getAttribute("marketOrderQueue");
	if (!marketOrderQueue.isEmpty()) {
	    synchronized (Thread.currentThread()) {
		retVal = marketOrderQueue.remove(0);
	    }
	}
	return retVal;
    }

    public void updateStockEngine(ServletContext sc) {
	Connection conn = null;
	try {
	    Class.forName("org.mariadb.jdbc.Driver");
	    String url = "jdbc:mariadb://localhost:3306/sollerstrading";
	    // create a connection to the database
	    conn = DriverManager.getConnection(url, "webuser", "Sollers@123");
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
	int i = 0;
	while (true) {
	    try {
		Statement stmt = null;
		ResultSet rs = null;
		stmt = conn.createStatement();
		rs = stmt.executeQuery("select ticker from stocks");

		while (rs.next()) {
		    Stock stock = YahooFinance.get(rs.getString("ticker"));
		    stmt.executeUpdate("update stocks set bid = " + stock.getQuote(true).getBid() + ", last = "
			    + stock.getQuote().getPrice() + ", ask = " + stock.getQuote().getAsk() + " where ticker='"
			    + rs.getString("ticker") + "';");
		}
		System.out.println("\n/\\Stocks updated..." + ++i + "/\\\n");
		trigger_fun(sc);
		Thread.sleep(20000);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * 
     * Used to check whether change in stocks price, triggers any Pending Order or
     * not. If triggered, then Pending Order should be removed from Hashtable and
     * should be added to Execute Order i.e. in Vector. If not triggered, then
     * Pending Order remains in Hashtable.
     */

    @SuppressWarnings("unchecked")
    public void trigger_fun(ServletContext sc) throws IOException {
	Hashtable<Integer, Order> pO;
	Vector<Order> marketOrderQueue;
	pO = (Hashtable<Integer, Order>) sc.getAttribute("pendingOrderCollection");
	marketOrderQueue = (Vector<Order>) sc.getAttribute("marketOrderQueue");
	Set<Integer> keys = pO.keySet();
	System.out.println(pO.size() + " pending orders");
	for (Integer key : keys) {
	    Order temp = pO.get(key);
	    Stock stock = YahooFinance.get(temp.getSymbol());
	    BigDecimal lP = stock.getQuote().getPrice();
	    double lastPrice = lP.doubleValue();
	    Order.OrderType orderType = temp.getOrderType();
	    Order.PriceType priceType = temp.getPriceType();
	    double limitPrice = temp.getStopPrice();

	    if ((orderType == Order.OrderType.BUY | orderType == Order.OrderType.BUY_TO_COVER) & priceType == Order.PriceType.LIMIT & lastPrice < limitPrice) {
		pO.remove(key);
		marketOrderQueue.add(temp);
		// addMarketOrder(temp);
		System.out.println("Simulation: adding Limit Order to execute queue ..." + temp.getSymbol());
	    } else if ((orderType == Order.OrderType.SELL | orderType == Order.OrderType.SELL_SHORT) & priceType == Order.PriceType.LIMIT
		    & lastPrice > limitPrice) {
		pO.remove(key);
		marketOrderQueue.add(temp);
		System.out.println("Simulation: adding Limit Order to execute queue..." + temp.getSymbol());
	    } else if ((orderType == Order.OrderType.BUY | orderType == Order.OrderType.BUY_TO_COVER) & priceType == Order.PriceType.STOP & lastPrice > limitPrice) {
		pO.remove(key);
		marketOrderQueue.add(temp);
		// addMarketOrder(temp);
		System.out.println("Simulation: adding Stop Order to execute queue..." + temp.getSymbol());
	    } else if ((orderType == Order.OrderType.SELL | orderType == Order.OrderType.SELL_SHORT) & priceType == Order.PriceType.STOP & lastPrice < limitPrice) {
		pO.remove(key);
		marketOrderQueue.add(temp);
		// addMarketOrder(temp);
		System.out.println("Simulation: adding Limit Order to execute queue..." + temp.getSymbol());
	    } else {
		System.out.println(temp.getSymbol() + " not triggered...");
	    }
	}
    }
}
