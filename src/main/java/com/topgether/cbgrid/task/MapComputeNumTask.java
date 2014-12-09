package com.topgether.cbgrid.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gridgain.grid.GridException;
import org.gridgain.grid.cache.GridCache;
import org.gridgain.grid.cache.GridCacheEntry;
import org.gridgain.grid.compute.GridComputeJobResult;
import org.gridgain.grid.compute.GridComputeTaskName;
import org.gridgain.grid.util.GridClassLoaderCache;

import com.topgether.base.CbJob;
import com.topgether.base.ICbCall;
import com.topgether.base.JobEntity;
import com.topgether.cbgrid.datagrid.CGridCallTask;
import com.topgether.cbgrid.exception.CJobExcuteException;
import com.topgether.cbgrid.exception.CTaskReduceException;

@GridComputeTaskName("MapComputeNumTask")
public class MapComputeNumTask extends CGridCallTask<String, Long, Integer[]> {

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
		GridClassLoaderCache.printMemoryStats();
		loadCache(args);
		GridCache<Integer, JobEntity<Integer, Integer[]>> cache = grid.cache("share-hqfx");
		for (GridCacheEntry<Integer,JobEntity<Integer, Integer[]>> entry : cache.entrySet()) {
			list.add(new CbJob<Long, Integer[]>(entry.getKey(), new MyCall(), entry.getValue().getP()));
		}
		GridClassLoaderCache.printMemoryStats();
		return list;
	}

	@Override
	public void loadCache(Serializable... args) throws CJobExcuteException {
		GridCache<Integer, JobEntity<Integer, Integer[]>> cache = grid.cache("share-hqfx");
		Map<Integer,JobEntity<Integer, Integer[]>> map = new HashMap<Integer,JobEntity<Integer, Integer[]>>();
		int j = 0;
		int a = 0;
		int len = args == null ? 1000 : (Integer) args[0];
		Integer[] arg = new Integer[len];
		for (int i = 1; i < 1000000; i++) {
			arg[a] = i;
			a++;
			if (a == 1000 || i == 1000000) {
				++j;
				map.put(j, new JobEntity<Integer, Integer[]>(j,arg));
				a = 0;
				arg = new Integer[1000];
			}
		}
		try {
			cache.putAll(map);
		} catch (GridException e) {
			e.printStackTrace();
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