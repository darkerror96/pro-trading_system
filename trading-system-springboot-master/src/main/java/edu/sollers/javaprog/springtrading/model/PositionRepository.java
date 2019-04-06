/**
 * 
 */
package edu.sollers.javaprog.springtrading.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * @author Karanveer
 *
 */
@Repository
public interface PositionRepository extends CrudRepository<Position, Integer>{
	/**
	 * Gets list of all positions for the given account
	 * @param account
	 * @return List of positions or null
	 */
	public List<Position> getPositionsByAccount(Account account);
	
	
	/**
	 * Gets list of all positions for given account and "isOpen" boolean value. 
	 * False returns all closed positions, true returnes all open positions. 
	 * @param account
	 * @param isOpen true or false
	 * @return List or null
	 */
	public List<Position> getPositionsByAccountAndIsOpen(Account account, Boolean isOpen);
	
	/**
	 * Pass the symbol "MONEY" to get the money position for the account. 
	 * @param symbol
	 * @param account
	 * @return Position object or null
	 */
	@Nullable
	public Position findPositionBySymbolAndAccount(String symbol, Account account);
}
