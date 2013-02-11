/*
 * Copyright (c) 2012 Novell Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.suse.studio.client.model;

import java.util.Date;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Appliance {

    @Element
    private int id;

    @Element
    private String name;

    @Element(required = false)
    private String arch;

    @Element(required = false)
    private String type;

    @Element(name = "last_edited")
    private Date lastEdited;

    @Element(name = "estimated_raw_size", required = false)
    private String estimatedRawSize;

    @Element(name = "estimated_compressed_size", required = false)
    private String estimatedCompressedSize;

    @Element(name = "edit_url")
    private String editUrl;

    @Element(name = "icon_url", required = false)
    private String iconUrl;

    @Element(required = false)
    private String basesystem;

    @Element(required = false)
    private String uuid;

    @Element
    private Parent parent;

    @Element
    private Builds builds;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArch() {
        return arch;
    }

    public String getType() {
        return type;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public String getEstimatedRawSize() {
        return estimatedRawSize;
    }

    public String getEstimatedCompressedSize() {
        return estimatedCompressedSize;
    }

    public String getEditUrl() {
        return editUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getBasesystem() {
        return basesystem;
    }

    public String getUuid() {
        return uuid;
    }

    public Parent getParent() {
        return parent;
    }

    public List<Build> getBuilds() {
        return builds.getBuilds();
    }
}
