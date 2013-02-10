package com.suse.studio.client.model.configuration;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.suse.studio.client.util.ParserUtils;

@Root
public class Network {

    @Element
    private String type;

    @Element(required = false)
    private String hostname;

    @Element(required = false)
    private String ip;

    @Element(required = false)
    private String netmask;

    @Element(required = false)
    private String route;

    @Element(name = "nameservers", required = false)
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
        return ParserUtils.commaSeparatedStringToList(nameServerString);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setNameServers(List<String> nameServers) {
        this.nameServerString = ParserUtils.listToCommaSeparatedString(nameServers);
    }
}
