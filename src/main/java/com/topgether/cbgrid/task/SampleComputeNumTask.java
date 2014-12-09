package com.topgether.cbgrid.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.gridgain.grid.GridException;
import org.gridgain.grid.cache.GridCache;
import org.gridgain.grid.cache.GridCacheEntry;
import org.gridgain.grid.compute.GridComputeJobResult;
import org.gridgain.grid.compute.GridComputeTaskName;

import com.topgether.base.CbJob;
import com.topgether.base.ICbCall;
import com.topgether.cbgrid.datagrid.CGridCallTask;
import com.topgether.cbgrid.exception.CJobExcuteException;
import com.topgether.cbgrid.exception.CTaskReduceException;

@GridComputeTaskName("SampleComputeNumTask")
public class SampleComputeNumTask extends CGridCallTask<String, Long, Integer[]> {

	private static final long serialVersionUID = 1L;

	@Override
	public String reduceTask(List<GridComputeJobResult> jobResultList)
			throws CTaskReduceException {
		Long result = 0L;
		for (GridComputeJobResult jresult : jobResultList) {
			result += (Long) jresult.getData();
		}
		System.out.println("计算结果为：" + result);
		return result.toString();
	}

	@Override
	public Collection<CbJob<Long, Integer[]>> splitJob(Serializable... args) throws CJobExcuteException {
		Collection<CbJob<Long, Integer[]>> list = new ArrayList<CbJob<Long, Integer[]>>();
		loadCache(args);
		GridCache<Integer, Integer[]> cache = grid.cache("share-hqfx");
		for (GridCacheEntry<Integer, Integer[]> entry : cache.entrySet()) {
			list.add(new CbJob<Long, Integer[]>(entry.getKey(), new MyCall(), entry.getValue()));
		}
		return list;
	}

	@Override
	public void loadCache(Serializable... args) throws CJobExcuteException {
		GridCache<Integer, Integer[]> cache = grid.cache("share-hqfx");
		cache.clearAll();
		for (int i = 0; i < 100; i++) {
			try {
				System.out.println("Key:"+i);
				cache.putx(i, new Integer[]{i,i+1});
			} catch (GridException e) {
				e.printStackTrace();
			}
		}
	}

	class MyCall implements ICbCall<Long, Integer[]> {

		private static final long serialVersionUID = 1L;

		@Override
		public Long call(Integer[] p) throws CJobExcuteException {
			Long r = 0L;
			for (Integer pp : p) {
				r += pp;
			}
			return r;
		}
	}

}