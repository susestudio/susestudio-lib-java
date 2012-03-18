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

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Build {

    @Element
    private int id;

    @Element
    private String version;

    @Element(name = "image_type")
    private String imageType;

    @Element(name = "image_size")
    private String imageSize;

    @Element(name = "compressed_image_size")
    private String compressedImageSize;

    @Element(name = "download_url")
    private String downloadUrl;

    public int getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public String getImageType() {
        return imageType;
    }

    public String getImageSize() {
        return imageSize;
    }

    public String getCompressedImageSize() {
        return compressedImageSize;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }
}
