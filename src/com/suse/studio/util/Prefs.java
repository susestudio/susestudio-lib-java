/*
 * Copyright (c) 2011 Novell Inc.
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

package com.suse.studio.util;

import java.util.prefs.Preferences;

/**
 * Utility class for providing access to properties needed during runtime.
 *
 * @author jrenner
 */
public class Prefs {

    // Keys to be used
    public static String BASE_URL = "baseURL";

    // Default values
    private static final String DEFAULT_URL = "http://susestudio.com/api/v2";

    // The Preferences node
    private static Preferences prefs = Preferences.userRoot().node(
            Prefs.class.getName());

    /**
     * Set a preference given by key and value. Use one of the public key
     * strings above.
     *
     * @param key
     * @param value
     */
    public static void put(String key, String value) {
        prefs.put(key, value);
    }

    /**
     * Return the base URL or the default.
     *
     * @return base URL
     */
    public static String getBaseURL() {
        return prefs.get(BASE_URL, DEFAULT_URL);
    }
}
