package com.topgether.cbgrid.thread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.topgether.base.CbJob;
import com.topgether.cbgrid.exception.CJobExcuteException;
import com.topgether.cbgrid.exception.CTaskReduceException;

public abstract class CbTask<R, T, P> implements ICbTask<R, T, P> {
	
	@Override
	public Collection<Callable<T>> invokeAll (Collection<CbJob<T, P>> jobList) throws CJobExcuteException {
		Collection<Callable<T>> jobsList = new ArrayList<Callable<T>>();
		for (final CbJob<T, P> job : jobList) {
			jobsList.add(new Callable<T>() {
				@Override
				public T call() throws Exception {
					return job.call();
				}
			});
		}
		return jobsList;
	}
	
	@Override
	public Collection<T> reduce (Collection<Future<T>> jobResultList) throws CTaskReduceException {
		Collection <T> resultList = new ArrayList<T>();
		try {
			for (Future<T> future : jobResultList) {
				resultList.add((T)future.get());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new CTaskReduceException("callable is interrupted when reduce.");
		} catch (ExecutionException e) {
			e.printStackTrace();
			throw new CTaskReduceException("callable execute has a exceptoin when reduce.");
		}
		return resultList;
	}
	
}
