package com.topgether.base;

import java.io.Serializable;

/**
 * 每个Job对应该的参数实体，提供Job的参数，CGridTask根据Job参数自动拆分任务和构造Job实体
 * @author Administrator
 *
 * @param <K> K：每个Job实体的标识
 * @param <P> P：每个Job要传给可执行单元的参数列表类型
 */
public class CbJob<T, P> extends JobEntity<Serializable, P> {

	private static final long serialVersionUID = 1L;
	
	private ICbCall<T,P> callable;
	
	public CbJob(Serializable k, ICbCall<T,P> callable, P p) {
		super(k,p);
		this.callable = callable;
	}
	
	public T call() {
		return this.callable == null ? null : this.callable.call(this.getP());
	}
	
	public ICbCall<T, P> getCallable() {
		return callable;
	}

	public void setCallable(ICbCall<T, P> callable) {
		this.callable = callable;
	}

}
