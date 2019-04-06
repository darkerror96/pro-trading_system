/**
 * 
 */
package edu.sollers.javaprog.springtrading.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Karanveer
 *
 */
@Entity
@Table(name = "positions")
public class Position {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;
	
	private String symbol;
	private Integer side;
	private Double size;
	private Double price;
	
	@Column(name = "is_open")
	private Boolean isOpen;
	
	@Column(name = "creation_date")
	private LocalDateTime creationDate;
	
	public Position() {
		
	}

	/**
	 * @param account
	 * @param symbol
	 * @param side
	 * @param size
	 * @param price
	 */
	public Position(Account account, String symbol, Integer side, Double size, Double price) {
		super();
		this.account = account;
		this.symbol = symbol;
		this.side = side;
		this.size = size;
		this.price = price;
		this.isOpen = true; // set to false when position is closed
		this.creationDate = LocalDateTime.now();
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
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
	 * @return the side
	 */
	public Integer getSide() {
		return side;
	}

	/**
	 * @param side the side to set
	 */
	public void setSide(Integer side) {
		this.side = side;
	}

	/**
	 * @return the size
	 */
	public Double getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Double size) {
		this.size = size;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the isOpen
	 */
	public Boolean getIsOpen() {
		return isOpen;
	}

	/**
	 * @param isOpen the isOpen to set
	 */
	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	/**
	 * @return the creationDate
	 */
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
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
