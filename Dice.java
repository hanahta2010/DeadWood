import java.util.Random;
public class Dice {
	
	private int sides;
	
	public Dice() {
		this.sides = 6;
	}
	
	public int roll() {
		Random r = new Random();
		return r.nextInt(sides) + 1;
	}
	
}