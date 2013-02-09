package com.suse.studio.client.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="user", strict = false)
public class ApplianceUser {
	
	@Element(required = false)
	private int uid;
	
	@Element
	private String name;
	
	@Element(required = false)
	private String password;
	
	@Element
	private String group;
	
	@Element
	private String shell;

	@Element(name = "homedir")
	private String homeDirectory;

	public int getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getGroup() {
		return group;
	}

	public String getShell() {
		return shell;
	}

	public String getHomeDirectory() {
		return homeDirectory;
	}
}
