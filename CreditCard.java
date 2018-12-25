package edu.sollers.javaprog.tradingsystem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author rutpatel
 *
 */

public class CreditCard 
{
	private int id;
	private String owner;
	private String cc_type;
	private String number;
	private int exp_month;
	private int exp_year;
	private String cvv;
	
	public CreditCard(int id, String owner, String cc_type, String number, int exp_month, int exp_year, String cvv) {
		this.id = id;
		this.owner = owner;
		this.cc_type = cc_type;
		this.number = number;
		this.exp_month = exp_month;
		this.exp_year = exp_year;
		this.cvv = cvv;
	}

	public CreditCard(ResultSet rs) throws SQLException{
    	id = rs.getInt(1);
    	owner = rs.getString(2);
    	cc_type = rs.getString(3);
    	number = rs.getString(4);
    	exp_month = rs.getInt(5);
    	exp_year = rs.getInt(6);
    	cvv = rs.getString(7);
    }

	public String getFieldOrder() {
	    return "id, owner, cc_type, number, exp_month, exp_year, cvv";
	}
	    
	public String getTableName() {
	    return "credit_cards";
	}
	    
	public String getSelectClause() {
	    return "select " + getFieldOrder() + " from " + getTableName();
	}
	    
	public String getInsertStatement() {
	    return "insert into "+getTableName()+"("+getFieldOrder()+") values ("+id+", '"+owner+"', '"+cc_type+"', '"+number+"' ,"+exp_month+", "+exp_year+", '"+cvv+"')";
	}
	    
	public String getUpdateStatement() {
	    return "update " + getTableName() + " set owner='"+owner+ "', cc_type='"+cc_type+"', number='"+number+"', exp_month="+exp_month+", exp_year="+exp_year+", cvv='"+cvv+"' where id="+id+"";
	}
}
