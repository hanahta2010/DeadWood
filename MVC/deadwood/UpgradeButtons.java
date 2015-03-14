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
 
public class UpgradeButtons 
 
   {	
		public UButton[] buttons = new UButton[10];

//constructor-------------------------------------------------------------------------------------------------
		public UpgradeButtons() {
				int index = 0;				
				try{
					FileInputStream fin = new FileInputStream("../xml/upGrade.xml");
					try {
						DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
						Document doc = dBuilder.parse(fin);
						doc.getDocumentElement().normalize();
						Element root = doc.getDocumentElement();
						NodeList ubuts = root.getChildNodes();					
						for (int i = 0; i < ubuts.getLength(); i++){
							Node field = ubuts.item(i);
										
							if (field.getNodeType() == Node.ELEMENT_NODE && field.getNodeName() == "upgrade") {					
								
								Element upgrade = (Element) field;
								UButton butt = new UButton(upgrade);										
								buttons[index] = butt;
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

//nested class---------------------------------------------------------		
		
		public class UButton extends JLabel{
			public String currency;
			public String level;
			
			public UButton(Element info) {
				currency = info.getAttribute("currency");
				level = info.getAttribute("level");
				NodeList coords = info.getChildNodes();
				for (int j = 0; j < coords.getLength(); j++){
					if (coords.item(j).getNodeType() == Node.ELEMENT_NODE && coords.item(j).getNodeName() == "area") {		
						Element coord = (Element) coords.item(j);						
						int x = Integer.parseInt(coord.getAttribute("x"));	
						int y = Integer.parseInt(coord.getAttribute("y"));	
						int w = Integer.parseInt(coord.getAttribute("w"));	
						int h = Integer.parseInt(coord.getAttribute("h"));						
						setBounds(x,y,w,h);
						try {
							ImageIcon image = new ImageIcon (
							  ImageIO.read(
								new File(String.format("../images/butto.png", "i"))));
							setIcon(image);
						}catch (IOException e) {
						  e.printStackTrace();
						  System.exit(1);
						}
					}
					addMouseListener(
			  			new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								Controller.upGrade(currency, level) ;
							}
			  		});
				}
			}

		}
//------------------------------------------------------------------------------------------------
}
