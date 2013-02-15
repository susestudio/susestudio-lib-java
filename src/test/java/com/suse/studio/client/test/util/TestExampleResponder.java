package com.suse.studio.client.test.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.simpleframework.http.Response;

import com.suse.studio.client.test.httpservermock.Responder;

/**
 * Convenience responder that outputs an XML file from the "examples" resource folder in the response body.
 */
public class TestExampleResponder implements Responder {

    /** The output XML file name. */
    private String fileName;

    /**
     * Instantiate a new responder.
     * 
     * @param fileName the XML file name
     */
    public TestExampleResponder(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @see com.suse.studio.client.test.httpservermock.Responder#respond(org.simpleframework.http.Response)
     */
    @Override
    public void respond(Response response) {
        PrintStream printStream = null;

        try {
            printStream = response.getPrintStream();
            InputStream inputStream = TestUtils.getStreamFromExampleFile(fileName);
            
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) >= 0) {
                printStream.write(buffer, 0, length);
            }
            
        } catch (IOException e) {
            // Nothing to do here, move along
            e.printStackTrace();
        } finally {
            if (printStream != null) {
                printStream.close();
            }
        }
    }
}
