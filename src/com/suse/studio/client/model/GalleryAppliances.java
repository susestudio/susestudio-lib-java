/*
 * Copyright (c) 2012 Novell
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

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "appliances")
public class GalleryAppliances {

    @Attribute
    private int count;

    @Element
    private int pages;

    @Element
    private int current_page;

    @ElementList(name = "appliance", inline = true, required = false)
    private List<GalleryAppliance> appliance;

    public int getCount() {
        return this.count;
    }

    public int getPages() {
        return this.pages;
    }

    public int getCurrentPage() {
        return this.current_page;
    }

    public List<GalleryAppliance> getAppliances() {
        if (appliance == null) {
            appliance = new ArrayList<GalleryAppliance>();
        }
        return this.appliance;
    }
}
