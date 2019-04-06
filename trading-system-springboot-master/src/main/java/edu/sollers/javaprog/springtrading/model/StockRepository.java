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
public interface StockRepository extends CrudRepository<Stock, Integer> {
	@Nullable
	public Stock findBySymbol(String symbol);
}
