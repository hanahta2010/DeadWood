import java.util.*;

public class Card {
	private String name;
	private int scene;
	private String description;
	private int budget;
	private ArrayList<Role> mainRoles = new ArrayList<Role>();
	
	public Card (String name, String scene, String description, String budget, ArrayList<Role> mainRoles) {
		this.name = name;
		this.scene = Integer.parseInt(scene);
		this.description = description;
		this.budget = Integer.parseInt(budget);
		this.mainRoles = mainRoles;
	}
	
	public String getName() {
		return name;
	}
	
	public int getScene() {
		return scene;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getBudget() {
		return budget;
	}
	
	public ArrayList<Role> getMainRoles() {
		return mainRoles;
	}
	
	public Role isInCard (String role) {
		for (int i = 0; i < mainRoles.size(); i++) {
			if (role.equals(mainRoles.get(i).getName())) {
				return mainRoles.get(i);
			} 
		}
		return null;
	}
	
}
