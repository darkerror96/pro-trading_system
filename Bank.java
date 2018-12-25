package edu.sollers.javaprog.tradingsystem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author rutpatel
 *
 */

public class Bank {
	private int id;
	private String owner;
	private String name;
	private String acc_num;
	private String routing_num;

	public Bank(int id, String owner, String name, String acc_num, String routing_num) {
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.acc_num = acc_num;
		this.routing_num = routing_num;
	}

	public Bank(ResultSet rs) throws SQLException{
    	id = rs.getInt(1);
    	owner = rs.getString(2);
    	name = rs.getString(3);
    	acc_num = rs.getString(4);
    	routing_num = rs.getString(5);	
    }

	public String getFieldOrder() {
	    return "id, owner, name, acc_num, routing_num";
	}
	    
	public String getTableName() {
	    return "banks";
	}
	    
	public String getSelectClause() {
	    return "select "+getFieldOrder()+" from "+getTableName();
	}
	    
	public String getInsertStatement() {
	    return "insert into "+getTableName()+" ("+getFieldOrder()+") values ("+id+", '"+owner+"', '"+name+"', '"+acc_num+"' ,'"+routing_num+"')";
	}
	    
	public String getUpdateStatement() {
	    return "update " + getTableName() + " set owner='"+owner+ "', name='"+name+"', acc_num='"+acc_num+"', routing_num='"+routing_num+"' where id="+id+"";
	}
}
