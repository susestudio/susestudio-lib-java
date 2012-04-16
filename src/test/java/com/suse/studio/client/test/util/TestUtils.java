package com.suse.studio.client.test.util;

import java.io.InputStream;

import com.suse.studio.client.util.ParserUtils;

/**
 * Utility methods for testing.
 */
public class TestUtils {

    // Path to example files
    private static final String PATH_EXAMPLES =  "/examples/";

    /**
     * Return the contents of a file as {@link InputStream}.
     *
     * @param path
     * @return stream
     */
    private static InputStream getInputStream(String path) {
        return TestUtils.class.getResourceAsStream(path);
    }

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
        T ret = ParserUtils.parseBodyStream(clazz, getInputStream(PATH_EXAMPLES
                + filename));
        return ret;
    }
}
