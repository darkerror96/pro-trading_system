package edu.sollers.javaprog.springtrading.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

/**
 * 
 * @author rutpatel
 *
 */
@Service
public class AsynchronousService {
	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private ApplicationContext applicationContext;

	public void executeAsynchronously() {
		TrdSysDaemon trdSysDaemon = applicationContext.getBean(TrdSysDaemon.class);
		taskExecutor.execute(trdSysDaemon);
	}
}