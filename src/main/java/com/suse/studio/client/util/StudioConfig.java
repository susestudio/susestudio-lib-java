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

package com.suse.studio.client.util;

import java.util.Properties;

/**
 * Utility class for providing access to properties needed during runtime.
 */
public class StudioConfig {

    // Keys to be used
																//check data
    public static String KEY_BASE_URL = "baseURL";
    public static String KEY_ENCODED_CREDS = "encodedCreds";

    // Default values
    private static final String DEFAULT_URL = "http://susestudio.com/api/v2";

    // The properties object
    private Properties properties;

    /**
     * Constructor.
     */
    public StudioConfig() {
        this.properties = new Properties();
    }

    /**
     * Set a preference given by key and value. Use one of the public key
     * strings above.
     *
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * Return the base URL or the default.
     *
     * @return base URL
     */
    public String getBaseURL() {
        return properties.getProperty(KEY_BASE_URL, DEFAULT_URL);
    }

    /**
     * Return the encoded credentials or null.
     *
     * @return credentials
     */
    public String getEncodedCredentials() {
        return properties.getProperty(KEY_ENCODED_CREDS, null);
    }
}
