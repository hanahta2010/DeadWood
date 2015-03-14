import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.Collections;  

	public class MoveButtons {	
	//public String test;
	public JLabel[] movebuttons = new JLabel[12];

//constructor------------------------------------------------------------------		
	public MoveButtons() {
		try{
			FileInputStream fin = new FileInputStream("../xml/locations.xml");
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fin);
				doc.getDocumentElement().normalize();
				Element root = doc.getDocumentElement();
				NodeList moves = root.getChildNodes();
				int index =0;					
				for (int i = 0; i < moves.getLength(); i++){
					Node move = moves.item(i);
										
					if (move.getNodeType() == Node.ELEMENT_NODE && move.getNodeName() == "button") {
									
						Element mv = (Element) move;						
						final String name = mv.getAttribute("name");						
						JLabel m = new JLabel();
						m.setBounds(Integer.parseInt(mv.getAttribute("x")) ,Integer.parseInt(mv.getAttribute("y")),45,45);		
						String image = "../images/" + mv.getAttribute("icon") + "move.png";
						
						try {
							ImageIcon img = new ImageIcon (
							  ImageIO.read(
								new File(String.format(image, "i"))));
							m.setIcon(img);
						}catch (IOException e) {
						  e.printStackTrace();
						  System.exit(1);
						}					
						m.addMouseListener(
					  			new MouseAdapter() {
									public void mouseClicked(MouseEvent e) {
										Controller.move((String) name); 
										Controller.refreshResults("You have moved to " + name);
										//Controller.refreshStats();         				
									}
					  	});
						movebuttons[index] = m;
						index += 1;
					}
				}
			} finally {
				fin.close();
			} 
		} catch (Exception e) {
			e.printStackTrace();
	    }
	}

//-----------------------------------------------------------------------------------
}


