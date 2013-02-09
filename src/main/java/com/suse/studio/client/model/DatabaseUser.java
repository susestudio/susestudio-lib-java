package com.suse.studio.client.model;

import java.util.Arrays;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "user")
public class DatabaseUser {

	@Element
	private String username;

	@Element
	private String password;

	@Element(name = "database_list", required = false)
	private String databasesString;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public List<String> getDatabases() {
		return Arrays.asList(databasesString.split(", ?"));
	}
}
