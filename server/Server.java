import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

import lib.*;


public class Server
{
	public static boolean KILL_SERVER = false;
	private ArrayList<Process> activeProcesses;	

	/*
    * Default constructor for this class.
    */
	public Server()
	{
		ModuleManager manager = new ModuleManager();
		manager.loadModules("config/modules.xml");
		
		ArrayList<Module> modules = manager.getModules();
	
		activeProcesses = new ArrayList<Process>();

		for(int i = 0; i < modules.size(); i++) {
			try {
				String program = modules.get(i).getInvocation();
				Process p = new ProcessBuilder("java", "program").start();
				activeProcesses.add(p);
				// TODO Let the console know the x service was started.
				System.out.println("Server started: " + modules.get(i).getName());
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		JFrame frame = new JFrame("Application");
		frame.setBounds(0,0,100,100);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.out.println("Kill request initiated.");
				Server.KILL_SERVER = true;
			}
		});

		frame.setVisible(true);

		int j = 0;
		while(!Server.KILL_SERVER)
		{
			j++;
		}

		for(int i = 0; i < activeProcesses.size(); i++)
		{
			System.out.println("Killing all processes...");
			this.activeProcesses.get(i).destroy();
		}	// end for loop

		System.exit(0);

	}	// end constructor

	public static void main(String[] args)
	{
		new Server();
	}	// end function main
}
