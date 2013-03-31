import java.io.*;
import java.util.*;
import java.net.*;

public class RunVerify {
	public static void main(String[] args) {
		Socket server = null;
		BufferedWriter out = null;
		BufferedReader in = null;

		try {
			System.out.println("Opening socket...");
			server = new Socket("localhost", 20002);
			System.out.println("Connection successful!");
			out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(server.getInputStream()));

			System.out.println("Sending login information to the server.");
			
			if(server.isConnected()) {
				out.write("<?xml version='1.0'?>");
				out.write("<verify>");
				out.write("<domain>localhost</domain>");
				out.write("<name>matthew.crist</name>");
				out.write("<password>simulation</password>");
				out.write("</verify>");
				out.newLine();
				out.flush();	// flush should write
				server.shutdownOutput();
				
				String response = "";
				while(!(response = in.readLine()).contains("END")) {
					if(response != null)
						System.out.println(response);
				}	// end while loop
			} else {
				System.out.println("Connection with server could not be established.");
			}	// end if-else
			
			//out.close();
			in.close();
			server.close();
		
		} catch(Exception e) {
			e.printStackTrace();
		}

		System.exit(0);
	}
}
