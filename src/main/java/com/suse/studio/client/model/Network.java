package com.suse.studio.client.model;

import java.util.Arrays;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Network {
	
    @Element
    private String type;

    @Element(required=false)
    private String hostname;

    @Element(required=false)
    private String ip;

    @Element(required=false)
    private String netmask;

    @Element(required=false)
    private String route;

    @Element(name="nameservers", required=false)
    private String nameServerString;

	public String getType() {
		return type;
	}

	public String getHostname() {
		return hostname;
	}

	public String getIp() {
		return ip;
	}

	public String getNetmask() {
		return netmask;
	}

	public String getRoute() {
		return route;
	}

	public List<String> getNameServers() {
		return Arrays.asList(nameServerString.split(", ?"));
	}
}
