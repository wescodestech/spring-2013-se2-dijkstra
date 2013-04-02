package edu.ou.cs.se2.dijkstra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;


/**
 * This file will replace the send-message.sh shell script and be included in the ACL2 email client
 * package. 
 * @author Wesley R. Howell
 * 
 * Original header:
 * #Created By: Wesley R. Howell
 * #Shell Script to Process an email message
 * #invocation ./route-email.sh "inputFile" 
 * where inputFile is the email message in the 
 * incoming/email folder on the server
 *
 */

public class SendEmail {
	
	public final static String OUTPATH = "/Users/w_howell/code/spring-2013-se2-dijkstra/client/store/email/outbox/";
	public final static String ACL2PATH = "/Users/w_howell/Desktop/acl2-image-4.3.0/saved_acl2";
	
	//No need to package this as an object, Just use a static method to call the
	//Functions and ACl2.
	
	/**
	 * Function that replaces Shell Script for Sending Emails.
	 * @param to
	 * @param from
	 * @param sub
	 * @param msg
	 */
	public static void sendMessage(String to, String from, String sub, String msg){
		
		//Build the ACL2 script
		String script = "(in-package \"ACL2\")(include-book \"../client/modules/email/email-action/rw-email\"" +
				" :uncertified-okp t) (writeMessage \""+to+"\" \""+from+"\" \""+sub+"\" \""+msg+"\" state)";
		
		try{
		//Run on ACL2
		// Initialize ACL2 and dump its output to the log
		System.out.println("Executing ACL2 runtime for Email Generation...");
		ProcessBuilder processBuilder = new ProcessBuilder(ACL2PATH);
		File log = new File("acl2_log.txt");
		processBuilder.redirectErrorStream(true);
		processBuilder.redirectOutput(log);
		
		Process process;
	
		process = processBuilder.start();
		
		PrintWriter procIn = new PrintWriter(process.getOutputStream());
		
		// Write the ACL2 to the process, close ACL2
		procIn.println(script);
		procIn.println("(good-bye)");
		procIn.flush();
		procIn.close();
		
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Split Emails with new Java file
		File folder = new File(OUTPATH);
		File[] listOfFiles = folder.listFiles();
		
		
		for (File f : listOfFiles){
			if(f.isFile() && !f.isHidden()){
				//Open the file and get contents.
				ArrayList<String> contents = new ArrayList<String>();
				StringBuilder strb = null;
				BufferedReader reader = null;
				
				try {
					reader = new BufferedReader(new FileReader (f));
					String line = null;
					
					try {
						while ((line = reader.readLine()) != null){
							if(line.contains("<?xml version")){
								if(strb != null){
									contents.add(strb.toString());
								}
								strb = new StringBuilder();
							} 
							strb.append(line);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				contents.add(strb.toString());
				
				f.delete();
				
				int count = 1;
				
				for(String str : contents){
					/*
					 * Open a new file for each one and save the contents.
					 */
					try{
						FileWriter fstream = new FileWriter(OUTPATH + (new Date().toString()) + "_"+count+".xml");
						BufferedWriter out = new BufferedWriter(fstream);
						out.write(str);
						out.close();
					} catch (Exception e){
						e.printStackTrace();
					}
					
					count ++;
				}
			}
			
		}
		
		
		
		
		//Send ALL files over socket 20005
		
		
		
		
		
		
	}
	
	
}
