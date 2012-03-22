package com.suse.studio.client.test.util;

import java.io.InputStream;

/**
 * Utility methods for testing.
 */
public class TestUtils {

    /**
     * Return the contents of a file as {@link InputStream}.
     *
     * @param filename
     * @return stream
     */
    public static InputStream getInputStream(String filename) {
        // Get the calling class from the stacktrace
        String callerClass = Thread.currentThread().getStackTrace()[2]
                .getClassName();
        try {
            return Class.forName(callerClass).getResourceAsStream(filename);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
