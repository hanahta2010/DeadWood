import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.util.*;
public class SceneView extends JPanel {

	final public String name;	
	final public List<Roleview> roles = new ArrayList<Roleview>();
	final public String set;
	public JLabel picture;

//constructor--------------------------------------------------------------------------------------
	public SceneView (int x, int y, CardLayout.Card card, String st) {	
		super(null);
		setSize(205,115);
    	setDoubleBuffered(true);		
		
		set = st;		
		name = card.name;				
		setBounds(x, y, 205, 115);		
				for(Map.Entry<String, HashMap<String,Integer>> ent : card.roles.entrySet()){
					final String rolename = ent.getKey();
					HashMap<String, Integer> roleSpot = ent.getValue();					
					int xx = roleSpot.get("x");
					int yy = roleSpot.get("y"); 
					Roleview rle = new Roleview(xx, yy, rolename, name, set, x, y);										
					roles.add(rle);
					add(rle);
				} 		
		picture = new JLabel();
		picture.setBounds(0, 0, 205, 115);		
		String image = "../cards/" + card.img;		
		try {
			ImageIcon img = new ImageIcon (
		      ImageIO.read(
		        new File(String.format(image, "i"))));
			picture.setIcon(img);
		}catch (IOException e) {
		  e.printStackTrace();
		  System.exit(1);
    }		
		add(picture);
	}
//--------------------------------------------------------------------------------
}
