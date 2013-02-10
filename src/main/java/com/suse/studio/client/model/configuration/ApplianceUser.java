package com.suse.studio.client.model.configuration;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "user", strict = false)
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

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setShell(String shell) {
        this.shell = shell;
    }

    public void setHomeDirectory(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }
}
