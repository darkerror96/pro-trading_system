/**
 * 
 */
package edu.sollers.javaprog.springtrading.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "orders")
public class Order {

	// define enums
	public enum OrderType {
		BUY, SELL, SELL_SHORT, BUY_TO_COVER;
	}
	public enum PriceType {
		MARKET, LIMIT, STOP;
	}
	public enum TimeInForce {
		DAY, GTX, GTC;
	}
	public enum OrderStatus {
		PENDING, CANCELLED, EXECUTED;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name="order_type")
	private OrderType orderType;


	@Enumerated(EnumType.STRING)
	@Column(name="price_type")
	private PriceType priceType;

	@Enumerated(EnumType.STRING)
	@Column(name="time_in_force")
	private TimeInForce timeInForce;


	@Enumerated(EnumType.STRING)
	@Column(name="order_status")
	private OrderStatus orderStatus;

	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;

	private String symbol;
	private Double size;

	@Column(name = "stop_price")
	private Double stopPrice;

	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	public Order() {

	}

	/**
	 * Id field is generated based on database id
	 * @param orderType
	 * @param priceType
	 * @param timeInForce
	 * @param account
	 * @param symbol
	 * @param size
	 * @param stopPrice
	 */
	public Order(OrderType orderType, PriceType priceType, TimeInForce timeInForce,
			Account account, String symbol, Double size, Double stopPrice) {

		this.orderType = orderType;
		this.priceType = priceType;
		this.timeInForce = timeInForce;
		this.orderStatus = OrderStatus.PENDING;
		this.account = account;
		this.symbol = symbol;
		this.size = size;
		this.stopPrice = stopPrice;
		this.creationDate = LocalDateTime.now();
	}

	/**
	 * @return the orderType
	 */
	public OrderType getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the priceType
	 */
	public PriceType getPriceType() {
		return priceType;
	}

	/**
	 * @param priceType the priceType to set
	 */
	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}

	/**
	 * @return the timeInForce
	 */
	public TimeInForce getTimeInForce() {
		return timeInForce;
	}

	/**
	 * @param timeInForce the timeInForce to set
	 */
	public void setTimeInForce(TimeInForce timeInForce) {
		this.timeInForce = timeInForce;
	}

	/**
	 * @return the orderStatus
	 */
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
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
	 * @return the stopPrice
	 */
	public Double getStopPrice() {
		return stopPrice;
	}

	/**
	 * @param stopPrice the stopPrice to set
	 */
	public void setStopPrice(Double stopPrice) {
		this.stopPrice = stopPrice;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the creationDate
	 */
	public LocalDateTime getCreationDate() {
		return creationDate;
	}


	public String toString() {
		String result = "Order Type: " + orderType + "\n"
				+ "Symbol: " + symbol + "\n"
				+ "Size: " + size + "\n"
				+ "Price Type: " + priceType + "\n";
		result += (stopPrice == 0) ? "" : "Stop Level: $" + stopPrice + "\n";
		result += "Creation Date: " + creationDate;
		return result;
	}

}
