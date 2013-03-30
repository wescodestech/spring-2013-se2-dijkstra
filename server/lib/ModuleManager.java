package lib;

/******************************************************************************
  * ModuleManager.java
  * Created on March 26, 2013 by Matthew A. Crist.
  * 
  * This class will manage to loading of module information from the
  * modules.xml file that is stored in the ./config directory.
  *
  * CHANGE LOG:
  * ---------------------------------------------------------------------------
  * 2013-03-26	-	Initial conception of this file.
  *
  ****************************************************************************/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class ModuleManager extends JDialog {
	public ArrayList<Module> modules = new ArrayList<Module>();

	private JDialog dialog;
	private JTextField _fileInput;
	private JTextField _nameInput;
	private JTextField _portInput;
	private JButton    _removeModule;

	/**
     * Default, unargumented constructor for this class.
     */
	public ModuleManager() {
		modules = loadModules("config/modules.xml");
	}	// end default, unargumented constructor

	/**
	 * Acquires the dialog that will allow a user to manage the current modules
	 * that are registered with the server.
	 */
	public void getManageModuleDialog() {
		JPanel _main = new JPanel(new BorderLayout());
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		JList moduleList = new JList(listModel);
		JScrollPane scroller = new JScrollPane(moduleList);
		
		// Add all the modules to the list model
		for(int i = 0; i < this.modules.size(); i++) {
			listModel.addElement(this.modules.get(i).getName());
		}	// end for loop
		
		_removeModule = new JButton("Remove");
		
		/*
		moduleList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent lse) {
				if(((ListSelectionModel).lse.getSource()).isSelectionEmpty()) {
					_removeModule.setEnabled(false);
				} else {
					_removeModule.setEnabled(true);
				}
			}
		});*/
		
		JButton _saveModules = new JButton("Save");
		_saveModules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				boolean status = storeModules("config/modules.xml");
				
				if(status) {
					JOptionPane.showMessageDialog(((JButton)ae.getSource()).getRootPane(), "Modules updated successfully!");
				} else {
					JOptionPane.showMessageDialog(((JButton)ae.getSource()).getRootPane(), "Unable to update modules.  Please see console output.", "Module Management Error", JOptionPane.ERROR_MESSAGE);
				}	// end if-else
			}	// end method actionPerformed
		});
		
		_main.setBorder(BorderFactory.createTitledBorder("Module Management"));
		_main.add(scroller, BorderLayout.CENTER);
		_main.add(_saveModules, BorderLayout.SOUTH);
		
		this.add(_main);
		
		int width  = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setBounds(width/2-300, height/2-200, 600, 400);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}	// end method getManageModuleDialog
	
	/**
	  * Acquires the dialog that will allow a user to register a module with the 
      * server for monitoring.
	  */
	public void getAddModuleDialog() {
		JPanel _main = new JPanel();		

		JLabel _nameLabel   = new JLabel("Module Name:");
		JLabel _invokeLabel = new JLabel("Script to Invoke:");
		JLabel _portLabel   = new JLabel("Listening Port:");
	
		_nameInput  = new JTextField();
		_fileInput  = new JTextField();
		_portInput  = new JTextField();

		JButton _fileSelect       = new JButton("Select File...");
		JButton _saveModuleButton = new JButton("Save Module");

		_fileSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)	{
				JFileChooser chooser = new JFileChooser(".");
				int val = chooser.showOpenDialog(dialog);
				
				if(val == JFileChooser.APPROVE_OPTION) {
					String filePath      = chooser.getSelectedFile().getPath();
					// Kill the absolute package resolution
					String modulePackage = filePath.substring(filePath.lastIndexOf("server"), filePath.length()-1);
					// Replace the file separators with . for the package name resolution
					modulePackage = modulePackage.replace(System.getProperty("file.separator"), ".");
					// Trim off server. portion of string
					modulePackage = modulePackage.substring(modulePackage.indexOf(".")+1, modulePackage.length()-1);
					// Trim off the file extension.
					modulePackage = modulePackage.substring(0, modulePackage.lastIndexOf("."));
					_fileInput.setText(modulePackage);
				}	// end if
			}	// end method actionPerformed
		});

		_saveModuleButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				Module module = new Module();
				module.setName(_nameInput.getText());
				module.setInvocation(_fileInput.getText());
				module.setPort(_portInput.getText());

				addModule(module);
			}	// end method actionPerformed
		});

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gbl = new GridBagLayout();
		_main.setLayout(gbl);

		gc.fill = GridBagConstraints.NONE;
		gc.weightx = 0.0;
		gc.gridx = 1;
		gc.gridy = 1;
		_main.add(_nameLabel, gc);		

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weightx = 1.0;
		gc.gridx = 2;
		gc.gridwidth = 2;
		_main.add(_nameInput, gc);

		gc.fill = GridBagConstraints.NONE;
		gc.weightx = 0.0;
		gc.gridx = 1;
		gc.gridy = 2;
		gc.gridwidth = 1;
		_main.add(_invokeLabel, gc);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weightx = 1.0;
		gc.gridx = 2;
		_main.add(_fileInput, gc);

		gc.fill = GridBagConstraints.NONE;
		gc.weightx = 0.0;
		gc.gridx = 3;
		_main.add(_fileSelect, gc);

		gc.gridy = 3;
		gc.gridx = 1;
		_main.add(_portLabel, gc);
	
		gc.gridx = 2;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;
		_main.add(_portInput, gc);
	
		gc.gridx = 3;
		gc.gridy = 4;
		gc.gridwidth = 1;
		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.VERTICAL;
		_main.add(_saveModuleButton, gc);
		
		_main.setBorder(BorderFactory.createTitledBorder("Register Module"));
		
		this.add(_main);
		this.setBounds(0,0, 400, 150);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	  * Adds a module to the modules list.
     */
	public boolean addModule(Module module) {
		boolean ok = true;

		for(int i = 0; i < this.modules.size(); i++) {
			if(this.modules.get(i).getPort().equalsIgnoreCase(module.getPort())) {
				ok = false;
			}	// end if
		}	// end for loop

		// Attempt to add the module.
		if(ok) {
			this.modules.add(module);
			this.storeModules("config/modules.xml");
			
			return true;
		} else {
			JOptionPane.showMessageDialog(this, "Unable to add module.  There exists a module using the same port as the one you are attempting to register.", "Error Registering Module", JOptionPane.ERROR_MESSAGE);
			return false;
		}	// end if-else
	}	// end method addModule

	/**
     * Removes a module from the modules list.
	  */
	public boolean removeModule(String name) {
		boolean removed = false;

		for(int i = 0; i < this.modules.size(); i++) {
			if(this.modules.get(i).getName().toLowerCase() == name.toLowerCase()) {
				this.modules.remove(i);
				removed = true;
			}	// end if
		}	// end for loop
		
		return removed;
	}	// end method removeModule
		
	/**
     * Loads the modules from a persistent XML storage file.
     */
	public ArrayList<Module> loadModules(String configFile)
	{
		ArrayList<Module> modules = new ArrayList<Module>();

		try
		{
			File moduleFile = new File(configFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(moduleFile);

			doc.getDocumentElement().normalize();

			NodeList nodes = doc.getElementsByTagName("module");

			for(int i = 0; i < nodes.getLength(); i++)
			{
				Node node = nodes.item(i);
				
				if(node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element)node;
					Module module = new Module();
					module.setName(element.getElementsByTagName("name").item(0).getTextContent());
					module.setInvocation(element.getElementsByTagName("invoke").item(0).getTextContent());
					module.setPort(element.getElementsByTagName("port").item(0).getTextContent());
	
					modules.add(module);
				}
			}
		}	// end try
		catch(Exception e)
		{
			e.printStackTrace();
		}	// end catch

		return modules;
	}	// end method loadModules

	/**
     * Writes the modules out to an XML file for storage.
     */
	public boolean storeModules(String configFile)
	{
		// Header information for the XML file.
		String xml = "<?xml version='1.0'?>\n"                                + 
						 "<!DOCTYPE modules SYSTEM '../dtd/module.dtd'>\n" +
						 "<modules>\n";

		// Cycle through all modules and compile the XML for output.
		for(int i = 0; i < this.modules.size(); i++)
		{
			xml += "\t<module>\n"                                                   +
					 "\t\t<name>" + this.modules.get(i).getName() + "</name>\n"            +
                "\t\t<invoke>" + this.modules.get(i).getInvocation() + "</invoke>\n"  +
                "\t\t<port>" + this.modules.get(i).getPort() + "</port>\n" +
					 "\t</module>\n"; 
		}	// end for loop

		xml += "</modules>";

		try
		{
			FileWriter writer = new FileWriter(configFile);
			writer.write(xml);
			writer.close();	
		}	// end try
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return false;
		}	// end catch
		
		return true;
	}	// end method storeModules

	/**
     * Acquires the set of modules that are currently stored in the module manager.
     */
	public ArrayList<Module> getModules()
	{
		return this.modules;
	}	// end method getModules
}	// end class ModuleManager
