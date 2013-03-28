import java.io.*;
import java.util.*;
import java.net.*;

public class RunVerify {
	public static void main(String[] args) {
		Socket server = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			System.out.println("Opening socket...");
			server = new Socket("localhost", 20002);
			System.out.println("Connection successful!");
			out = new PrintWriter(server.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(server.getInputStream()));

			out.println("<?xml version='1.0'?>");
			out.println("<verify>");
			out.println("\t<domain>localhost</domain>");
			out.println("\t<name>matthew.crist</name>");
			out.println("\t<password>simulation</password>");
			out.println("</verify>");

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
