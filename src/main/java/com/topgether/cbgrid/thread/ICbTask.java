package com.topgether.cbgrid.thread;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.topgether.base.CbJob;
import com.topgether.cbgrid.exception.CJobExcuteException;
import com.topgether.cbgrid.exception.CTaskReduceException;

public interface ICbTask<R, T, P> {
	
	public Collection<Callable<T>> invokeAll (Collection<CbJob<T, P>> jobList) throws CJobExcuteException;

	/**
	 * 每个Task的运行结果调度
	 * @param jobResultList
	 * @return
	 * @throws CTaskReduceException
	 */
	public abstract R reduceTask (Collection<T> jobResultList) throws CTaskReduceException;
	
	/**
	 * 每个Task的运行结果调度
	 * @param jobResultList
	 * @return
	 * @throws CTaskReduceException
	 */
	public abstract Collection<T> reduce (Collection<Future<T>> jobResultList) throws CTaskReduceException;
	
	/**
	 * 每个Job的可执行单元
	 * @param args
	 * @return
	 * @throws CJobExcuteException
	 */
	public abstract Collection<CbJob<T, P>> splitJob (Serializable ... args) throws CJobExcuteException;
	
}

