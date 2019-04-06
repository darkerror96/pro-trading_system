package edu.sollers.javaprog.springtrading.config;

import java.util.Hashtable;
import java.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import edu.sollers.javaprog.springtrading.model.Order;


@Service
public class TrdSysDaemon implements Runnable {
	private Vector<Order> marketOrders;
	private Hashtable<Integer, Order> pendingOrders;

	public TrdSysDaemon() {
		marketOrders = new Vector<Order>();
		pendingOrders = new Hashtable<Integer, Order>();
		Logger logger = LoggerFactory.getLogger(TrdSysDaemon.class);

		logger.info("Started TrdSysDaemon");
	}

	@Override
	public void run() {
		System.out.println("INSIDE RUN METHOD");
		Logger logger = LoggerFactory.getLogger(TrdSysDaemon.class);
		try {
			logger.info("Heartbeat");
			Thread.sleep(10000);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	
	public Vector<Order> getMarketOrders(){
		return marketOrders;
	}
	
	public Hashtable<Integer, Order> getPendingOrders() {
		return pendingOrders;
	}
	
}
