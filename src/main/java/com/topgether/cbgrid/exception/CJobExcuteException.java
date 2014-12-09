package com.topgether.cbgrid.exception;

/**
 * Job执行异常
 * @author Administrator
 *
 */
public class CJobExcuteException extends CJobException {

	private static final long serialVersionUID = 1L;
	
	public CJobExcuteException () {
		super();
	}
	
	public CJobExcuteException (String msg) {
		super(msg);
	}
	
	public CJobExcuteException (Throwable e) {
		super(e);
	}
	
	public CJobExcuteException (String msg, Throwable e) {
		super(msg, e);
	}

}
