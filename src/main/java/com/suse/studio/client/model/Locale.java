package com.suse.studio.client.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root
public class Locale {

    @Element(name = "keyboard_layout", required = false)
    private String keyboardLayout;

    @Element(required = false)
    private String language;

    @Element
    @Path("timezone")
    private String location;

    public String getKeyboardLayout() {
        return keyboardLayout;
    }

    public String getLanguage() {
        return language;
    }

    public String getLocation() {
        return location;
    }

    public void setKeyboardLayout(String keyboardLayout) {
        this.keyboardLayout = keyboardLayout;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
