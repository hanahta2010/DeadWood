import java.util.*;

public abstract class Deadwood {

  // These methods will be implemented by a derived class and will do the
  // correct thing, whatever that might be.
  public abstract boolean gameOver();
  public abstract void who();
  public abstract void where();
  public abstract void move(String to);
  public abstract void work(String part);
  public abstract void upgradeDollars(int rank);
  public abstract void upgradeCredit(int rank);
  public abstract void rehearse();
  public abstract void end();
  public abstract void act();
  
  // The is the factory function that builds the correct type.
  public static Deadwood build (int numPlayer) {
    return new GameMaster(numPlayer);
  }

  // Very simple main program that parses the console game's language.  
  // You could probably do this better with regular expressions, but since it is
  // only for testing, this is good enough.  
  public static void main(String[] args)
  {
    Deadwood dw = build(Integer.parseInt(args[0]));
    Scanner in = new Scanner(System.in);

    while(!dw.gameOver() && in.hasNext()) {

      String cmd = in.next();
      switch(cmd) {
      case "who": 
        dw.who(); 
        break;
        
      case "where":
        dw.where();
        break;

      case "move": 
        dw.move(in.nextLine().trim());
        break;

      case "work":
        dw.work(in.nextLine().trim());
        break;

      case "upgrade":
        String currency = in.next().trim();
        int rank = in.nextInt();
        switch(currency) {
        case "$":
          dw.upgradeDollars(rank);
          break;
        case "cr":
          dw.upgradeCredit(rank);
          break;
        default:
          System.out.println("Unknown currency \"" + currency +"\"");
        }
        break;

      case "rehearse":
        dw.rehearse();
        break;
        
      case "act":
    	dw.act();
    	break;
    	
      case "end":
        dw.end();
        break;

      default:
        System.out.println("Unknown command \""+cmd+"\"");        
      }
      
    }
    
  }
}

// This is a dummy implementation of deadwood.  This is just to show you how you
// might extended the deadwood base class.  Can you figure out the quote for the
// end command?  
class GameMaster extends Deadwood {
  private boolean isOver = false;
  private Player current_player;
  private int playerIndex;
  private ArrayList<Player> playerList = new ArrayList<Player>();
  private int numPlayer;
  private int rank;
  private String[] names = {"blue" , "red", "yellow", "black", "white", "cyan", "pink", "magenta"};
  private int dayCount = 0;
  private int maxDays;
  private int cardCount;
  private int totalScore;
  private int stageNum;
  private ArrayList<Card> cards = CardParse.CP();
  private HashMap<String, Room> rooms = BoardParse.BP();
  
// Game Manager
  public GameMaster(int numPlayer) {
	  this.numPlayer = numPlayer;
	  if (numPlayer <= 6) {
		  rank = 1;
	  } else {
		  rank = 2;
	  }
	  for (int i = 0; i < numPlayer; i++) {
		  playerList.add(new Player(names[i], rank));
		  if (numPlayer == 5) {
			  playerList.get(i).addCredit(2);
		  } else if (numPlayer == 6) {
			  playerList.get(i).addCredit(4);
		  }
		  playerList.get(i).setPosition(rooms.get("trailer"));
	  }
	  if (numPlayer <= 3) {
		  maxDays = 3;
	  } else {
		  maxDays = 4;
	  }
	  playerIndex = 0;
	  current_player = playerList.get(playerIndex);
	  dayStart();
	  
  }

// Trigger when the game is over 
  public boolean gameOver() {
	  int countTemp = 0;
	  for ( Map.Entry<String, Room> entry : rooms.entrySet()) {
		    String key = entry.getKey();
		    if (entry.getValue() instanceof Stage){
		    	Stage room = (Stage) entry.getValue();
		    	if(!room.isClose()) {
		    		countTemp++;
		    	}
		    }
	  }
	  if (countTemp == 1 ) {
		  return dayEnd();
	  }
    if (dayCount == maxDays) {
    	return true;
    } else {
    	return false;
    }
  }

// Tell who turn it's on and their information
  public void who() {
	  System.out.println(current_player.getName() + "(" + current_player.getMoney() + ", " + current_player.getCredit() + ")");
	  if (current_player.getRole() != null) {
		  System.out.println(" working " + current_player.getRole().getName() + ", " + current_player.getRole().getLine());
		  System.out.println();
	  } else {
		  System.out.println(" just sitting around and drinking tea...I wish.");
		  System.out.println();
	  }
  }

// Display where the player is at and some useful information like the room the 
// can move to, the roles they can act in.  
  public void where() {
	  int temp = 0;
	System.out.print("in " + current_player.getPosition().getName()); 
	System.out.println();
	
	if (current_player.getRole() != null) {
		System.out.print(" shooting " + current_player.getRole().getName() + " scene " + ((Stage) current_player.getPosition()).getCard().getScene());
		System.out.println();
	} 
    if (current_player.getPosition().getName().equals("office") || current_player.getPosition().getName().equals("trailer")) {
    	System.out.println();
    	System.out.println("You can move here to try to work: ");
		for (int k = 0; k < current_player.getPosition().getNeighbor().size(); k++) {
			System.out.println(current_player.getPosition().getNeighbor().get(k));
		}
		System.out.println();
    } else {
    	Stage stage = (Stage) current_player.getPosition(); 
    	System.out.println();
    	System.out.println("You can move here to try to work: ");
		for (int k = 0; k < stage.getNeighbor().size(); k++) {
			System.out.println(stage.getNeighbor().get(k));
		}
		System.out.println();
    	if (stage.isClose()) {
    		System.out.println("Room is closed! No work available!");
    		System.out.println();
    	} else {
	    	ArrayList<Role> extras = stage.getExtraRole();
	    	ArrayList<Role> mains = stage.getCard().getMainRoles();
	    	System.out.println("Extra Roles are: ");
	    	for (int i = 0; i < extras.size(); i++) {
	    		System.out.println(extras.get(i).getName() + " " + extras.get(i).getRank());
	    	}
	    	System.out.println();
	    	System.out.println("Card is in this game is: " + stage.getCard().getName());
	    	System.out.println();
	    	System.out.println("Main Roles Available are: ");
	    	for (int j = 0; j < mains.size(); j++) {
	    		System.out.println(mains.get(j).getName() + " " + mains.get(j).getRank());
	    	}
	    	System.out.println();
    	}	
    }
  }

// This is function to tell the player to move.
  public void move(String to) {
	  Room currentRoom = current_player.getPosition();
	  Room nextRoom = rooms.get(to);
	  if (current_player.getRole() != null) {
		  System.out.println("STOPP!!! Finish your job...Why are you so irresponsible?");
		  System.out.println();
	  } else {
		  if (currentRoom.isAdjacent(to)) {
			  current_player.getPosition().leaveRoom(current_player);
			  current_player.setPosition(nextRoom);
			  current_player.getPosition().enterRoom(current_player);
			  System.out.println("You are moved to: " + current_player.getPosition().getName());
			  System.out.println();
		  }
	  }
  }

// This function let the player take on a role
  public void work(String role) {
	  if (current_player.getRole() != null) {
		  return;
	  }
	  if (current_player.getPosition().getName().equals("trailer") || current_player.getPosition().getName().equals("office")) {
		System.out.println("You're not in a room with roles. You can't act on a role. Shame on you for not remember where you're at.");
		System.out.println();
	  } else {
		  Stage currentRoom = (Stage) current_player.getPosition();
		  if (currentRoom.isClose()) {
			  System.out.println("Room is closed. Can't work. Sorry!");
			  System.out.println();
			  return;
		  } 
		  ArrayList<Role> roles = currentRoom.getExtraRole();
		  Role extraRole = currentRoom.isIn(role);
		  Role mainRole = currentRoom.getCard().isInCard(role);
		  
		  if(extraRole != null){
			  if (extraRole.getIsAvailable() && (current_player.getRank() >= extraRole.getRank())) {
				  current_player.setRole(extraRole);
				  extraRole.setIsAvailable(false);
				  System.out.println("You are trying to work as " + role);
				  System.out.println();
			  } else {
				  System.out.println("Your rank isn't enough to work here or maybe the role isn't available. Too bad!");
				  System.out.println();
			  }
		  }
		  else if(mainRole != null){
			  if (mainRole.getIsAvailable() && (current_player.getRank() >= mainRole.getRank())) {
				  current_player.setRole(mainRole);
				  mainRole.setIsAvailable(false);
				  System.out.println(current_player.getName() + " is working on " + mainRole.getName());
				  System.out.println();
			  } else {
				  System.out.println("Your rank isn't enough to work here or maybe the role isn't available. Too bad!");
				  System.out.println();
			  }
		  }
		  else{
			  System.out.println("You are working on something! Do what you're supposed to. Jeez...");
			  System.out.println();
		  }
	  }
  }

// This is function where the players upgrade their rank with money
  public void upgradeDollars(int rank) {
	  if (!current_player.getPosition().getName().equals("office")) {
		  System.out.println("You can't upgrade. You forgot you're not in casting office. Too bad!");
		  System.out.println();
	  } else {
		  if (current_player.upgradeWithMoney(rank)) {
			  System.out.format("Upgrade to %d with dollars\n", rank);
			  System.out.println();
		  } else {
			  System.out.format("I didn't upgrade. Too bad! Dumb me cause I can get more money. Sorry, I don't like money.\n");
			  System.out.println();
		  }
	  }
  }

// This is function where the players upgrade their rank with credit
  public void upgradeCredit(int rank){
	  if (current_player.upgradeWithCredit(rank)) {
		  System.out.format("Upgrade to %d with credits\n", rank);
		  System.out.println();
	  } else {
		  System.out.format("I don't have enough credit.\n");
		  System.out.println();
	  }
  }

// This function is for player to rehearse
  public void rehearse() {
	  	current_player.getRole().rehearse();
	  	System.out.format("You have %d in rehearse credit\n", current_player.getRole().getRehearseCredit());
	  	System.out.println();
  }

// Trigger that the player is ending their turn;
  public void end() {
	playerIndex++;
	current_player = playerList.get(playerIndex % numPlayer);
  }
 
// New day begins 
  public void dayStart() {
	  for ( Map.Entry<String, Room> entry : rooms.entrySet()) {
		    String key = entry.getKey();
		    if (entry.getValue() instanceof Stage){
		    	Stage room = (Stage) entry.getValue();
		    	room.resetShootCounter();
		    	room.setCard(getRandomCard());
		    	room.resetExtraRoles();
		    }
		}
	  for (int i = 0; i < playerList.size(); i++) {
		  playerList.get(i).setPosition(rooms.get("trailer"));
	  }
	  stageNum = 10;
  }

// Day has end  
  public boolean dayEnd() {
	  dayCount++;
	  Player maxPlayer = null;
	  int maxPlayerS = 0;
	  if(dayCount < maxDays) {
		  dayStart();
		  return false;
	  } 
	  for (int i = 0; i < playerList.size(); i++) {
		  System.out.println("Player " + playerList.get(i).getName() + " has " + finalScore(playerList.get(i)) + " points.");
		  if (finalScore(playerList.get(i)) > maxPlayerS) {
			  maxPlayerS = finalScore(playerList.get(i));
			  maxPlayer = playerList.get(i);
		  }
	  }
	  System.out.println("The player " + maxPlayer.getName() + " is a winner. Congratulation! Too bad, no prize... :P");
	  System.out.println();
	  return true;
  }

// Let the player acts.  
  public void act() {
	  if (current_player.getRole() == null) {
		  System.out.println("I'm not on a role. What am I thinking??? ");
		  System.out.println();
	  } else {
		  if (current_player.doAct()) {
 			  System.out.println("Hooray...who knew you can be useful???");  
 			  System.out.println();
		  } else {
			  System.out.println("You failed! You're useless...");
			  System.out.println();
		  }
	  }
  }

// Calculate the player's score  
  public int finalScore(Player player) {
	  Player current_player = player;
	  int money = current_player.getMoney();
	  int credit = current_player.getCredit();
	  int rank = current_player.getRank();
	  totalScore = money + credit + rank * 5;
	  return totalScore;
  }
  
// Make the cards distribute randomly
  public Card getRandomCard() {
	  Random c = new Random();
	  int index = c.nextInt(cards.size());
	  return cards.remove(index);
  }
}
