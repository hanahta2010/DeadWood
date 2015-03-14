import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.util.*;
import java.lang.Object;
import java.lang.*;
import java.net.*;

public class Boardview extends JPanel {

	public String[] cards = new String[40];	
	static public ArrayList<PlayerView> players;
	private String[] playerNameList = {"b", "c", "g", "o", "p", "r", "v", "y"};
	private Layout layout;
 	static public CardLayout cardlayout; 	
	static public HashMap<String, RoomView> rooms = new HashMap<String,RoomView>();
	static private int playerCount;
	private MoveButtons moves;
	private JLabel board;
	//public JLabel stats;
	static public PlayerView currentPlayer;
	private UpgradeButtons office;
	private int daynumber;
//constructor-----------------------------------------------------------------------------------------
	public CardLayout cardlayout() {
		return cardlayout;
	}
	public Boardview (int[] cardorder, int numPlayers) {
     	super(null);
		setSize(1200,900);
    	setDoubleBuffered(true);
		daynumber = 0;
		//make and add office
		office = new UpgradeButtons();
		for(int i = 0; i<10; i++){
			add(office.buttons[i]);
		} 
		//make and add players
		playerCount = numPlayers;		
		int rank = numPlayers/7 + 1;
		players = new ArrayList<PlayerView>();		
		for (int i = 0; i < numPlayers; i++) {
			String image = "../dice/" + playerNameList[i] + rank + ".png";					
			PlayerView p = new PlayerView(0,0, image, playerNameList[i]);			
			players.add(p);
			add(p);		
		}
		
		//set first turn		
		currentPlayer = players.get(0);
		
		//find out where everything is
		layout = new Layout();
		cardlayout = new CardLayout(cardorder);
		
		//make list of card picture names
		for (int i = 1; i<10; i++) {
			cards[i-1] = "0" + i + ".png";
		}
		for (int i = 10; i<41; i++) {
			cards[i-1] = i + ".png";
		}

		//make rooms;
		for(Map.Entry<String, Layout.Room> entry : layout.rooms.entrySet()){
			String name = entry.getKey();
			RoomView r = new RoomView(name,entry.getValue());
			rooms.put(name,r);	
		}
		
		//add rooms;	

		for(Map.Entry<String, RoomView> entry : rooms.entrySet()){					
			RoomView r = entry.getValue();			
			if ( !entry.getKey().equals("trailer") && !entry.getKey().equals("office")){			
				for(Shotcounter s: r.shots){
					add(s);
				}		
				for(Map.Entry<String, Roleview> e : r.roles.entrySet()){														
					add(e.getValue());
				}			
			}	
		}
		
		//add scenes to sets and move players to trailers		
		//beginday();
		for(Map.Entry<String, RoomView> entry : rooms.entrySet()){	
				if ( !entry.getKey().equals("trailer") && !entry.getKey().equals("office")){
								
								
					RoomView r = entry.getValue();	
					for (Shotcounter shot: r.shots) {
						shot.setVisible(false);
					}				
					CardLayout.Card card = cardlayout.deck.remove(0);												
					r.addScene(card);
					add(r.sceneview);
					r.shotsDone = 0;
				}
			}
		//}
		daynumber += 1;		
		gotoTrailers();
		//add move buttons
		moves = new MoveButtons();
		for(int i = 0; i<12; i++) {
			add(moves.movebuttons[i]);
		} 		
		
		//add board
		board = new JLabel();
		add(board);
		board.setBounds(0,0,1200,900);
		try {
			ImageIcon image = new ImageIcon (
		      ImageIO.read(
		        new File(String.format("../images/board.jpg", "i"))));
			board.setIcon(image);
		}catch (IOException e) {
		  e.printStackTrace();
		  System.exit(1);
		}
	}

//board functions----------------------------------------------------------------	
	
	public void beginday() {
		if (daynumber != 0) {
			for(Map.Entry<String, RoomView> entry : rooms.entrySet()){	
				if ( !entry.getKey().equals("trailer") && !entry.getKey().equals("office")){
					RoomView r = entry.getValue();
					remove(r.sceneview);
					repaint();
				}	
			}				
		}
		remove(board);
		repaint();
		for(Map.Entry<String, RoomView> entry : rooms.entrySet()){	
				if ( !entry.getKey().equals("trailer") && !entry.getKey().equals("office")){
								
								
					RoomView r = entry.getValue();	
					for (Shotcounter shot: r.shots) {
						shot.setVisible(false);
					}				
					CardLayout.Card card = cardlayout.deck.remove(0);												
					r.addScene(card);
					add(r.sceneview);
					repaint();					
					r.shotsDone = 0;
				}
			}
		//}
		getParent().repaint();		
		daynumber += 1;		
		gotoTrailers();
		add(board);
	}
	
	//maybe move this function	
	public static void newPlayer(){
		currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % playerCount);
		
	}

	public static void gotoTrailers() {
		int xoffset; 	
		int yoffset;		
		for (int i = 0; i < players.size(); i++) {					
			xoffset = 47 * (i % 4);
			yoffset = i/4 * 47;
			players.get(i).setBounds(991 + xoffset, 248 + yoffset, 46,46);	
			players.get(i).room = "trailer";		
		}
	}

	
//------------------------------------------------------------------------------------------
}

