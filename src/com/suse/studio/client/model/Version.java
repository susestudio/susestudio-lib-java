package com.suse.studio.client.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Text;

@Element
public class Version {

    @Text
    String value;

    public String getValue() {
        return value;
    }
}
