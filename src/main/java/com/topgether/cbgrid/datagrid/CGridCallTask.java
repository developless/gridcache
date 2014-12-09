package com.topgether.cbgrid.datagrid;

import com.topgether.base.CbJob;
import com.topgether.cbgrid.exception.CJobExcuteException;

/**
 * 所有可在gridgain平台执行的Task
 * @author wangwei
 * @param <K> K:每个Job的key类型
 * @param <P> P:Job执行时需要的参数列表类型
 * @param <T> T:Job执行后的返回值类型
 * @param <R> R:所有Job执行后返回的数据类型
 */
public abstract class CGridCallTask<R, T, P> extends CGridTask<R, T, P> {

	private static final long serialVersionUID = 1L;

	@Override
	public T excuteJob(CbJob<T,P> entity) throws CJobExcuteException {
		return entity.call();
	}
	
}
