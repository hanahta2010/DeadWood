import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class PlayerView extends JLabel {

	public String name;	
	public String room;	
	static private String[] playerNameList = {"b", "c", "g", "o", "p", "r", "v", "y"};
	
//constructor---------------------------------------------------------------------
	public PlayerView(int x, int y, String image, String id) {	
		name = id;
		setBounds(x,y,46,46);
		try {
			ImageIcon img = new ImageIcon (
		      ImageIO.read(
		        new File(String.format(image, "i"))));
			setIcon(img);
		}catch (IOException e) {
		  e.printStackTrace();
		  System.exit(1);
		}		
	}

//methods-------------------------------------------------------------------------------
	public void changeRank(String rank) {
		String image = name + rank + ".png";
		setIcon(new ImageIcon(rank));
	}
//--------------------------------------------------------------------------------
}
