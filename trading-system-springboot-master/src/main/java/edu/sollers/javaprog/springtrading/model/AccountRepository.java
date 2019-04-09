/**
 * 
 */
package edu.sollers.javaprog.springtrading.model;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author rutpatel
 *
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
	public Optional<Account> findById(Integer id);
}
