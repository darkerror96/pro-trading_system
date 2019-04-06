/**
 * 
 */
package edu.sollers.javaprog.springtrading.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * @author praka
 *
 */
@Entity 
public class Login {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Gets id to set form table
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	private String uname;
	private String password;

	public Login() {}

	public Login(String uname, String password) {
		this.uname = uname;
		this.password = password;
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
	 * @return the name
	 */
	public String getUname() {
		return uname;
	}
	/**
	 * @param name the name to set
	 */
	public void setUname(String name) {
		this.uname = name;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
