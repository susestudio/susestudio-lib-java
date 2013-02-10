package com.suse.studio.client.model.configuration;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Scripts {

    @Element(name = "boot")
    private BootScript bootScript;

    @Element(name = "build")
    private BuildScript buildScript;

    @Element(name = "autoyast")
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

    public void setBootScript(BootScript bootScript) {
        this.bootScript = bootScript;
    }

    public void setBuildScript(BuildScript buildScript) {
        this.buildScript = buildScript;
    }

    public void setAutoYaSTScript(AutoYaSTScript autoYaSTScript) {
        this.autoYaSTScript = autoYaSTScript;
    }
}
