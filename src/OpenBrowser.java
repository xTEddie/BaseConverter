import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * 
 * @author Edward Tran
 *
 */
public class OpenBrowser implements ActionListener {
	
//	public static String website = "http://xteddie.noip.me/";
	public static String website = "http://192.168.1.8";

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(website));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
