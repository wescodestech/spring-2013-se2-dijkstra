package lib;

import javax.swing.JTextArea;

public class ServerConsole {
	public static JTextArea console = new JTextArea();
	
	public static void post(String text) {
		ServerConsole.console.setText(ServerConsole.console.getText().concat(text));
	}	// end method post
}	// end class ServerConsole