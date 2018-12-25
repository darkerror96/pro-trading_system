package edu.sollers.javaprog.tradingsystem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Karanveer
 */

public class Stock {
    private String ticker;
    private String fullName;
    private double bid;
    private double ask;
    private double last;

    /**
     * @param ticker
     * @param fullName
     * @param bid
     * @param ask
     * @param last
     */
    public Stock(String ticker, String fullName, double bid, double ask, double last) {
	this.ticker = ticker;
	this.fullName = fullName;
	this.bid = bid;
	this.ask = ask;
	this.last = last;
    }
    
    public Stock (ResultSet rs) throws SQLException{
    	ticker = rs.getString(1);
    	fullName = rs.getString(2);
    	bid = rs.getDouble(3);
    	ask = rs.getDouble(4);
    	last = rs.getDouble(5);
    }
    
    public static String getFieldOrder() {
    	return "ticker, full_name, bid, ask, last";
    }
    
    public static String getTableName() {
    	return "stocks";
    }
    
    public static String getSelectClause() {
    	return "select " + getFieldOrder() + " from " + getTableName();
    }
    
    public String getInsertStatement() {
    	return "insert into " + getTableName() + " (" + getFieldOrder() + ") values ('" + ticker +"', '" + fullName + ", " + bid + ", " + ask + " ," + last + ")";
    }
    
    public String getUpdateStatement() {
    	return "update " + getTableName() + " set bid=" + bid + ", ask=" + ask + ", last=" + last + " where ticker='" + ticker + "'";
    }
    
    /**
     * @return the ticker
     */
    public String getTicker() {
        return ticker;
    }
    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }
    /**
     * @return the bid
     */
    public double getBid() {
        return bid;
    }
    /**
     * @return the ask
     */
    public double getAsk() {
        return ask;
    }
    /**
     * @return the last
     */
    public double getLast() {
        return last;
    }

}
