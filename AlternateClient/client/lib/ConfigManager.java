package lib;

import modules.user.register.*;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class ConfigManager {
	public static HashMap<String, String> prefs = new HashMap<String, String>(); 
	
	/**
     * Loads the configuration from a persistent XML storage file.
     */
	public static void loadConfiguration(String configFile)
	{
		try
		{
			File _file = new File(configFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(_file);

			doc.getDocumentElement().normalize();

			NodeList nodes = doc.getElementsByTagName("preference");

			for(int i = 0; i < nodes.getLength(); i++)
			{
				Node node = nodes.item(i);
				
				if(node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element)node;
					ConfigManager.prefs.put(element.getElementsByTagName("key").item(0).getTextContent(), element.getElementsByTagName("value").item(0).getTextContent());
				}	// end if
			}	// end for loop
		}	// end try
		catch(Exception e)
		{
			e.printStackTrace();
		}	// end catch
	}	// end method loadModules
		
	/** 
	 * Stores the configuration file.
	 */
	public static void saveConfiguration(String configFile) {
		String xml  = "<?xml version='1.0'?>"                                                   +
		              "<!DOCTYPE preferences SYSTEM '../dtd/preferences.dtd'>"                  +
		              "<preferences>"                                                           +
		                 "<preference>"                                                         +
		                    "<key>name</key>"                                                   +
		                    "<value>" + ConfigManager.prefs.get("name") + "</value>"            +
		                 "</preference>"                                                        +
		                 "<preference>"                                                         +
		                    "<key>domain</key>"                                                 +
		                    "<value>" + ConfigManager.prefs.get("domain") + "</value>"          +
		                 "</preference>"                                                        +
		                 "<preference>"                                                         +
		                    "<key>password</key>"                                               +
		                    "<value>" + ConfigManager.prefs.get("password") + "</value>"        +
		                 "</preference>"                                                        +
		                 "<preference>"                                                         +
		                    "<key>server_location</key>"                                        +
		                    "<value>" + ConfigManager.prefs.get("server_location") + "</value>" +
		                 "</preference>"                                                        +
		              "</preferences>";
		
		try {
			FileWriter writer = new FileWriter(configFile);
			writer.write(xml);
			writer.close();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}	// end catch
	}	// end method saveConfiguration
	
	public static void getConfigurationWindow(JFrame owner) {
		// The Window
		final JDialog dialog = new JDialog(owner);
		
		// Buttons
		final JButton _ok = new JButton("Save", new ImageIcon("lib/accept.png"));
		final JButton _cancel = new JButton("Cancel", new ImageIcon("lib/cancel.png"));
		final JButton _create = new JButton("Create Account", new ImageIcon("lib/user_add.png"));
		
		// Button positioning
		_ok.setBounds(155, 150, 100, 20);
		_cancel.setBounds(260, 150, 100, 20);
		_create.setBounds(185, 15, 175, 20);
		
		// Labels
		final JLabel _name = new JLabel("Name");
		final JLabel _domain = new JLabel("Domain");
		final JLabel _password = new JLabel("Password");
		final JLabel _server = new JLabel("Server");
		
		// Label positioning
		_name.setBounds(20, 40, 150, 20);
		_domain.setBounds(20, 65, 150, 20);
		_password.setBounds(20, 90, 150, 20);
		_server.setBounds(20, 115, 150, 20);
		
		// Fields
		final JTextField _nameField = new JTextField(ConfigManager.prefs.get("name"));
		final JTextField _domainField = new JTextField(ConfigManager.prefs.get("domain"));
		final JTextField _passwordField = new JPasswordField(ConfigManager.prefs.get("password"));
		final JTextField _serverField = new JTextField(ConfigManager.prefs.get("server_location"));
		
		// Field positioning
		_nameField.setBounds(160, 40, 200, 20);
		_domainField.setBounds(160, 65, 200, 20);
		_passwordField.setBounds(160, 90, 200, 20);
		_serverField.setBounds(160, 115, 200, 20);
		
		// Window and preferences
		dialog.setLayout(null);
		dialog.setBounds(owner.getWidth() / 2 - 190, owner.getHeight() / 2 - 110, 380, 220);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setResizable(false);
		((JPanel)dialog.getContentPane()).setBorder(BorderFactory.createTitledBorder("Account Settings"));
		
		// Button Actions
		_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ConfigManager.saveConfiguration("config/settings.xml");
				
				// Repopulate the prefs
				ConfigManager.prefs.remove("name");
				ConfigManager.prefs.put("name", _nameField.getText());
				ConfigManager.prefs.remove("domain");
				ConfigManager.prefs.put("domain", _domainField.getText());
				ConfigManager.prefs.remove("password");
				ConfigManager.prefs.put("password", _passwordField.getText());
				ConfigManager.prefs.remove("server_location");
				ConfigManager.prefs.put("server_location", _serverField.getText());
				
				dialog.dispose();
			}	// end method actionPerformed
		});
		
		_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dialog.dispose();
			}	// end method actionPerformed
		});
		
		_create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if((_nameField.getText().length() > 0) && (_domainField.getText().length() > 0) && (_passwordField.getText().length() > 0) && (_serverField.getText().length() > 0)) {
					ConfigManager.saveConfiguration("config/settings.xml");
					
					// Repopulate the prefs
					ConfigManager.prefs.remove("name");
					ConfigManager.prefs.put("name", _nameField.getText());
					ConfigManager.prefs.remove("domain");
					ConfigManager.prefs.put("domain", _domainField.getText());
					ConfigManager.prefs.remove("password");
					ConfigManager.prefs.put("password", _passwordField.getText());
					ConfigManager.prefs.remove("server_location");
					ConfigManager.prefs.put("server_location", _serverField.getText());
					
					// Submit the request to the server
					SendRequest.sendRequest(_nameField.getText(), _domainField.getText(), _passwordField.getText());
				} else {
					JOptionPane.showMessageDialog(dialog, "You must fill out all fields to register a new user.");
				}
			}	// end method actionPerformed
		});
		
		// Add all visual elements to the window
		dialog.add(_name);
		dialog.add(_domain);
		dialog.add(_password);
		dialog.add(_server);
		dialog.add(_nameField);
		dialog.add(_domainField);
		dialog.add(_passwordField);
		dialog.add(_serverField);
		dialog.add(_ok);
		dialog.add(_cancel);
		dialog.add(_create);
		
		dialog.setVisible(true);
	}	// end method getConfigurationWindow
}	// end class ConfigManager