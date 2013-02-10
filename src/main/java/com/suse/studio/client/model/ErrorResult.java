package com.suse.studio.client.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "error")
public class ErrorResult {

    @Element(required = false)
    private String code;

    @Element(required = false)
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
