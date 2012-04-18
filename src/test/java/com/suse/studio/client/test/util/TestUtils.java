package com.suse.studio.client.test.util;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.suse.studio.client.util.ParserUtils;

/**
 * Utility methods for testing.
 */
public class TestUtils {

    // Path to example files
    private static final String PATH_EXAMPLES = "/examples/";

    /**
     * Return object which is the result of parsing the given resource file.
     *
     * @param clazz
     *            class of return type
     * @param filename
     *            filename of test resource
     * @return instance of clazz
     */
    public static <T> T parseExampleFile(Class<T> clazz, String filename) {
        String path = PATH_EXAMPLES + filename;
        InputStream stream = TestUtils.class.getResourceAsStream(path);
        return ParserUtils.parseBodyStream(clazz, stream);
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
    public static Date getDate(int year, int month, int date, int hourOfDay,
            int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.set(year, month, date, hourOfDay, minute, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
