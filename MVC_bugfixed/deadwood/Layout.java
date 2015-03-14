import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.*;
import java.io.*;
import java.util.Collections;  

public class Layout {
	
//fields------------------------------------------------------------------------------------------
	private String[] playerNameList = {"blue", "cyan", "green", "orange", "pink", "red", "violet", "yellow"};
	public HashMap<String, Room> rooms = new HashMap<String,Room>();

//constructor---------------------------------------------------------------------------------------
		public Layout() {
		try{
			FileInputStream fin = new FileInputStream("../xml/board.xml");
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fin);
				doc.getDocumentElement().normalize();
				Element root = doc.getDocumentElement();
				NodeList board = root.getChildNodes();
				for (int i = 0; i < board.getLength(); i++){
					Node sp = board.item(i);
					if (sp.getNodeType() == Node.ELEMENT_NODE) {
						String name;
						Element s = (Element) sp;
						switch(sp.getNodeName()) {
							case "set":								
								name = s.getAttribute("name");								
								Room roome = new Room();								
								NodeList info = s.getChildNodes();
								for (int j = 0; j< info.getLength(); j++) {
									if (info.item(j).getNodeType() == Node.ELEMENT_NODE) {
										Element field = (Element) info.item(j);
										switch (field.getNodeName()) {
											case "area":  
												HashMap<String,Integer> area = new HashMap<String,Integer>();						
												area.put("x", Integer.parseInt(field.getAttribute("x")));
												area.put("y", Integer.parseInt(field.getAttribute("y")));
												area.put("h", Integer.parseInt(field.getAttribute("h")));
												area.put("w", Integer.parseInt(field.getAttribute("w")));
												roome.sceneSpot = area;												
												break;
											case "parts":  
												NodeList partslist = field.getChildNodes();
												for (int k = 0; k< partslist.getLength(); k++) {
													if (partslist.item(k).getNodeType() == Node.ELEMENT_NODE) {
														Element part = (Element) partslist.item(k);
																												
														if (part.getNodeName().equals("part")) {
															String n = part.getAttribute("name");
															NodeList intel = part.getChildNodes();
															for (int p = 0; p< intel.getLength(); p++) {
																if (intel.item(p).getNodeType() == Node.ELEMENT_NODE) {
																	Element fd = (Element) intel.item(p);
																	HashMap<String, Integer> a = new HashMap<String,Integer>();
																	if (fd.getNodeName().equals("area")) {
																		a.put("x", Integer.parseInt(fd.getAttribute("x")));
																		a.put("y", Integer.parseInt(fd.getAttribute("y")));
																		a.put("h", Integer.parseInt(fd.getAttribute("h")));
																		a.put("w", Integer.parseInt(fd.getAttribute("w")));
																		roome.roles.put(n, a);
																	}
																	
																}
															}
														}	
													}
												}
												
												break;
											case "takes":
												NodeList takes = field.getChildNodes();
												for (int q = 0; q< takes.getLength(); q++) {
													if (takes.item(q).getNodeType() == Node.ELEMENT_NODE) {
														Element take = (Element) takes.item(q);
														Shot shot = new Shot(take);
														roome.shots.add(shot);
													}
												}
											rooms.put(name, roome);
									}
						}
					}
							break;	
								
							default:
								Room r = new Room();								
																
								name = s.getAttribute("name");							
								NodeList ifo = s.getChildNodes();
								
								
								for (int z = 0; z< ifo.getLength(); z++) {
									if (ifo.item(z).getNodeType() == Node.ELEMENT_NODE) {
										Element fl = (Element) ifo.item(z);
										if (fl.getNodeName() == "area") { 
											HashMap<String,Integer> b = new HashMap<String,Integer>();											
											b.put("x", Integer.parseInt(fl.getAttribute("x")));
											b.put("y", Integer.parseInt(fl.getAttribute("y")));
											b.put("h", Integer.parseInt(fl.getAttribute("h")));
											b.put("w", Integer.parseInt(fl.getAttribute("w")));
											r.sceneSpot = b;
										}
										
									}
								}
								
							rooms.put(name, r);		
				}			
			}}
			} finally {
				fin.close();
			} 
		} catch (Exception e) {
			e.printStackTrace();
	    }
	}

//nested classes-----------------------------------------------------------------------------------------------
	
	static public class Room {
		public HashMap<String, Integer> sceneSpot = new HashMap<String, Integer>();
		public List<Shot> shots = new ArrayList<Shot>();
		public HashMap<String, HashMap<String,Integer>> roles = new HashMap<String, HashMap<String,Integer>>();	
	}

//--------------------------------------------------------------------------------------------------------------	
	public class Shot {

		public HashMap<String, Integer> area = new HashMap<String,Integer>(); 

		public Shot(Element take){
			Node a = take.getFirstChild();
			if (a.getNodeType() == Node.ELEMENT_NODE) {
				Element span = (Element) a;
				if (span.getNodeName() == "area") {
					area.put("x", Integer.parseInt(span.getAttribute("x")));
					area.put("y", Integer.parseInt(span.getAttribute("y")));
					area.put("h", Integer.parseInt(span.getAttribute("h")));
					area.put("w", Integer.parseInt(span.getAttribute("w")));
				}
			}
		}
	//---------------------------------------------------------------------------------------
	}
//------------------------------------------------------------------------------------------------------
}
