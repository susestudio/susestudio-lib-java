package com.suse.studio.client.model;

 import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class TestDrive {

	@Element
	private String state;
	
	@Element
	private String id;

	@Element(name="build_id")
	private String buildId;
	
	public String getState() {
		return state;
	}
	
	public String getBuildId() {
		return buildId;
	}
	
	public String getId() {
		return id;
	}
	
	
}
