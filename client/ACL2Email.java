
import java.awt.Image;
import java.awt.Toolkit;


public class ACL2Email {

        /**
         * main.java
         * Entry point for ACL2 email window.
         * @author Wesley R. Howell
         * @param args
         */
        
        public static void main(String[] args) {
                emailWindow email = new emailWindow();
                email.setSize(1024, 800);
                Image icon = Toolkit.getDefaultToolkit().getImage("icon.gif");
                email.setIconImage(icon);
                email.setVisible(true);
        }

}