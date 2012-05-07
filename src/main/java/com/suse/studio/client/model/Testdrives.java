package com.suse.studio.client.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class Testdrives {

	@ElementList
	private List<Testdrive> testdrive;
	
	
	public List<Testdrive> getTestdrive() {
	    if (testdrive == null) {
				testdrive = new ArrayList<Testdrive>();
	    }
	    return testdrive;
	}
	
	
	
	
}
	
