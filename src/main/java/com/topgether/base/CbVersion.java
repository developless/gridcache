package com.topgether.base;

import java.io.Serializable;

public class CbVersion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static String version;
	
	static {
		version = System.currentTimeMillis()+"";
	}

}
