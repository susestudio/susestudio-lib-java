package com.suse.studio.client.model.configuration;

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

    public void setCommand(String command) {
        this.command = command;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
