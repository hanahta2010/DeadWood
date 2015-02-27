
public class Role {
	
	private String name;
	private int rank;
	private String line;
	private int rehearseCredit;
	private boolean isAvailable = true;
	
	public String getName() {
		return name;
	}
	
	public int getRank() {
		return rank;
	}
	
	public String getLine() {
		return line;
	}

	public int getRehearseCredit() {
		return rehearseCredit;
	}
	
	public Role(String name, String rank, String line) {
		this.name = name;
		this.rank = Integer.parseInt(rank);
		this.line = line;
		this.rehearseCredit = 0;
	}
	
	public void rehearse() {
		rehearseCredit += 1;
	}
	
	public boolean getIsAvailable() {
		return isAvailable;
	}
	
	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
}
		