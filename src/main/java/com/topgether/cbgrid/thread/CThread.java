package com.topgether.cbgrid.thread;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.Future;

import com.topgether.cbgrid.exception.CJobExcuteException;


public class CThread {
	
	private final static CbThreadPool threadPool = new CbThreadPool(CbThreadPool.getCpuCoreNum(), 2000, 2, 200);
	
	public static <R, T, P> R execute (Class<? extends ICbTask<R, T, P>> clazz, Serializable ... args) {
		ICbTask<R, T, P> task;
		try {
			task = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new  CJobExcuteException("Class loading failed.");
		} 
		Collection<Future<T>> result;
		try {
			result = threadPool.invokeAll(task.invokeAll(task.splitJob(args)));
		} catch (CJobExcuteException e1) {
			throw e1;
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new  CJobExcuteException("callable is interrupted when invoke.");
		}
		
		while (!threadPool.isAllTaskCompleted()) {
			try {
				Thread.sleep(1000);
				System.out.println(String.format("there still have %s: thread running",threadPool.getActiveCount()));
			} catch (InterruptedException e) {
				e.printStackTrace();
				threadPool.shutdown();
				throw new  CJobExcuteException("callable is interrupted when monitoring.");
			}
		}
		R r = task.reduceTask(task.reduce(result));
		threadPool.shutdown();
		return r;
	}
	
}
