/**
 * 
 */
package edu.sollers.javaprog.springtrading.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Karanveer
 *
 */
@Entity
@Table(name = "stocks")
public class Stock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Integer id;
	
	@Column(name = "ticker")
	private String symbol;
	
	@Column(name = "full_name")
	private String fullName;
	
	private Double bid;
	private Double ask;
	private Double last;
	
	public Stock() {
	}

	/**
	 * @param ticker
	 * @param fullName
	 * @param bid
	 * @param ask
	 * @param last
	 */
	public Stock(String symbol, String fullName, Double bid, Double ask, Double last) {
		this.symbol = symbol;
		this.fullName = fullName;
		this.bid = bid;
		this.ask = ask;
		this.last = last;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the bid
	 */
	public Double getBid() {
		return bid;
	}

	/**
	 * @param bid the bid to set
	 */
	public void setBid(Double bid) {
		this.bid = bid;
	}

	/**
	 * @return the ask
	 */
	public Double getAsk() {
		return ask;
	}

	/**
	 * @param ask the ask to set
	 */
	public void setAsk(Double ask) {
		this.ask = ask;
	}

	/**
	 * @return the last
	 */
	public Double getLast() {
		return last;
	}

	/**
	 * @param last the last to set
	 */
	public void setLast(Double last) {
		this.last = last;
	}
}
