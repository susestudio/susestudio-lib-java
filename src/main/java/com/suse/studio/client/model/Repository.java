/**
 * Copyright (c) 2013 SUSE
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

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

@Root(strict = false)
public class Repository {

    @Element
    private int id;

    @Element
    private String name;

    @Element(required = false)
    private String type;

    @Element(name = "repotag", required = false)
    private String repoTag;

    @Element(name = "base_system", required = false)
    private String baseSystem;

    @Element(name = "base_url", required = false)
    private String baseUrl;

    @Element(name = "smt_name", required = false)
    private String smtName;

    @Element(name = "smt_target", required = false)
    private String smtTarget;

    @Element(required = false)
    @Convert(Match.ListConverter.class)
    private List<Match> matches;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRepoTag() {
        return repoTag;
    }

    public String getBaseSystem() {
        return baseSystem;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getSMTName() {
        return smtName;
    }

    public String getSMTTarget() {
        return smtTarget;
    }

    public List<Match> getMatches() {
        return matches;
    }
}
