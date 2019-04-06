/**
 * 
 */
package edu.sollers.javaprog.springtrading.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Karanveer
 *
 */
@Entity
@Table(name="accounts")
public class Account {
	@Id
	private Integer id; // will be assigned userId from session as constructor parameter
	
	@Column(name="first_name") // column name in table referenced by field name
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	private String dob;
	private String ssn;
	private String email;
	
	// Foreign keys
	
	@ManyToOne
	@JoinColumn(nullable=true, name="address_id")
	private Address address;
	
	@ManyToOne
	@JoinColumn(nullable=true, name="mailing_address_id")
	private Address mailingAddress;
	
	public Account() {
		
	}

	/**
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param dob
	 * @param ssn
	 * @param email
	 */
	public Account(Integer id, String firstName, String lastName, String dob, String ssn, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.ssn = ssn;
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the addressId to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the mailingAddress
	 */
	public Address getMailingAddress() {
		return mailingAddress;
	}

	/**
	 * @param mailingAddress the mailingAddress to set
	 */
	public void setMailingAddress(Address mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	
}
	

	