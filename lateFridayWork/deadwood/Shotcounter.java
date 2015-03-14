import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class Shotcounter extends JLabel {

public Shotcounter(int x, int y, int w, int h) {	
	
	setBounds(x,y,w,h);	
	try {
    	ImageIcon image = new ImageIcon (
          ImageIO.read(
            new File(String.format("../images/shot.png", "i"))));
		setIcon(image);
    }catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
	setVisible(false);
}
//--------------------------------------------------------------------------------
}
