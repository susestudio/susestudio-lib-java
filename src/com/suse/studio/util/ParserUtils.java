package com.suse.studio.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * A wrapper around the Simple XML library plus some utility methods for working
 * with Streams.
 */
public class ParserUtils {

    /**
     * Parse a given {@link InputStream} into a {@link Class}.
     *
     * @param clazz
     * @param stream
     * @return
     */
    public static <T> T parseBodyStream(Class<T> clazz, InputStream stream) {
        T result = null;
        Serializer serializer = new Persister();
        try {
            result = serializer.read(clazz, stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Convert a given {@link InputStream} to a {@link String}.
     *
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is) {
        return new Scanner(is).useDelimiter("\\A").next();
    }

    /**
     * Convert a given {@link String} to an {@link InputStream}.
     *
     * @param is
     * @return
     */
    public static InputStream convertStringToStream(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }
}
