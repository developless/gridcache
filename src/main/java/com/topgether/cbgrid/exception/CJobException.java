package com.topgether.cbgrid.exception;

/**
 * Job异常
 * @author Administrator
 *
 */
public class CJobException extends CbGridException {

	private static final long serialVersionUID = 1L;
	
	public CJobException () {
		super();
	}
	
	public CJobException (String msg) {
		super(msg);
	}
	
	public CJobException (Throwable e) {
		super(e);
	}
	
	public CJobException (String msg, Throwable e) {
		super(msg, e);
	}

}
