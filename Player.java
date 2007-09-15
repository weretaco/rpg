
public class Player extends Creature {
	private Point target;
	private int experience;
	private int gold;
	
	public Player() {
		super();
		target = new Point(0, 0);
	}

	public Player(String name) {
		super(name);
		target = new Point(0, 0);
	}
	
	public Player(String name, Gender gender) {
		super(name, gender);
		target = new Point(0, 0);
	}
	
	public int getExperience() {
		return experience;
	}
	
	public int getGold() {
		return gold;
	}
	
	public Point getTarget() {
		return target;
	}
	
	public void setExperience(int experience) {
		this.experience = experience;
	}
	
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public void setTarget(Point target) {
		this.target = target;
	}
}
