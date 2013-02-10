package com.suse.studio.client.model.configuration;

import org.simpleframework.xml.Element;

public class Script {
    @Element
    private boolean enabled;

    @Element(required = false)
    private String script;

    public boolean isEnabled() {
        return enabled;
    }

    public String getScript() {
        return script;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
