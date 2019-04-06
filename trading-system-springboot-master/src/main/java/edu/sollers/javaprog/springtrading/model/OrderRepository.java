/**
 * 
 */
package edu.sollers.javaprog.springtrading.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sollers.javaprog.springtrading.model.Order.OrderStatus;

/**
 * @author Karanveer
 *
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Integer>{
	public List<Order> getOrderByAccountAndOrderStatus(Account account, OrderStatus orderStatus);
	
}
