package com.topgether.cbgrid.exception;

/**
 * 所有的异常都应该继承此基类异常
 * @author Administrator
 *
 */
public class CbGridException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CbGridException () {
		super();
	}
	
	public CbGridException (String msg) {
		super(msg);
	}
	
	public CbGridException (Throwable e) {
		super(e);
	}
	
	public CbGridException (String msg, Throwable e) {
		super(msg, e);
	}

}
