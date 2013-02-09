package com.suse.studio.client.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Autostart {

	@Element
	private String command;
	
	@Element
	private String description;
	
	@Element
	private boolean enabled;
	
	@Element
	private String user;

	public String getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getUser() {
		return user;
	}
}
