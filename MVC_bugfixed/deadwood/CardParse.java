import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


// Parsing through cards.xml
// Note: The card's information is not entirely similar with the original board on the website. 
public class CardParse {
	
		public static ArrayList<Card> CP() {	
			
			ArrayList<Card> cards = new ArrayList<Card>();
			
			try {
				FileInputStream file = new FileInputStream("../xml/cards2.xml");
				try {
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					int num = 1;
					Document doc = builder.parse(file);
					
					doc.getDocumentElement().normalize();
					
					Element root = doc.getDocumentElement();
					
					IterableNodeList<Node> cardList = new IterableNodeList<Node>(root.getChildNodes());
					for (Node c: cardList) {	
						if(c.getNodeType() == Node.ELEMENT_NODE) {
							Element card = (Element) c;
							cards.add(build (card));
							//System.out.println(num);
							//num+= 1;
						}
					}
					java.util.Collections.shuffle(cards);
				}
				finally {
					file.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return cards;
		}

// building the card and add roles to the card		
		public static Card build(Element card) {
			String partName = null;
			String level = null;
			String line = null;
			String description = null;
			String sceneNum = null;
			String name = card.getAttribute("name");
			//System.out.println(name);			
			String img = card.getAttribute("img");
			String budget = card.getAttribute("budget");
			ArrayList<Role> roles = new ArrayList<Role>();
			
			IterableNodeList<Node> cardE = new IterableNodeList<Node>(card.getChildNodes());
			for (Node cc: cardE) {
				if (cc.getNodeType() == Node.ELEMENT_NODE) {
					Element comp = (Element) cc;
					switch (comp.getNodeName()) {
					case "scene":
						sceneNum = comp.getAttribute("number");
						break;
					case "part":
						partName = comp.getAttribute("name");						
						level = comp.getAttribute("level");
						IterableNodeList<Node> childComp = new IterableNodeList<Node>(comp.getChildNodes());
						for (Node ccc : childComp) {
							if (ccc.getNodeType() == Node.TEXT_NODE) {
								description = ccc.getTextContent();
							} else {
								Element comp2 = (Element) ccc;
								switch (comp2.getNodeName()) {
								case "area":
									String x = comp2.getAttribute("x");
									String y = comp2.getAttribute("y");
									String h = comp2.getAttribute("h");
									String w = comp2.getAttribute("w");
									break;
								case "line":
									line = comp2.getLastChild().getTextContent();
									break;
								}
							}
						}
						roles.add(new Role(partName, level, line));
						break;
					}	
				}
			}
			return new Card(name, sceneNum, description, budget, roles);
		}
}
