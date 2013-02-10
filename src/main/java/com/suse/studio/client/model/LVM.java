package com.suse.studio.client.model;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class LVM {

    @Element
    private boolean enabled;

    @Element(name = "volume_group", required = false)
    private String volumeGroup;

    @ElementList(required = false, empty = false)
    private List<Volume> volumes;

    public boolean isEnabled() {
        return enabled;
    }

    public String getVolumeGroup() {
        return volumeGroup;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setVolumeGroup(String volumeGroup) {
        this.volumeGroup = volumeGroup;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }
}
