package com.suse.studio.client.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Scripts {
	
	@Element(name="boot")
	private BootScript bootScript;
	
	@Element(name="build")
	private BuildScript buildScript;
	
	@Element(name="autoyast")
	private AutoYaSTScript autoYaSTScript;

	public BootScript getBootScript() {
		return bootScript;
	}

	public BuildScript getBuildScript() {
		return buildScript;
	}

	public AutoYaSTScript getAutoYaSTScript() {
		return autoYaSTScript;
	}
}
