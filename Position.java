/**
 * 
 */
package edu.sollers.javaprog.tradingsystem;
/**
 * @author Karanveer
 */

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Position {
    private int    accountId;
    private int	   positionId;
    private String symbol;
    private int	   side;
    private double    size;
    private double price;
    private boolean isOpen;
    private Date   creationDate;

    /**
     * @param symbol
     * @param accountId
     * @param side
     * @param size
     * @param price
     * @param creationDate
     */
    public Position(int positionId, String symbol, int accountId, int side, double size, double price, Date creationDate) {
	this.positionId = positionId;
	this.symbol = symbol;
	this.side = side;
	this.size = size;
	this.price = price;
	this.isOpen = true;	// set to false when closing the position
	this.creationDate = creationDate;
    }
    
    /**
     * Constructor that instantiates a position from ResultSet paramater
     * @param rs
     * @throws Exception
     */
    public Position(ResultSet rs) throws Exception {
	positionId 	= rs.getInt(1);
	symbol		= rs.getString(2);
	accountId	= rs.getInt(3);
	side		= rs.getInt(4);
	size		= rs.getDouble(5);
	price		= rs.getDouble(6);
	creationDate	= new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(7));
	isOpen		= rs.getBoolean(8);
    }
    
    /**
     * Get table name
     * @return String
     */
    public static String getTableName() {
    	return "positions";
    }
    
    /**
     * Get order for getting all non-id attributes
     * @return String "symbol, account_id, side, size, price, creation_date"
     */
    public static String getFieldOrder() {
	return "symbol, account_id, side, size, price, creation_date";
    }
    
    /**
     * Get order for getting all attributes including id
     * @return String "id, symbol, account_id, side, size, price, creation_date, is_open"
     */
    public static String getAllFieldsOrder() {
	return "id, symbol, account_id, side, size, price, creation_date, is_open";
    }
    
    /**
     * Get all non-id attributes from database clause
     * @return String "select " + getFieldOrder() + " from " + getTableName()"
     */
    public static String getSelectClause() {
	return "select " + getFieldOrder() + " from " + getTableName();
    }
    
    /**
     * Get all attributes from database, including id attribute
     * @return String "select " + getAllFieldsOrder() + " from " + getTableName()"
     */
    public static String getAllSelectClause() {
    	return "select " + getAllFieldsOrder() + " from " + getTableName();
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
	return positionId;
    }
    
    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
	return creationDate;
    }

    /**
     * @return the accountId
     */
    public int getAccountId() {
	return accountId;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
	return symbol;
    }

    /**
     * @return the side
     */
    public int getSide() {
	return side;
    }

    /**
     * @return the size
     */
    public double getSize() {
	return size;
    }

    /**
     * @return the price
     */
    public double getPrice() {
	return price;
    }

    /**
     * @return the is Open
     */
    public boolean getIsOpen() {
	return isOpen;
    }

    /**
     * @param status the status to set when closing a position
     */
    public void setIsOpen(boolean status) {
	this.isOpen = status;
    }

    @Override
    public String toString() {
	String result = symbol + ": ";
	result += (side == 1) ? "long" : "short";
	result += ", ";
	result += size + " shares, $";
	result += price + ", ";
	result += creationDate;
	return result;
    }
}
