import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.*;
import java.io.*;
import java.util.Collections;  

public class CardLayout {
	public List<Card> deck = new ArrayList<Card>();	

	public class Card {
		public String test;		
		public String name;
		public HashMap<String, HashMap<String,Integer>> roles = new HashMap<String, HashMap<String,Integer>>();
		public String img;
		public String desc;
	}
//constructor--------------------------------------------------------------------------------------------
	public CardLayout(int[] cardorder) {
			
		try{
			FileInputStream fin = new FileInputStream("../xml/cards2.xml");
			try {
				for (int w = 0; w<40; w++) {
					deck.add(new Card());
				}				
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fin);
				doc.getDocumentElement().normalize();
				Element root = doc.getDocumentElement();
				NodeList scenes = root.getChildNodes();
				int index =0;					
				for (int i = 0; i < scenes.getLength(); i++){
					Node card = scenes.item(i);
										
					if (card.getNodeType() == Node.ELEMENT_NODE && card.getNodeName() == "card") {
					int sceneIndex = 0;					
					int placement = 0;					
					Element card2 = (Element) card;
						Card cd = new Card();
						cd.img = card2.getAttribute("img");
						cd.name = card2.getAttribute("name");					
						index = index + 1;						
						NodeList info = card.getChildNodes();
						for (int j = 0; j< info.getLength(); j++) {		
							if (info.item(j).getNodeType() == Node.ELEMENT_NODE) {
								Element field = (Element) info.item(j);
								if (field.getNodeName() == "scene") {
									sceneIndex = Integer.parseInt(field.getAttribute("number"));									
									placement = Arrays.binarySearch(cardorder, sceneIndex);									
									for (int n = 0; n<40; n++) {
										if (sceneIndex ==  cardorder[n]){
											placement = n;
										}
									}																	
									cd.desc = field.getFirstChild().getNodeValue();
								}
								if (field.getNodeName() == "part") {
									HashMap<String,Integer> place = new HashMap<String, Integer>();
									String name = field.getAttribute("name");
									NodeList inf = field.getChildNodes();
									for (int k = 0; k< inf.getLength(); k++) {
										if (inf.item(k).getNodeType() == Node.ELEMENT_NODE) {
											Element fd = (Element) inf.item(k);
											if (fd.getNodeName().equals("area")) {
												place.put("x", Integer.parseInt(fd.getAttribute("x")));
												place.put("y", Integer.parseInt(fd.getAttribute("y")));
												place.put("h", Integer.parseInt(fd.getAttribute("h")));
												place.put("w", Integer.parseInt(fd.getAttribute("w")));
												cd.roles.put(name,place);
											}
										}
									}
								}
							}
						}											
						deck.set(placement, cd);
					}
				}	
			} finally {
				fin.close();
			} 
		} catch (Exception e) {
			e.printStackTrace();
	    }
	}
//-----------------------------------------------------------------------------------------------------------
}
	



		
