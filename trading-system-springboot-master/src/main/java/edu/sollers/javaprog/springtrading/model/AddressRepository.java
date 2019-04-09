/**
 * 
 */
package edu.sollers.javaprog.springtrading.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author rutpatel
 *
 */
@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
	@Nullable
	public Address findByStreet1AndStreet2AndCityAndZip(String street1, String street2, String city, String zip);
}
