package com.topgether.cbgrid.datagrid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.gridgain.grid.Grid;
import org.gridgain.grid.GridException;
import org.gridgain.grid.compute.GridComputeJob;
import org.gridgain.grid.compute.GridComputeJobAdapter;
import org.gridgain.grid.compute.GridComputeJobResult;
import org.gridgain.grid.compute.GridComputeTaskSession;
import org.gridgain.grid.compute.GridComputeTaskSplitAdapter;
import org.gridgain.grid.logger.GridLogger;
import org.gridgain.grid.resources.GridInstanceResource;
import org.gridgain.grid.resources.GridLoggerResource;
import org.gridgain.grid.resources.GridTaskSessionResource;
import org.jetbrains.annotations.Nullable;

import com.topgether.base.CbJob;

/**
 * 所有可在gridgain平台执行的Task
 * @author wangwei
 * @param <K> K:每个Job的key类型
 * @param <P> P:Job执行时需要的参数列表类型
 * @param <T> T:Job执行后的返回值类型
 * @param <R> R:所有Job执行后返回的数据类型
 */
public abstract class CGridTask<R, T, P> extends GridComputeTaskSplitAdapter<Serializable [],R> implements ICGrid<R, T, P> {

	private static final long serialVersionUID = 1L;
	
	@GridLoggerResource
	protected GridLogger logger;
	
	@GridInstanceResource
	protected Grid grid;
	
	@GridTaskSessionResource
	protected GridComputeTaskSession taskSession;

	@Override
	@Nullable
	public R reduce(List<GridComputeJobResult> jobResultList) throws GridException {
		return this.reduceTask(jobResultList);
	}

	@Override
	protected Collection<? extends GridComputeJob> split(int jobSize, Serializable [] args) throws GridException {
		Collection<GridComputeJob> jobs = new ArrayList<GridComputeJob>();
		Collection<CbJob<T,P>> jobList = splitJob(args);
		for (final CbJob<T,P> jobEntity : jobList) {
			
			jobs.add(new GridComputeJobAdapter () {
				
				private static final long serialVersionUID = 1L;

				@Nullable
				@Override
				public T execute() {
					System.out.println("jobEntity[key:"+jobEntity.getK()+"]>>> Executing deployment example job on this node.");
					return excuteJob(jobEntity);
				}
				
			});
		}
		return jobs;
	}
	
}
