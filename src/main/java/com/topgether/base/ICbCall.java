package com.topgether.base;

import java.io.Serializable;

import com.topgether.cbgrid.exception.CJobExcuteException;

public interface ICbCall<T, P> extends Serializable {

	public abstract T call (P p) throws CJobExcuteException; 
	
}
