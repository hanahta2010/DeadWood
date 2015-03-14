import java.util.*;

// Room's class
public class Room {
	private String name;
	private ArrayList<String> neighbor = new ArrayList<String>();
	protected ArrayList<Player> players = new ArrayList<Player>();
	
	// Constructor
	public Room(String name, ArrayList<String> neighbor) {
		this.name = name;
		this.neighbor = neighbor;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isAdjacent(String to) {
		if (neighbor.contains(to)) {
			return true;
		}
		return false;
	}
	
	public ArrayList<String> getNeighbor() {
		return neighbor;
	}
	
	public void enterRoom(Player player) {
		players.add(player);
	}
	
	public void leaveRoom(Player player) {
		players.remove(player);
	}
	
}

// Stage's class where it's inherited from Room with some extensions. 
class Stage extends Room {
	
	private ArrayList<Role> extraRole = new ArrayList<Role>();
	private int shootCounter;
	private int maxShoot;
	private Card card;
	
	public Stage(String name, ArrayList<String> neighbor, ArrayList<Role> extraRole, int shootCounter) {
		super(name, neighbor);
		this.extraRole = extraRole;
		this.shootCounter = 0;
		this.maxShoot = shootCounter;
	}
	
	public Card getCard() {
		return card;
	}
	
	public void setCard(Card card) {
		this.card = card;
	}
	
	public ArrayList<Role> getExtraRole() {
		return extraRole;
	}
	
	public void resetExtraRoles() {
		for (int i = 0; i < extraRole.size(); i++) {
			extraRole.get(i).setIsAvailable(true);
		}
	}
	
	public int getMaxCounter() {
		return maxShoot;
	}

//  Checking to see if the role is belong to one of the extra roles.	
	public Role isIn (String role) {
		for (int i = 0; i < extraRole.size(); i++) {
			if (role.equals(extraRole.get(i).getName())) {
				return extraRole.get(i);
			} 
		}
		return null;
	}

// Show how many shoot counter there is and how much progress on it. 	
	public int incrementShootCounter() {
		System.out.println("Max shots: " + maxShoot);
		shootCounter++;
		System.out.println("Current shots : " + shootCounter);
		return shootCounter;
	}

// Check if the room is closed or still available for jobs. 	
	public boolean isClose() {
		if (maxShoot == shootCounter) {
			return true;
		} else {
			return false;
		}
	}
	public void resetShootCounter() {
		shootCounter = 0;
	}

// Trigger when the room is finished.	
	public void isFinish() {
		Dice dice = new Dice();
		Role current_role;
		ArrayList<Integer> diceRolls = new ArrayList<Integer>();
		for (int i = 0; i < card.getBudget(); i++) {
			diceRolls.add(dice.roll());
		}
		Collections.sort(diceRolls);
		Collections.reverse(diceRolls);
		ArrayList<Role> mainRoles = card.getMainRoles();
		Collections.reverse(mainRoles);
		
		for (int j = 0; j < card.getBudget(); j++) {
			current_role = mainRoles.get(j % mainRoles.size());
			for (int k = 0; k < players.size(); k++) {
				if ((players.get(k).getRole() != null) && (players.get(k).getRole().getName().equals(current_role.getName()))) {
					players.get(k).addMoney(diceRolls.get(j));
				}
			}
		}
		for (int h = 0; h < extraRole.size(); h++) {
			current_role = extraRole.get(h);
			for (int g = 0; g < players.size(); g++) {
				if ((players.get(g).getRole() != null) && (players.get(g).getRole().getName().equals(current_role.getName()))) {
					players.get(g).addMoney(current_role.getRank());
				}
			}
		}
		for (int l = 0; l < players.size(); l++) {
			current_role = null;
			players.get(l).setRole(current_role);
		}
		//stageNum--;
	}
}
