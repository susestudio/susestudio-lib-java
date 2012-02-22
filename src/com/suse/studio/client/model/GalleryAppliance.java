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

import java.util.Date;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "appliance")
public class GalleryAppliance {

    @Element
    private int id;

    @Element
    private String name;

    @Element
    private String publisher;

    @Element
    private String username;

    @Element(required = false)
    private String homepage;

    @Element(required = false)
    private String description;

    @Element
    private int ratings;

    @Element(name = "average_rating")
    private double averageRating;

    @Element
    private int comments;

    @Element(name = "based_on")
    private String basedOn;

    @Element
    private Date date;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getUsername() {
        return username;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getDescription() {
        return description;
    }

    public int getRatings() {
        return ratings;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getBasedOn() {
        return basedOn;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
