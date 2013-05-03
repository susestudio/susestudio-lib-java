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
