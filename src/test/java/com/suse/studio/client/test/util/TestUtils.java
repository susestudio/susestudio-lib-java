/**
 * Copyright (c) 2011-2013 SUSE
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

package com.suse.studio.client.test.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import com.suse.studio.client.util.ParserUtils;

/**
 * Utility methods for testing.
 */
public class TestUtils {

    // Path to example files
    private static final String PATH_EXAMPLES = "/com/suse/studio/client/test/examples/";

    /**
     * Return object which is the result of parsing the given resource file.
     * 
     * @param clazz class of return type
     * @param filename filename of test resource
     * @return instance of clazz
     */
    public static <T> T parseExampleFile(Class<T> clazz, String filename) {
        InputStream stream = getStreamFromExampleFile(filename);
        try {
            return ParserUtils.parseBodyStream(clazz, stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Return an example resource file as a stream.
     * 
     * @param filename filename of test resource
     * @return an input stream
     */    
    public static InputStream getStreamFromExampleFile(String filename) {
        String path = PATH_EXAMPLES + filename;
        InputStream stream = TestUtils.class.getResourceAsStream(path);
        return stream;
    }

    /**
     * Persists a given SimpleXML-annotated object into a String.
     * 
     * @param object the object to serialize
     * @return the string
     */
    public static String persistInString(Object object) {
        Serializer serializer = new Persister(new AnnotationStrategy());
        String result = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            serializer.write(object, baos);
            result = baos.toString("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Return a UTC {@link Date} specified by the parameters.
     * 
     * @param year
     * @param month
     * @param date
     * @param hourOfDay
     * @param minute
     * @param second
     * @return date object
     */
    public static Date getDate(int year, int month, int date, int hourOfDay, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.set(year, month, date, hourOfDay, minute, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
