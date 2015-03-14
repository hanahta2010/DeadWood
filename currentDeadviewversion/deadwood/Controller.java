import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.util.*;
import java.awt.Point;
public class Controller {
	
	
//fields--------------------------------------------------------------------	
	static public Deadwood dead;
	static public Deadview dv;
	static public int theDay = 0;
//static methods-----------------------------------------------------------------
	
	static public void move (String location){
		if(dead.move(location)) {
			RoomView room = Boardview.rooms.get(location);			
			Boardview.currentPlayer.setBounds(room.playerPosition[0],room.playerPosition[1],room.playerPosition[2],room.playerPosition[3]);		
			room.nextPlayPosition();
			Boardview.currentPlayer.room = location;
			Controller.refreshStats();		
		}
	}

//-------------------------------------------------------------------------------------------------
	static public void takeRole(Roleview role, String location, String part, String scene){
		if(dead.work(part)) {
			if (scene.equals("none")){			
				RoomView room = Boardview.rooms.get(location);			
				Roleview p = room.roles.get(part);			
				Point z = p.getLocation();			
				Boardview.currentPlayer.setBounds((int)z.getX() + role.xdisplacement ,(int)z.getY() + role.ydisplacement,46,46);
				
			} else {
				Point q = role.getLocation();
				Boardview.currentPlayer.setBounds((int)q.getX()+ role.xdisplacement,(int)q.getY()+ role.ydisplacement,46,46);
				
			}
		}

	}

//----------------------------------------------------------------------------------------------------
	static public void rehearse(String player) {
		dead.rehearse();
		
	}

//----------------------------------------------------------------------------------------------
	static public void upGrade(String currency, String rank) {
		if(currency.equals("dollar")) {
			
			
			
			if (dead.upgradeDollars(Integer.parseInt(rank))) {
				String image = "../dice/" + Boardview.currentPlayer.name + rank + ".png";
				try {
					ImageIcon img = new ImageIcon (
				      ImageIO.read(
				        new File(String.format(image, "i"))));
					Boardview.currentPlayer.setIcon(img);
				}catch (IOException e) {
				  e.printStackTrace();
				  System.exit(1);
				}	

			}

		} else {
			if (dead.upgradeCredit(Integer.parseInt(rank))) {
				String image = "../dice/" + Boardview.currentPlayer.name + rank + ".png";
				try {
					ImageIcon img = new ImageIcon (
				      ImageIO.read(
				        new File(String.format(image, "i"))));
					Boardview.currentPlayer.setIcon(img);
				}catch (IOException e) {
				  e.printStackTrace();
				  System.exit(1);
				}
			}
		}
	}

//---------------------------------------------------------------------------------------------
	static public void act() {
		if (dead.act()) {
			Boardview.rooms.get(Boardview.currentPlayer.room).shotDone();
			Controller.refreshResults("Hooray...who knew you can be useful???");
		}
		else {
			Controller.refreshResults("You failed or you aren't working! You're useless...");
		}
	}
	

//----------------------------------------------------------------------------------------------
	static public void end() {
		Boardview.newPlayer();	
		dead.end();
		refreshStats();
		refreshResults("It is now " + Boardview.currentPlayer.name + " turn!");
	}

	static public void refreshStats() {
		dv.stats.remove(dv.stats.sub1);
		dv.stats.repaint();
		dv.mainFrame.repaint();		
		String current_role;
		int rehearse;
		Player player = dead.getCurrentPlayer();
		if (player.getRole() == null) {
			current_role = "No role";
			rehearse = 0;
		} else {
			current_role = player.getRole().getName();
			rehearse = player.getRole().rehearseCredit;
		}
		Status newstats = new Status(player.getName(), Integer.toString(player.getRank()), player.getPosition().getName(), 
									 Integer.toString(player.getMoney()), Integer.toString(player.getCredit()), 
									 current_role, Integer.toString(rehearse));	
		dv.stats.sub1 = newstats;
		dv.stats.repaint();		
		dv.stats.add(dv.stats.sub1);
		dv.stats.repaint();				
		dv.mainFrame.repaint();	
	}	
//----------------------------------------------------------------------------------------------------------------\
	
	static public void refreshResults(String comment){
		dv.stats.remove(dv.stats.sub3);
		dv.stats.repaint();
		dv.mainFrame.repaint();		
		if (theDay != dead.getDay()) {
			dv.board.beginday();
			theDay++;
		}
		Results newresults = new Results(0, comment, dead.getDay(), 0, 0);	
		dv.stats.sub3 = newresults;
		dv.stats.repaint();		
		dv.stats.add(dv.stats.sub3);
		dv.stats.repaint();				
		dv.mainFrame.repaint();	
	}


//main method---------------------------------------------------------------------------------	
	public static void main(String[] args)
	  {
		dead = Deadwood.build(Integer.parseInt(args[0]));
	    Scanner in = new Scanner(System.in);
	    
		
		int[] deckorder = new int[40];		
		for (int i = 0; i<40; i++) {
			deckorder[i] = dead.cards().get(i).scene;	
		}
		
		//bug bandaid because of misalignment of scenes from view and model		
		int temp1;
		for (int k = 0; k<40; k+=10){	 	
			//swap indexes for train station and bank			
			temp1 = deckorder[k+ 3];
			deckorder[k+3] = deckorder[k+4];
			deckorder[k+4] = temp1;
			
			//swap indexes for General Store and Church
			temp1 = deckorder[k+6];
			deckorder[k+6] = deckorder[k+8];
			deckorder[k+8] = temp1;
		}
		
		try {
	         dv = new Deadview(deckorder, Integer.parseInt(args[0]));
	      
			} catch (IOException e) {
	        e.printStackTrace();
	      }		
		//dead.ded = dv;
		dead.dayStart();
		
		while(!dead.gameOver() && in.hasNext()) {

	      String cmd = in.next();
	      switch(cmd) {
	      case "who": 
	        dead.who(); 
	        break;
	        
	      case "where":
	        dead.where();
	        break;

	      case "move": 
	        dead.move(in.nextLine().trim());
	        break;

	      case "work":
	        dead.work(in.nextLine().trim());
	        break;

	      case "upgrade":
	        String currency = in.next().trim();
	        int rank = in.nextInt();
	        switch(currency) {
	        case "$":
	          dead.upgradeDollars(rank);
	          break;
	        case "cr":
	          dead.upgradeCredit(rank);
	          break;
	        default:
	          System.out.println("Unknown currency \"" + currency +"\"");
	        }
	        break;

	      case "rehearse":
	        dead.rehearse();
	        break;
	        
	      case "act":
	    	dead.act();
	    	break;
	    	
	      case "end":
	        dead.end();
	        break;

	      default:
	        System.out.println("Unknown command \""+cmd+"\"");   
	        System.out.println();
	      }
	      
	    }
	    
	  }
//---------------------------------------------------------------------------------------	
}
	
