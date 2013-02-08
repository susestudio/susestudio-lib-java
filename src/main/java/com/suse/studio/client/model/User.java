package com.suse.studio.client.model;

import java.util.Date;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class User {

	@Element
	private String username;
	
	@Element
	private String displayname;
	
	@Element
	private String email;

	@Element(name="created_at")
	private Date createdAt;
	
	@ElementList(name="openid_urls", entry="openid_url", empty=false, required=false)
	private List<String> openIdUrls;

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
	
	public Date getCreatedAt() {
        return createdAt;
    }

	public List<String> getOpenIdUrls() {
		return openIdUrls;
	}

	public DiskQuota getDiskQuota() {
		return diskQuota;
	}
}
