/**
  * VerifyUser.java
  * Created by Matthew A. Crist on March 28, 2013.
  * This file is designed to replace the verify-user.sh script for more flexibility.
  * PREVIOUS DOCUMENTATION:
   # verify-user.sh
	# Created on March 23, 2013 by Matthew A. Crist.
	# This file will invoke the script required to verify that a user should 
	# have access to an inbox and open a connection to send those files to the
	# client.
	#
	# THIS MODULE RELIES HEAVILY ON THE CREATED DIRECTORIES FOR MAIL FOLDERS 
	# ON USER REGISTRATION.  IF THIS FOLDER DOES NOT EXIST, THE VERIFICATION
	# PROCESS IS POINTLESS.  A RESPONSE WILL BE USED ON THE SERVER SIDE
	# ACCEPT(user.verify) or REJECT(user.verify) TO RESPOND BACK TO THE CLIENT
	# IF IT IS ACCEPTABLE TO SEND/RECIEVE THE EMAIL IN THEIR INBOX.  WHEN THAT	
	# EMAIL IS SENT, IT IS REMOVED FROM THE SERVER ENTIRELY.
	#
	# CHANGE LOG:
	# ------------------------------------------------------------------------
	# 2013-03-23	-	Initial conception of this file.
*/

package modules.user.verify;

import java.io.*;
import java.util.*;
import java.net.*;

public class VerifyUser {
	public static void main(String[] args) {
		boolean listening = true;

		try {
			ServerSocket server = new ServerSocket(20002);

			while(listening) {
				Socket client = server.accept();

				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

				String input, store = "", request = "";

				// For all input received, write it to the request file.
				while((input = in.readLine()) != null) {
					request += input;
				}	// end while

				BufferedReader reader = new BufferedReader(new FileReader("store/address-book/address-book.xml"));
		
				while((input = reader.readLine()) != null) {
					store += input;
				} 

				String acl2 = "(include-book \"modules/user/verify/verify-user\")" + 
			                 "(set-state-ok t)" +
								  "(set-guard-checking :none)" + 
				              "(testUser \"" + request + "\" \"" + store + "\" state)";

				System.out.println(acl2);
				
				System.out.println("Executing process...");
				ProcessBuilder processBuilder = new ProcessBuilder("acl2");
				Process process = processBuilder.start();
				PrintWriter procIn = new PrintWriter(process.getOutputStream());
				File log = new File("logs/user/verify/acl2_log.txt");
				
				
				procIn.println(acl2);
				procIn.println("(good-bye)");
				procIn.flush();
				procIn.close();
				System.out.println("Done...");
				out.close();
				in.close();
				client.close();
			}
			
			server.close();
			System.exit(0);
		} catch(Exception e) {
			// TODO Log this issue to the GUI console.
			e.printStackTrace();
		}
	}
}
