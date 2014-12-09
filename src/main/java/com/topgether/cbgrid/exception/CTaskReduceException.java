package com.topgether.cbgrid.exception;

/**
 * Task调度异常
 * @author Administrator
 *
 */
public class CTaskReduceException extends CTaskException {

	private static final long serialVersionUID = 1L;
	
	public CTaskReduceException () {
		super();
	}
	
	public CTaskReduceException (String msg) {
		super(msg);
	}
	
	public CTaskReduceException (Throwable e) {
		super(e);
	}
	
	public CTaskReduceException (String msg, Throwable e) {
		super(msg, e);
	}

}
