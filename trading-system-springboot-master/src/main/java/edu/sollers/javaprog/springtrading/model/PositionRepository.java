/**
 * 
 */
package edu.sollers.javaprog.springtrading.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author rutpatel
 *
 */
@Repository
public interface PositionRepository extends CrudRepository<Position, Integer> {

	public List<Position> getPositionsByAccount(Account account);

	public List<Position> getPositionsByAccountAndIsOpen(Account account, Boolean isOpen);

	@Nullable
	public Position findPositionBySymbolAndAccount(String symbol, Account account);
}
