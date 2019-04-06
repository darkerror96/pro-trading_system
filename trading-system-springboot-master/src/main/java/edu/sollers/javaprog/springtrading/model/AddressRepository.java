/**
 * 
 */
package edu.sollers.javaprog.springtrading.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * @author Karanveer
 *
 */
@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
	/**
	 * This method query returns an address entity by street1, city and zip. 
	 * It can be used to determine whether an entered address already exists in the table.
	 * @param street1
	 * @param city
	 * @param zip
	 * @return Address entity or null
	 */
	@Nullable
	public Address findByStreet1AndStreet2AndCityAndZip(String street1, String street2, String city, String zip);
}
