import lib.*;

public class ACL2Email {
    /**
      * main.java
      * Entry point for ACL2 email window.
      * @author Wesley R. Howell
      * @param args
      */ 
	public static void main(String[] args) {
		ConfigManager.loadConfiguration("config/settings.xml");
        new EmailWindow();
    }	// end static method main
}	// end class ACL2Email