package com.topgether.cbgrid.exception;

/**
 * Job拆分异常
 * @author Administrator
 *
 */
public class CJobSplitException extends CJobException {

	private static final long serialVersionUID = 1L;
	
	public CJobSplitException () {
		super();
	}
	
	public CJobSplitException (String msg) {
		super(msg);
	}
	
	public CJobSplitException (Throwable e) {
		super(e);
	}
	
	public CJobSplitException (String msg, Throwable e) {
		super(msg, e);
	}

}
