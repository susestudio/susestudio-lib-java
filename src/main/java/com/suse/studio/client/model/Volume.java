package com.suse.studio.client.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Volume {
	
	@Element(required=false)
	private int size;
	
	@Element
	private String path;

	public int getSize() {
		return size;
	}

	public String getPath() {
		return path;
	}
}
