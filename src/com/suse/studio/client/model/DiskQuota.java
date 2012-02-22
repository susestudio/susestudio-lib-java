package com.suse.studio.client.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class DiskQuota {

	@Element
	private String available;
	
	@Element
	private String used;
	
	public String getAvailable() {
		return available;
	}
	
	public String getUsed() {
		return used;
	}
}
