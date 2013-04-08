package lib;

import modules.email.action.*;
import modules.email.send.*;
import modules.user.register.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.event.*;

/**
 * email Window
 * This file will generate a window for the ACL2 email system. This is a client based window and 
 * will call the shell scripts we generated for the ACL2 program
 * 
 * mainly, this is a fancy way of entering data instead of using the console and navigating folders.
 * 
 * The Icons in this applications are open source obtained from http://openiconlibrary.sourceforge.net/
 * The license stipulates that the icons are free to use, modify and redistribute. 
 * 
 * March 13, 2013
 * @version 1.0
 * @author Wesley R. Howell
 * 
 * CHANGE LOG:
 * ------------------------------------------------------------------------
 * 2013-04-08	-	Add more functionality to the Email Window, modified location, added package.
 *
 */

public class EmailWindow extends JFrame {
	private JFrame _self;
    private JList emailList;
    private JSplitPane splitPane;
    private JToolBar toolBar;
    private JMenuBar menuBar;
    private JEditorPane previewPane;
    
    private JButton newEmail;
    private JButton getEmail;
    
    private final int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
        
    /**
      * Run The Email Window object. Most of the code will be handled here.
      */
    public EmailWindow() {
    	_self = this;
    	
    	// New email button
    	this.newEmail = new JButton(new ImageIcon("lib/email_edit.png"));
    	this.newEmail.setToolTipText("Create New Email");
    	this.getEmail = new JButton(new ImageIcon("lib/email_open.png"));
    	this.getEmail.setToolTipText("Get Email");
    	
    	// The newEmail actions
    	this.newEmail.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent ae) {
    			new NewEmail();
    		}	// end method actionPerformed
    	});
    	
    	// The top toolbar
    	this.toolBar = new JToolBar("Email Actions");
    	this.toolBar.setFloatable(false);
    	
    	this.toolBar.add(newEmail);
    	this.toolBar.addSeparator();
    	//this.toolBar.add(getEmail);
    	
    	// Preview Pane for emails
    	this.previewPane = new JEditorPane();
    	
    	// Content Arrangement
    	this.splitPane = new JSplitPane();
    	this.splitPane.setDividerLocation(screen_height / 2 - 150);
    	this.splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
  
    	this.splitPane.add(new JScrollPane(this.emailList), JSplitPane.TOP);
    	this.splitPane.add(new JScrollPane(this.previewPane), JSplitPane.BOTTOM);
    	
    	JMenu _file = new JMenu("File");
    	JMenuItem _exit = new JMenuItem("Exit");
    	JMenuItem _prefs = new JMenuItem("Accounts");
    	
    	// _exit action
    	_exit.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent ae) {
    			dispose();
    			System.exit(0);
    		}	// end method actionPerformed
    	});
    	
    	// _prefs action
    	_prefs.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent ae) {
    			ConfigManager.getConfigurationWindow(_self);
    		}	// end method actionPerformed
    	});
    	
    	_file.add(_prefs);
    	_file.addSeparator();
    	_file.add(_exit);
    	
    	JMenu _email = new JMenu("Email");
    	this.menuBar = new JMenuBar();
    	this.menuBar.add(_file);
    	
    	// Window Properties
    	this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    	this.setIconImage(Toolkit.getDefaultToolkit().getImage("lib/icon.gif"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setJMenuBar(this.menuBar);
        this.setTitle("ACL2 Email System");
                
        this.add(this.toolBar, BorderLayout.PAGE_START);
        this.add(this.splitPane, BorderLayout.CENTER);
        
        this.setVisible(true);
        
        /*
        emailsList = new JList();
        emailslist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        emailslist.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        final JLabel lblBlah = new JLabel();
        lblBlah.setBackground(Color.WHITE);
        JButton btnGetEmail = new JButton("Get Email");
        btnGetEmail.setIcon(new ImageIcon("lib/email_open.png"));
                
                
        /**
        btnGetEmail.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		String s;
        		GetEmail.getEmail(currentUser, currentDomain, currentPassword);
                
        		try {
        			Thread.sleep(2000);
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		}
                                
                                File folder = new File("store/email/inbox");
								folder.mkdirs();
                        File [] rawContents = folder.listFiles();
                        listofFiles = new File[rawContents.length];
                        names = new String[rawContents.length];
                       // Map<String, File> listforgui = new HashMap<String,File>();
                        for (int i = 0; i < rawContents.length; i++){
                                if(rawContents[i].isFile() && !rawContents[i].isHidden()){
                                        listofFiles[i] = rawContents[i];
                                        names[i] = getEmailHeader(listofFiles[i]);
                                }
                        }
                        emailsList.setListData(names);
                        }
                });
                btnGetEmail.setBounds(16, 6, 117, 29);
                getContentPane().add(btnGetEmail);
                
                
                
                
                /**
                File folder = new File("store/email/inbox");
        File [] rawContents = folder.listFiles();
        listofFiles = new File[rawContents.length];
        names = new String[rawContents.length];
       // Map<String, File> listforgui = new HashMap<String,File>();
        for (int i = 0; i < rawContents.length; i++){
                if(rawContents[i].isFile() && !rawContents[i].isHidden()){
                        listofFiles[i] = rawContents[i];
                        names[i] = getEmailHeader(listofFiles[i]);
                }
        }
                
        
        
        
                /**
                emailsList.addListSelectionListener(new ListSelectionListener() {
                        public void valueChanged(ListSelectionEvent arg0) {
                                //Open file and get text!
                                
                                BufferedReader reader;
                                try {
                                        StringBuilder  stringBuilder = new StringBuilder();
                                        if(emailsList.getSelectedIndex() != -1){
                                        reader = new BufferedReader( new FileReader (listofFiles[emailsList.getSelectedIndex()]));
                                         String         line = null;
                                            
                                            String         ls = System.getProperty("line.separator");

                                            while( ( line = reader.readLine() ) != null ) {
                                                stringBuilder.append( line );
                                                stringBuilder.append( ls );
                                            }
                                        }
                                            
                                            lblBlah.setText(stringBuilder.toString());
                                } catch (FileNotFoundException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                }
                           
                        }
                });
                emailsList.setBounds(16, 44, 315, 612);
                emailsList.setListData(names);
                getContentPane().add(emailsList);
                
                
                /**

                JPanel panel = new JPanel();
                panel.setBackground(Color.WHITE);
                panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
                panel.setBounds(336, 44, 682, 681);
                getContentPane().add(panel);
                panel.setLayout(null);
                
                
                /**
                lblBlah.setVerticalAlignment(SwingConstants.TOP);
                lblBlah.setBounds(6, 6, 670, 669);
                panel.add(lblBlah);
                
                JLabel lblNewLabel = new JLabel("");
                lblNewLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent arg0) {
                                try {
                                        String url = "http://en.wikipedia.org/wiki/ACL2";
                                 java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
                                } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                }
                        }
                });
                lblNewLabel.setIcon(new ImageIcon("lib/logo.gif"));
                lblNewLabel.setBounds(16, 688, 83, 74);
                getContentPane().add(lblNewLabel);
                JLabel lblAcl = new JLabel("<html><h2>ACL2 Email System</h2><p>2013 Team Dijkstra \n University of Oklahoma</p></html>\n");
                lblAcl.setVerticalAlignment(SwingConstants.TOP);
                lblAcl.setBounds(121, 688, 183, 74);
                getContentPane().add(lblAcl);
                
                /**

                JButton btnNewMessage = new JButton("New Message");
                btnNewMessage.setIcon(new ImageIcon("lib/email_edit.png"));
                btnNewMessage.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                newMessage msg = new newMessage();
                                msg.setSize(602,487);
                                msg.setVisible(true);
                        }
                });
                btnNewMessage.setBounds(142, 6, 132, 29);
                getContentPane().add(btnNewMessage);
                
                JLabel lblcodegooglecompspringsedijkstra = new JLabel("<html><font size=4><a href=https://code.google.com/p/spring-2013-se2-dijkstra//p/>code.google.com/p/spring-2013-se2-dijkstra</a>\n    </font></html>");
                lblcodegooglecompspringsedijkstra.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent arg0) {


                                // open the default web browser for the HTML page
                                try {
                                        String url = "https://code.google.com/p/spring-2013-se2-dijkstra/";
                                 java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
                                } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                }
                        }
                });
                
                /**

                lblcodegooglecompspringsedijkstra.setVerticalAlignment(SwingConstants.TOP);
                lblcodegooglecompspringsedijkstra.setBounds(688, 737, 330, 25);
                getContentPane().add(lblcodegooglecompspringsedijkstra);
                
                
                /**

                JButton btnRegister = new JButton("Register");
                btnRegister.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		currentUser = JOptionPane.showInputDialog("Input your name");
                		currentDomain  = JOptionPane.showInputDialog("input your domain");
                		currentPassword  = JOptionPane.showInputDialog("Input your Password");                		
                		SendRequest.sendRequest(currentUser, currentDomain, currentPassword);
                	}
                });
                btnRegister.setIcon(new ImageIcon("lib/user_add.png"));
                btnRegister.setBounds(901, 6, 117, 29);
                getContentPane().add(btnRegister);
		
		JButton btnNewButton = new JButton("Delete Message");
		btnNewButton.setIcon(new ImageIcon("lib/email_delete.png"));
                btnNewButton.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
			    int currentlySelected = emailsList.getSelectedIndex();
			    listofFiles[currentlySelected].delete();


			    //Update Jlist
                            File folder = new File("store/email/inbox");
			    folder.mkdirs();
			    File [] rawContents = folder.listFiles();
			    listofFiles = new File[rawContents.length];
			    names = new String[rawContents.length];
                       
			    for (int i = 0; i < rawContents.length; i++){
                                if(rawContents[i].isFile() && !rawContents[i].isHidden()){
                                        listofFiles[i] = rawContents[i];
                                        names[i] = getEmailHeader(listofFiles[i]);
                                }
			    }
			    emailsList.setListData(names);
                	}
                });
                btnNewButton.setBounds(16, 657, 315, 29);
                getContentPane().add(btnNewButton);*/
		

        }
        
        /**
         * Gets the email header for listing in the JFrame list 
         * @param file
         * @return
         */
        @SuppressWarnings("resource")
        private String getEmailHeader(File file){
                String rv = "";
                BufferedReader reader;
                try {
                        reader = new BufferedReader( new FileReader (file));
                                String         line = null;
                                String subject = "";
                            String name ="";
                            
                            int number = 0;
                            while( ( line = reader.readLine() ) != null ) {
                                if(number == 1){
                                        subject = line.substring(4, line.indexOf("</h1>"));

                                }
                                if(number == 3){
                                        name = line.substring(11, line.indexOf("</h2>"));
                                }
                                number++;
                            }
                            rv = name + "  -  " + subject +"    -  " + file.getName().substring(4);
                } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                
                return rv;
        }
}