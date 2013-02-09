package com.suse.studio.client.model;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Configuration {

	@Element
	private int id;

	@Element
	private String name;

	@Element(required = false)
	private String description;

	@Element
	private String version;

	@Element(required = false)
	private String type;

	@Element(required = false)
	private String website;

	@ElementList(entry="tag", empty = false, required = false)
	private List<String> tags;

	@Element
	private Locale locale;

	@Element
	private Network network;

	@Element
	private Firewall firewall;

	@ElementList(name = "users", entry = "user")
	private List<ApplianceUser> applianceUsers;

	@ElementList(entry = "eula", empty = false, required = false)
	private List<String> eulas;

	@ElementList(empty = false, required = false)
	private List<Database> databases;

	@ElementList(empty = false, required = false)
	private List<Autostart> autostarts;

	@Element
	private Settings settings;

	@Element
	private LVM lvm;

	@Element
	private Scripts scripts;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getVersion() {
		return version;
	}

	public String getType() {
		return type;
	}

	public String getWebsite() {
		return website;
	}

	public List<String> getTags() {
		return tags;
	}

	public Locale getLocale() {
		return locale;
	}

	public Network getNetwork() {
		return network;
	}

	public Firewall getFirewall() {
		return firewall;
	}

	public List<ApplianceUser> getApplianceUsers() {
		return applianceUsers;
	}

	public List<String> getEulas() {
		return eulas;
	}

	public List<Database> getDatabases() {
		return databases;
	}

	public List<Autostart> getAutostarts() {
		return autostarts;
	}

	public Settings getSettings() {
		return settings;
	}

	public LVM getLvm() {
		return lvm;
	}

	public Scripts getScripts() {
		return scripts;
	}
}
