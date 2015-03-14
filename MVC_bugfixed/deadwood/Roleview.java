import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class Roleview extends JLabel {
	
	public boolean occupied;
	final public String name;	
	final public String scene;
	final public String set;
	public int xdisplacement;
	public int ydisplacement;

//constructor------------------------------------------------------------------------------------	
	public Roleview (int x, int y, String id, String sc, String st, int xoffset, int yoffset) {	
		xdisplacement = xoffset;	
		ydisplacement = yoffset;	
		occupied = false;		
		name = id;
		scene = sc;
		set = st;		
		setBounds(x,y,47,47);
					final Roleview p = this;
					addMouseListener(
				  		new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {		  				
									Controller.takeRole(p, set, name, scene);
									Controller.refreshStats();
									Controller.refreshResults("You have taken the role " + name);
								
							}
			  		});
	}
//--------------------------------------------------------------------------------
}
