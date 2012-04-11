package com.suse.studio.client.test.util;

import java.io.File;
import java.io.InputStream;

import com.suse.studio.client.util.ParserUtils;

/**
 * Utility methods for testing.
 */
public class TestUtils {

	private static String pathToTestResources = "resources" + File.separator;

	/**
	 * Return the contents of a file as {@link InputStream}.
	 * 
	 * @param filename
	 * @return stream
	 */
	private static InputStream getInputStream(String filename) {
		// Get the calling class from the stacktrace
		String callerClass = Thread.currentThread().getStackTrace()[2]
				.getClassName();
		try {
			return Class.forName(callerClass).getResourceAsStream(filename);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Return object which is the result of parsing the test resource file
	 * 
	 * @param requeredClass
	 *            class which will be returned
	 * @param testResource
	 *            name of the test resource
	 * @return
	 */
	public static <T> T getTestResource(Class<T> requeredClass,
			String testResource) {
		return ParserUtils.parseBodyStream(requeredClass,
				getInputStream(pathToTestResources + testResource));
	}
}
