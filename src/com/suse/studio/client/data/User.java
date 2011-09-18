package com.suse.studio.client.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class User {

	@Element
	private String username;
	
	@Element
	private String displayname;
	
	@Element
	private String email;
	
	@Element(name="disk_quota")
	private DiskQuota diskQuota;
	
	public String getUsername() {
		return username;
	}
	
	public String getDisplayName() {
		return displayname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public DiskQuota getDiskQuota() {
		return diskQuota;
	}
}
