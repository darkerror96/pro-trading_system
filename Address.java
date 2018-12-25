/**
 * 
 */
package edu.sollers.javaprog.tradingsystem;

/**
 * @author Karanveer
 *
 */
public class Address {
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zip;
    
    /**
     * @param street1
     * @param street2
     * @param city
     * @param state
     * @param zip
     */
    public Address(String street1, String street2, String city, String state, String zip) {
	super();
	this.street1 = street1;
	this.street2 = street2;
	this.city = city;
	this.state = state;
	this.zip = zip;
    }
 
    public static String getTableName() {
    	return "address";
    }
    
    /**
     * Get order for all attributes INCLUDING id
     * @return String "id, street1, street2, city, state, zip"
     */
    public static String getAllFieldsOrder() {
    	return "id, street1, street2, city, state, zip";
    }
    
    /**
     * Get order for getting all non-id attributes
     * @return String "street1, street2, city, state, zip"
     */
    public static String getFieldOrder() {
    	return "street1, street2, city, state, zip";
    }
    
    /**
     * Get all attributes from database, including id attribute
     * @return String "select " + getAllFieldsOrder() + " from " + getTableName()"
     */
    public static String getAllSelectClause() {
    	return "select " + getAllFieldsOrder() + " from " + getTableName();
    }
    
    /**
     * Get all non-id attributes from database clause
     * @return String "select " + getFieldOrder() + " from " + getTableName()"
     */
    public static String getSelectClause() {
	return "select " + getFieldOrder() + " from " + getTableName();
    }
    
    /**
     * Gets insert statement clause
     * @return String insert into getTableName() (getFieldOrder()) values (street1, street2, city, state, zip);"
     */
    public String getInsertStatement() {
    	return "insert into " + getTableName() + " (" + getFieldOrder() + ") values ('" + street1 +"', '" + street2 + ", '" + city + "', '" + state + "', '" + zip + "');";
    }
    
    /**
     * This method returns the where clause String for checking if an address already exists
     * @return String " where street1='" + street1 + "' and city='" + city + "' and state='" + state + "';"
     */
    public String getComparisonWhereClause() {
	return " where street1='" + street1 + "' and city='" + city + "' and state='" + state + "';";
    }
    
    
    
}
