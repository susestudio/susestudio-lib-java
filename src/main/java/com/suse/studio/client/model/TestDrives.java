package com.suse.studio.client.model;

 import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class TestDrives {

	@Element
	private TestDrive testDrive;
	
	
	public TestDrive getTestDrive() {
		return testDrive;
	}
	
	
	
	
}
	
