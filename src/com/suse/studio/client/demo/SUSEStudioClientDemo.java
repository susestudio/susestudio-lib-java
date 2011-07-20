/**
 * 
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
	public static void main(String[] args) {
		// Check the number of arguments
		if (args.length != 2) {
			System.out.println("Wrong number of arguments!");
			System.out.println("Usage: java -jar SUSEStudioClient.jar <user> <apikey>");
			System.exit(1);
		}
		// Create the client object and do something with it
		SUSEStudioClient client = new SUSEStudioClient(args[0], args[1]);
		try {
			List<Appliance> appliances = client.getAppliances();
			for (Appliance a : appliances) {
				System.out.println("---------- Appliance ----------");
				System.out.println("Name: " + a.getName());
				System.out.println("Type: " + a.getType());
				for (Build b : a.getBuilds()) {
					System.out.println("- Build:");
					System.out.println("ID: " + b.getId());
					System.out.println("Version: " + b.getVersion());
					System.out.println("Image Type: " + b.getImageType());
					System.out.println("Download URL: " + b.getDownloadURL());
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
