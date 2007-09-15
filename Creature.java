import java.awt.image.*;

public class Creature {
	private String name;
	private CreatureType type;
	private BufferedImage img;
	private Gender gender;
	private int level;
	private Point loc;
	private int speed;
	private long lastMoved;
	private int attackSpeed;
	private int damage;
	private long lastAttacked;
	private int strength;
	private int dexterity;
	private int constitution;
	private int charisma;
	private int wisdom;
	private int intelligence;
	private int hitpoints;
	private int manapoints;
	private int maxHitpoints;
	private int maxManapoints;
	
	public Creature() {
		name = "";
		gender = Gender.None;
		loc = new Point(0, 0);
	}

	public Creature(String name) {
		this.name = name;
		loc = new Point(0, 0);
	}
	
	public Creature(String name, Gender gender) {
		this.name = name;
		this.gender = gender;
		loc = new Point(0, 0);
	}
	
	public int getAttackSpeed() {
		return attackSpeed;
	}

	public int getCharisma() {
		return charisma;
	}

	public int getConstitution() {
		return constitution;
	}

	public int getDamage() {
		return damage;
	}

	public int getDexterity() {
		return dexterity;
	}

	public Gender getGender() {
		return gender;
	}

	public int getHitpoints() {
		return hitpoints;
	}
	
	public BufferedImage getImg() {
		return img;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public long getLastAttacked() {
		return lastAttacked;
	}

	public long getLastMoved() {
		return lastMoved;
	}

	public int getLevel() {
		return level;
	}

	public Point getLoc() {
		return loc;
	}

	public int getManapoints() {
		return manapoints;
	}

	public int getMaxHitpoints() {
		return maxHitpoints;
	}

	public int getMaxManapoints() {
		return maxManapoints;
	}

	public String getName() {
		return name;
	}

	public int getSpeed() {
		return speed;
	}

	public int getStrength() {
		return strength;
	}
	
	public CreatureType getType() {
		return type;
	}

	public int getWisdom() {
		return wisdom;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}

	public void setConstitution(int constitution) {
		this.constitution = constitution;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}
	
	public void setImg(BufferedImage img) {
		this.img = img;
	}
 
	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public void setLastAttacked(long lastAttacked) {
		this.lastAttacked = lastAttacked;
	}

	public void setLastMoved(long lastMoved) {
		this.lastMoved = lastMoved;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setLoc(Point loc) {
		this.loc = loc;
	}

	public void setManapoints(int manapoints) {
		this.manapoints = manapoints;
	}

	public void setMaxHitpoints(int maxHitpoints) {
		this.maxHitpoints = maxHitpoints;
	}

	public void setMaxManapoints(int maxManapoints) {
		this.maxManapoints = maxManapoints;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}
	
	public void setType(CreatureType type) {
		this.type = type;
	}

	public void setWisdom(int wisdom) {
		this.wisdom = wisdom;
	}
	
	public String toString() {
		return name;
	}
	
	public boolean equals(Object c) {
		return name.trim().equals(((Creature)c).name.trim());
	}
}
