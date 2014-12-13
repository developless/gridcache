package com.topgether.base;

import java.io.Serializable;

public class CbVersion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String version;
	
	public CbVersion () {
		this.version = System.currentTimeMillis()+"";
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
