import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

// Parsing through board.xml
public class BoardParse {
	
	public static HashMap<String, Room> BP () {
		
		HashMap<String, Room> rooms = new HashMap<String, Room>();
		
		try {
			FileInputStream file = new FileInputStream("../xml/board.xml");
			
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				
				Document doc = builder.parse(file);
				
				doc.getDocumentElement().normalize();
				
				Element root = doc.getDocumentElement();
				
				IterableNodeList<Node> roomList = new IterableNodeList<Node>(root.getChildNodes());
				for (Node r: roomList) {	
					if(r.getNodeType() == Node.ELEMENT_NODE) {
						Element room = (Element) r;
						switch(room.getNodeName()){
						case "set":
							Stage stage = buildStage(room);
							rooms.put(stage.getName(), stage);
							break;
						case "trailer":
							Room rm1 = buildTO(room);
							rooms.put(rm1.getName(), rm1);
							break;
						case "office":
							Room rm2 = buildTO(room);
							rooms.put(rm2.getName(), rm2);
							break;
						}
					}
				}
			}
			finally {
				file.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rooms;
	}

// parsing the stages 	
	public static Stage buildStage(Element room) {
		String partName = null;
		String level = null;
		String line = null;
		int number = 0;
		String name = room.getAttribute("name");
		ArrayList<String> neighbors = new ArrayList<String>();
		ArrayList<Role> rolesInRoom = new ArrayList<Role>();
		
		IterableNodeList<Node> roomE = new IterableNodeList<Node>(room.getChildNodes());
		for(Node rr: roomE) {
			if(rr.getNodeType() == Node.ELEMENT_NODE) {
				Element rrr = (Element) rr;
				switch(rrr.getNodeName()) {
				case "neighbors":
					IterableNodeList<Node> childN = new IterableNodeList<Node>(rrr.getChildNodes());
					for(Node cN : childN) {
						if(cN.getNodeType() == Node.ELEMENT_NODE) {
							Element cNN = (Element) cN;
							String neighborName = cNN.getAttribute("name");
							neighbors.add(neighborName);
						}
					}
					break;
				case "takes":
					IterableNodeList<Node> childT = new IterableNodeList<Node>(rrr.getElementsByTagName("take"));
					number = childT.getLength() / 2;
					break;
				case "parts":
					IterableNodeList<Node> childP = new IterableNodeList<Node>(rrr.getChildNodes());
					for(Node cP : childP) {
						if(cP.getNodeType() == Node.ELEMENT_NODE) {
							Element part = (Element) cP;
							partName = part.getAttribute("name");
							level = part.getAttribute("level");
							IterableNodeList<Node> childPP = new IterableNodeList<Node>(part.getChildNodes());
							for (Node cPs : childPP) {
								switch (cPs.getNodeName()) {
								case "line" :
									line = cPs.getTextContent();
									break;
								case "area":
									break;
								}
							}
							rolesInRoom.add(new Role(partName, level, line));
						}
					}
					break;
				}
			}
		}
		return new Stage(name, neighbors, rolesInRoom, number);
	}

// building the room	
	public static Room buildTO(Element room) {
		ArrayList<String> neighbors = new ArrayList<String>();
		String name = room.getNodeName();
		
		IterableNodeList<Node> roomE = new IterableNodeList<Node>(room.getChildNodes());
		for(Node rr: roomE) {
			if(rr.getNodeType() == Node.ELEMENT_NODE) {
				Element rrr = (Element) rr;
				switch(rrr.getNodeName()) {
				case "neighbors":
					IterableNodeList<Node> childN = new IterableNodeList<Node>(rrr.getChildNodes());
					for(Node cN : childN) {
						if(cN.getNodeType() == Node.ELEMENT_NODE) {
							Element cNN = (Element) cN;
							String neighborName = cNN.getAttribute("name");
							neighbors.add(neighborName);
						}
					}
					break;
				case "area":
					break;
				}
			}
		}
		return new Room(name, neighbors);
	}
}
