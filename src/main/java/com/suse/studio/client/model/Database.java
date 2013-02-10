package com.suse.studio.client.model;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Database {
	@Element
	private String type;

	@ElementList(name = "users", entry = "user", empty = false, required = false)
	private List<DatabaseUser> databaseUsers;

	public String getType() {
		return type;
	}

	public List<DatabaseUser> getDatabaseUsers() {
		return databaseUsers;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDatabaseUsers(List<DatabaseUser> databaseUsers) {
		this.databaseUsers = databaseUsers;
	}	
}
