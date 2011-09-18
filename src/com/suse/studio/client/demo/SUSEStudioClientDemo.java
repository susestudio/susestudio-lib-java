/*
 * Copyright (c) 2011 Novell Inc.
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

package com.suse.studio.client.demo;

import java.io.IOException;
import java.util.List;

import com.suse.studio.client.SUSEStudioClient;
import com.suse.studio.client.data.Appliance;
import com.suse.studio.client.data.Build;

/**
 * Demo program for testing the SUSE Studio client library.
 * 
 * @author Johannes Renner
 */
public class SUSEStudioClientDemo {
	/**
	 * Takes the credentials (user and API key) as arguments.
	 *
	 * @param args
	 */
	
	public static final String user = "";
	public static final String key = "";
	public static void main(String[] args) {
		// Check the number of arguments
		
		// Create the client object and do something with it
		SUSEStudioClient client = new SUSEStudioClient(user, key);
		try {
			List<Appliance> appliances = client.getAppliances();
			for (Appliance a : appliances) {
				System.out.println("---------- Appliance ----------");
				System.out.println("Name: " + a.getName());
				for(Build b : a.getBuilds()) {
					System.out.println(b.getDownloadUrl());
				}
				
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
