package com.suse.studio.client.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import com.suse.studio.client.exception.SUSEStudioException;

/**
 * A wrapper around core Simple XML library functionality plus some utility methods.
 * 
 * Note: you should not use this class directly as it is for internal use only.
 */
public class ParserUtils {

    /**
     * Parse a given {@link InputStream} into a {@link Class}.
     * 
     * @param clazz
     * @param stream
     * @return an object corresponding to the XML in stream
     * @throws Exception
     */
    public static <T> T parseBodyStream(Class<T> clazz, InputStream stream) throws SUSEStudioException {
        // Print stream contents for debugging
        if (StudioConfig.getInstance().printStreamContents()) {
            String s = streamToString(stream);
            System.out.println(s);
            stream = stringToStream(s);
        }

        Serializer serializer = new Persister(new AnnotationStrategy());
        T result;
        try {
            result = serializer.read(clazz, stream);
        } catch (Exception e) {
            throw new SUSEStudioException(null, "Could not parse data as type " + clazz.getName() + ": " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * Persists a given SimpleXML-annotated object into a {@link OutputStream}.
     * 
     * @param object a SimpleXML-annotated object
     * @param stream a stream containing object serialized in XML
     */
    public static void persistInStream(Object object, OutputStream stream) {
        Serializer serializer = new Persister(new AnnotationStrategy());
        try {
            serializer.write(object, stream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Print stream contents for debugging
        if (StudioConfig.getInstance().printStreamContents()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                serializer.write(object, outputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(outputStream.toString());
        }
    }

    /**
     * Convert a given {@link String} to an {@link InputStream}.
     * 
     * @param s a string
     * @return an input stream on the string
     */
    public static InputStream stringToStream(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }

    /**
     * Convert a given {@link InputStream} to a {@link String}.
     * 
     * @param is an input stream
     * @return the string in the input stream
     */
    public static String streamToString(InputStream is) {
        Scanner scanner = new Scanner(is);
        String ret = scanner.useDelimiter("\\A").next();
        scanner.close();
        return ret;
    }

    /**
     * Splits a comma-separated string into a List.
     * 
     * @param string the string to split
     * @return the splitted string
     */
    public static List<String> commaSeparatedStringToList(String string) {
        return Arrays.asList(string.split(", ?"));
    }

    /**
     * Generated a comma-separated string from a List.
     * 
     * @param list the list to join
     * @return the joint string
     */
    public static String listToCommaSeparatedString(List<String> list) {
        if (list.size() == 0) {
            return null;
        } else {
            Iterator<String> i = list.iterator();
            StringBuilder builder = new StringBuilder(i.next());
            while (i.hasNext()) {
                builder.append(i.next());
            }
            return builder.toString();
        }
    }
}
