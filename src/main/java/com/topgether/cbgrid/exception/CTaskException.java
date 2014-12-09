package com.topgether.cbgrid.exception;

/**
 * Task异常
 * @author Administrator
 *
 */
public class CTaskException extends CbGridException {

	private static final long serialVersionUID = 1L;
	
	public CTaskException () {
		super();
	}
	
	public CTaskException (String msg) {
		super(msg);
	}
	
	public CTaskException (Throwable e) {
		super(e);
	}
	
	public CTaskException (String msg, Throwable e) {
		super(msg, e);
	}

}
