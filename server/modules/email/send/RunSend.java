import java.io.*;
import java.util.*;
import java.net.*;

public class RunSend {
	public static void main(String[] args) {
		Socket server = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			System.out.println("Opening socket...");
			server = new Socket("localhost", 20005);
			System.out.println("Connection successful!");
			out = new PrintWriter(server.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(server.getInputStream()));

			out.println("<?xml version='1.0'?>");
			out.println("<!DOCTYPE register 'dtd/send-email.dtd'>");
			out.println("<email>");
			out.println("\t<to>matthew.crist@localhost</to>");
			out.println("\t<from>adam.ghodratnama@localhost</from>");
			out.println("\t<subject>Message Subject</subject>");
			out.println("\t<message>This is the message content!</message>");
			out.println("</email>");

			out.flush();
			out.close();
			in.close();
			server.close();
		
		} catch(Exception e) {
			e.printStackTrace();
		}

		System.exit(0);
	}
}