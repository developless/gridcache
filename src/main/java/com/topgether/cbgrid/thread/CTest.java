package com.topgether.cbgrid.thread;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.topgether.base.CbJob;
import com.topgether.base.ICbCall;
import com.topgether.cbgrid.exception.CJobExcuteException;
import com.topgether.cbgrid.exception.CTaskException;
import com.topgether.cbgrid.exception.CTaskReduceException;

public class CTest {
	
	/**
	 * @param args
	 * @throws Exception 
	 * @throws CTaskException 
	 */
	public static void main(String[] args) throws CTaskException {
		CThread.execute(CCbJob.class, 100);
	}
	
	static class CCbJob extends CbTask<String, Long, Integer []> {

		@Override
		public Collection<CbJob<Long,Integer[]>> splitJob(Serializable... args) throws CJobExcuteException {
			List<CbJob<Long,Integer[]>> jobList = new ArrayList<CbJob<Long,Integer[]>>();
			int len = args == null ? 100 : (Integer)args[0];
			for (int i = 0; i < len; i++) {
				Integer [] job = new Integer[100000];
				for (int j = 0; j < 100000; j++) {
					job[j] = j;
				}
				jobList.add(new CbJob<Long,Integer[]>("job"+i, new ICbCall<Long,Integer[]>(){

					private static final long serialVersionUID = 1L;

					@Override
					public Long call(Integer[] p) throws CJobExcuteException {
						Long r = 0L;
						for (Integer pp : p) {
							r += pp;
						}
						return r;
					}
					
				} ,job));
			}
			return jobList;
		}

		@Override
		public String reduceTask(Collection<Long> jobResultList) throws CTaskReduceException {
			Long rr = 0L;
			for (Long r : jobResultList) {
				rr += r;
			}
			System.out.println(String.format("the result is %s: thread running",rr));
			return rr+"";
		}
		
	}


}
