package modules.email.send;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;

/**
 * New Message Dialog box
 * 
 * Creates a new new email message dialog and allows a user to input a message
 * Then calls a shell script to send the message to ACL2 for message generation and
 * processing
 * 
 * @author Wesley R. Howell
 *
 */

public class NewEmail extends JDialog {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private JTextField txtFdTo;
        private JTextField txtFdFrom;
        private JTextField txtFdSubject;
        private JTextArea textArea;
        public NewEmail() {
                getContentPane().setBackground(Color.LIGHT_GRAY);
                setResizable(false);
                setTitle("New Message - ACL2 Email System\n");
                addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                                setVisible(false);
                        }
                });
                getContentPane().setLayout(null);
                
                txtFdTo = new JTextField();
                txtFdTo.setBounds(105, 65, 469, 28);
                getContentPane().add(txtFdTo);
                txtFdTo.setColumns(10);
                
                txtFdFrom = new JTextField();
                txtFdFrom.setBounds(105, 107, 469, 28);
                getContentPane().add(txtFdFrom);
                txtFdFrom.setColumns(10);
                
                txtFdSubject = new JTextField();
                txtFdSubject.setBounds(105, 154, 469, 28);
                getContentPane().add(txtFdSubject);
                txtFdSubject.setColumns(10);
                
                textArea = new JTextArea();
                textArea.setBounds(30, 213, 544, 187);
                getContentPane().add(textArea);
                
                JLabel lblTo = new JLabel("To:");
                lblTo.setHorizontalAlignment(SwingConstants.LEFT);
                lblTo.setBounds(32, 71, 61, 16);
                getContentPane().add(lblTo);
                
                JLabel lblFrom = new JLabel("From:");
                lblFrom.setHorizontalAlignment(SwingConstants.LEFT);
                lblFrom.setBounds(32, 113, 61, 16);
                getContentPane().add(lblFrom);
                
                JLabel lblSubject = new JLabel("Subject:");
                lblSubject.setHorizontalAlignment(SwingConstants.LEFT);
                lblSubject.setBounds(32, 160, 61, 16);
                getContentPane().add(lblSubject);
                
                JLabel lblMessage = new JLabel("Message:");
                lblMessage.setBounds(32, 190, 61, 16);
                getContentPane().add(lblMessage);
                
                /**
                 * Send Button Action
                 */
                JButton btnSend = new JButton("Send");
                btnSend.setIcon(new ImageIcon("lib/email_go.png"));
                btnSend.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                
                                /**
                                 * Send to ACL2
                                 * 
                                 */
                               // String s;
                                String to = txtFdTo.getText();
                                String from = txtFdFrom.getText();
                                String sub = txtFdSubject.getText(); 
                                String cont = textArea.getText();
//                                
                                SendEmail.sendMessage(to, from, sub, cont);
                                
//                                String[] cmdArray = {"sh", "/Users/w_howell/code/spring-2013-se2-dijkstra/client/modules/email/email-action/send-message-java.sh", to, from, sub, cont};
//                                
//                                try {
//                                        Process p = Runtime.getRuntime().exec(cmdArray);
//                                                        
//                                        BufferedReader err = new BufferedReader (
//                                                new InputStreamReader (p.getErrorStream()));
//                                        while ((s = err.readLine()) != null) {
//                                                System.out.println(s);
//                                        }
//                                }
//                                catch (Exception x) {
//                                        x.printStackTrace();
//                                }
                                
                                setVisible(false);
                        }
                });
                btnSend.setBounds(457, 428, 117, 29);
                getContentPane().add(btnSend);
                
                /**
                 * Cancel Button Action
                 */
                JButton btnCancel = new JButton("Cancel");
                btnCancel.setIcon(new ImageIcon("lib/cancel.png"));
                btnCancel.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                                setVisible(false);
                        }
                });
                btnCancel.setBounds(328, 428, 117, 29);
                getContentPane().add(btnCancel);
                
                JLabel lblNewLabel_1 = new JLabel("<html><h2>Create New Message:</h2></html>");
                lblNewLabel_1.setBounds(30, 18, 238, 28);
                getContentPane().add(lblNewLabel_1);
                
                JLabel lblAclEmailSystem = new JLabel("<html><font size=2>ACL2 Email System <br>\n2013 Team Dijkstra <br>\nUniversity of Oklahoma");
                lblAclEmailSystem.setBounds(30, 410, 124, 47);
                getContentPane().add(lblAclEmailSystem);
        }
}
