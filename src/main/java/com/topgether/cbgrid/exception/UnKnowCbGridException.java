package com.topgether.cbgrid.exception;

/**
 * 末知异常
 * @author Administrator
 *
 */
public class UnKnowCbGridException extends CbGridException {

	private static final long serialVersionUID = 1L;
	
	public UnKnowCbGridException () {
		super();
	}
	
	public UnKnowCbGridException (String msg) {
		super(msg);
	}
	
	public UnKnowCbGridException (Throwable e) {
		super(e);
	}
	
	public UnKnowCbGridException (String msg, Throwable e) {
		super(msg, e);
	}

}
