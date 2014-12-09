package com.topgether.cbgrid.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 当添加任务失败后的执行策略
 * @author Administrator
 *
 */
public class CbRejectedExecutionHandler implements RejectedExecutionHandler {

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		if (executor.isShutdown()) {
			new ThreadPoolExecutor.AbortPolicy();
		} else {
			((CbThreadPool)executor).getLevel2Queue().add(r);
		}
	}
	
}
