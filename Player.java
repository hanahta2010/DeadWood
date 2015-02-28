// PLayer's class
public class Player {
	
	private String name;
	private int rank;
	private int money;
	private int credit;
	private Room position;
	private Role role;
	
	public Player(String name, int rank) {
		this.name = name;
		this.rank = rank;
		this.money = 0;
		this.credit = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public int getRank() {
		return rank;
	}
	
	public int getMoney() {
		return money;
	}
	
	public int getCredit() {
		return credit;
	}
	
	public Room getPosition() {
		return position;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public void addMoney(int money) {
		this.money += money;
	}
	
	public void addCredit(int credit) {
		this.credit += credit;
	}
	
	public void setPosition(Room position) {
		this.position = position;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public boolean upgradeWithMoney(int rank) {
		if (rank > this.rank && rank <= 6) {
			if (money >= (rank*rank + (rank-2))) {
				this.rank = rank;
				money -= rank*rank + (rank - 2);
				return true;
			}
		}
		return false;
	}

// upgrading with their credit	
	public boolean upgradeWithCredit(int rank) {
		if (rank > this.rank && rank <= 6) {
			if (credit >= (5*(rank - 1))) {
				this.rank = rank;
				credit -= 5 * (rank - 1);
				return true;
			}
		}
		return false;
	}
	
// Calculate the amount they get to pay when they act
	public boolean doAct() {
		int temp;
		Dice dice = new Dice();
		Stage stage = (Stage) position;
		System.out.println("Budget: " + stage.getCard().getBudget());
		
		if ((dice.roll() + role.getRehearseCredit()) >= stage.getCard().getBudget()) {
			if (stage.isIn(role.getName()) != null) {
				addMoney(1);
				addCredit(1);
			} else {
				addCredit(2);
			}
			if (stage.incrementShootCounter() == stage.getMaxCounter()) {
				stage.isFinish();
			}	
			return true;
		} else {
			if (stage.isIn(role.getName()) != null) {
				addMoney(1);
			}
			return false;
		}
	}
}
