package com.topgether.cbgrid.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gridgain.grid.cache.GridCache;
import org.gridgain.grid.cache.GridCacheEntry;
import org.gridgain.grid.cache.GridCacheProjection;
import org.gridgain.grid.compute.GridComputeJobResult;
import org.gridgain.grid.compute.GridComputeTaskName;

import com.topgether.base.CbJob;
import com.topgether.base.ICbCall;
import com.topgether.cbgrid.datagrid.CGridCallTask;
import com.topgether.cbgrid.exception.CJobExcuteException;
import com.topgether.cbgrid.exception.CTaskReduceException;

@GridComputeTaskName("PortableComputeNumTask")
public class PortableComputeNumTask extends CGridCallTask<String, Long, Integer[]> {

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
	public Collection<CbJob<Long, Integer[]>> splitJob(Serializable... args)
			throws CJobExcuteException {
		loadCache(args);
		Collection<CbJob<Long, Integer[]>> list = new ArrayList<CbJob<Long, Integer[]>>();
		GridCacheProjection<Serializable, Serializable> project = grid.cache("share-data");
		for (GridCacheEntry<Serializable,Serializable> entry : project.entrySet()) {
			list.add(new CbJob<Long, Integer[]>(Long.parseLong(entry.getKey().toString()), new MyCall(), (Integer [])entry.getValue()));
		}
		return list;
	}

	private class MyCall implements ICbCall<Long, Integer[]> {

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
	
	@Override
	public void loadCache(Serializable... args) throws CJobExcuteException {
		System.out.println("XXOO==loading cache begin");
		GridCache<Serializable, Serializable> cache = grid.cache("share-data");
		Map<Serializable, Serializable> map = new HashMap<Serializable, Serializable>();		
		int j = 0;
		int a = 0;
		int len = args == null ? 1000 : (Integer) args[0];
		Integer[] arg = new Integer[len];
		try {
			for (int i = 1; i < 1000000; i++) {
				arg[a] = i;
				a++;
				if (a == 1000 || i == 1000000) {
					++j;
					map.put(j, arg);
					a = 0;
					arg = new Integer[1000];
				}
			}
			cache.putAll(map);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		System.out.println("XXOO==loading cache end");
	}

}