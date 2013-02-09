package com.suse.studio.client.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root
public class Locale {
	
	@Element(name = "keyboard_layout", required = false)
	private String keyboardLayout;

	@Element(required = false)
	private String language;

	@Element
	@Path("timezone")
	private String location;

	public String getKeyboardLayout() {
		return keyboardLayout;
	}

	public String getLanguage() {
		return language;
	}

	public String getLocation() {
		return location;
	}
}
