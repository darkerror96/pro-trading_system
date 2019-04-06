/**
 * 
 */
package edu.sollers.javaprog.springtrading.model;


import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;


/**
 * @author praka
 *
 */

@Repository
public interface LoginRepository extends CrudRepository<Login, Integer> {
	@Nullable
	public Login findByuname(String name);
}
