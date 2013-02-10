package com.suse.studio.client.model;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Testdrives {

    @ElementList(required = false, inline = true, name = "testdrive", empty = false)
    private List<Testdrive> testdrives;

    public List<Testdrive> getTestdrives() {
        return testdrives;
    }
}
