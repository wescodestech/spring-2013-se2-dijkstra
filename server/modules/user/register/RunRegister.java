import java.io.*;
import java.util.*;
import java.net.*;

public class RunRegister {
	public static void main(String[] args) {
		Socket server = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			System.out.println("Opening socket...");
			server = new Socket("localhost", 20001);
			System.out.println("Connection successful!");
			out = new PrintWriter(server.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(server.getInputStream()));

			out.println("<?xml version='1.0'?>");
			out.println("<!DOCTYPE register 'dtd/register-user.dtd'>");
			out.println("<register>");
			out.println("\t<domain>localhost</domain>");
			out.println("\t<name>adam.ghodratnama</name>");
			out.println("\t<password>simulation</password>");
			out.println("</register>");

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