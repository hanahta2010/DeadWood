import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.util.*;
public class RoomView {
	
	public int shotsDone;
	public List<Shotcounter> shots = new ArrayList<Shotcounter>();
	public String name;
	public SceneView sceneview;
	public int[] playerPosition = new int[4];	
	public HashMap<String, Integer> sceneSpot;
	public HashMap<String, Roleview> roles = new HashMap<String, Roleview>();
	private int playerOffset;	
	public String sceneName;		
	public HashMap<String, JLabel> scenes = new HashMap<String, JLabel>();
	
//constructor------------------------------------------------------------------------------
	public RoomView(String id, Layout.Room info) {	
				shotsDone = 0;
				playerOffset = 0;		
				name = id;
				for (Layout.Shot shot: info.shots) {
					Shotcounter s = new Shotcounter(shot.area.get("x"),shot.area.get("y"), shot.area.get("w"), shot.area.get("h"));
					shots.add(s);
				}
				Collections.reverse(shots);
				sceneSpot = info.sceneSpot;
				playerPosition[0] = sceneSpot.get("x");
				playerPosition[1] = sceneSpot.get("y") + 116;
				playerPosition[2] = 46;
				playerPosition[3] = 46; 
				if(!name.equals("trailer") && !name.equals("office")){				
					for(Map.Entry<String, HashMap<String,Integer>> entry : info.roles.entrySet()){		
						HashMap<String,Integer> role = entry.getValue();											
						final String prt = entry.getKey();					
						Roleview r = new Roleview(role.get("x"),role.get("y"), prt, "none", name, 0, 0);						
						roles.put(entry.getKey(), r);
					}
				}
	}
//methods-------------------------------------------------------------------------------------------
	public void nextPlayPosition() {
		playerOffset = (playerOffset + 1) % 4;		
		playerPosition[0] = sceneSpot.get("x") + playerOffset*46;

	}
//--------------------------------------------------------------------------------------

	public void addScene(CardLayout.Card card) {
		sceneview = new SceneView(sceneSpot.get("x"), sceneSpot.get("y"), card, name); 				
	}



	public void shotDone() {
		if(shotsDone < shots.size()) {
			shots.get(shotsDone).setVisible(true);
			shotsDone++;
		}
	}
	
//--------------------------------------------------------------------------------
	}
