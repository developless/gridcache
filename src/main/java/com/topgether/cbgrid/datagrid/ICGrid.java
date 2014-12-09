package com.topgether.cbgrid.datagrid;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.gridgain.grid.compute.GridComputeJobResult;
import org.gridgain.grid.compute.GridComputeTask;

import com.topgether.base.CbJob;
import com.topgether.cbgrid.exception.CJobExcuteException;
import com.topgether.cbgrid.exception.CTaskReduceException;

public interface ICGrid<R, T, P> extends GridComputeTask<Serializable [], R> {
	
	/**
	 * 每个Job的可执行单元
	 * @param args
	 * @return
	 * @throws CJobExcuteException
	 */
	public abstract T excuteJob(CbJob<T,P> entity) throws CJobExcuteException;

	/**
	 * 每个Task的运行结果调度
	 * @param jobResultList
	 * @return
	 * @throws CTaskReduceException
	 */
	public abstract R reduceTask(List<GridComputeJobResult> jobResultList) throws CTaskReduceException;
	
	/**
	 * 每个Job的可执行单元
	 * @param args
	 * @return
	 * @throws CJobExcuteException
	 */
	public abstract Collection<CbJob<T,P>> splitJob (Serializable ... args) throws CJobExcuteException;
	
	public abstract void loadCache (Serializable ... args) throws CJobExcuteException;
	
	
}
