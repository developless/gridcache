package com.topgether.base;

import java.io.Serializable;

/**
 * 每个Job对应该的参数实体，提供Job的参数，CGridTask根据Job参数自动拆分任务和构造Job实体
 * @author Administrator
 *
 * @param <K> K：每个Job实体的标识
 * @param <P> P：每个Job要传给可执行单元的参数列表类型
 */
public class JobEntity<K, P> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private K k;
	
	private P p;
	
	public JobEntity(K k, P p) {
		this.k = k;
		this.p = p;
	}
	
	public K getK() {
		return k;
	}

	public void setK(K k) {
		this.k = k;
	}

	public P getP() {
		return p;
	}

	public void setP(P p) {
		this.p = p;
	}

}
