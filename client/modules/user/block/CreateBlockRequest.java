//package modules.user.register;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane; 

class CreateBlockRequest {
	public static void main(String[] args) {
		
		Socket server = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		try {
			//server = new ServerSocket(20001);
			
			//System.out.println("Opening socket...");
			//server = new Socket("localhost", 20001);
			//System.out.println("Connection successful!");
			//out = new PrintWriter(server.getOutputStream(), true);
			//in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			
			
			//read in the domain, name and password
			String myDomain = JOptionPane.showInputDialog ( "Enter Your Domain" );
			String myName = JOptionPane.showInputDialog ( "Enter Your Name" );
			
			String domain = JOptionPane.showInputDialog ( "Enter Blocked User's Domain" );
			String name = JOptionPane.showInputDialog ( "Enter Blocked User's Name" );

			
			//formatted date for acle timestamp
			Date date = new Date();			
			String formatted = new SimpleDateFormat("MMddyyHHmmss").format(date);
			
			//acl2 command
			String acl2 = "(in-package \"ACL2\")(include-book \"create-block-request\" :uncertified-okp t)" + 
			                 "(set-state-ok t)" +
							 "(set-guard-checking :none)" + 
				             "(createRequest '(\""+ myDomain +"\" \""+ myName+"\") '(\""+ domain+"\" \""+ name +"\")" +
				             formatted + " state)";
				             				             			
			// Initialize ACL2 and dump its output to the log
			System.out.println("Executing ACL2 runtime for RegisterUser...");
			
			ProcessBuilder processBuilder = new ProcessBuilder("acl2");
			File log = new File("../../../logs/user/block/acl2_log.txt");
			
			processBuilder.redirectErrorStream(true);
			processBuilder.redirectOutput(log);
			
			Process process = processBuilder.start();
			PrintWriter procIn = new PrintWriter(process.getOutputStream());
			
			// Write the ACL2 to the process, close ACL2
			procIn.println(acl2);
			procIn.println("(good-bye)");
			procIn.flush();
			procIn.close();
			
			//need to wait for xml file creation before it can be opened
			Thread.sleep(4000);
				
			//open xml request file			
			//FileInputStream fstream = new FileInputStream("../../../store/user/requests/block/request-register4"+formatted+".xml");
			
			//open xml request file
			FileInputStream fstream = new FileInputStream("../../../store/user/requests/block/request-register40413091653.xml");
			
			DataInputStream inputStream = new DataInputStream(fstream);
  			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			
			String strLine;
			
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				// Print the content on the console
				System.out.println (strLine);
				
				//print to server on port
				//out.println (strLine);
			}
			
			out.flush();
			out.close();
			in.close();
			server.close();

			
		}catch(Exception e) {
			e.printStackTrace();
		}	// end try/catch
		
	}
}