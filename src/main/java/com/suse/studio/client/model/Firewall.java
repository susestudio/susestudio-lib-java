package com.suse.studio.client.model;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Firewall {

	@Element
	private boolean enabled;

	@ElementList(entry = "open_port", inline = true, required = false, empty = false)
	private List<String> openPorts;

	public boolean isEnabled() {
		return enabled;
	}

	public List<String> getOpenPorts() {
		return openPorts;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setOpenPorts(List<String> openPorts) {
		this.openPorts = openPorts;
	}	
}
