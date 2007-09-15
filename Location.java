import java.util.*;

public class Location {
	private Land land;
	private Structure structure;
	private PriorityQueue<Creature> creatures;
	
	public Location(Land land, Structure structure) {
		this.land = land;
		this.structure = structure;
		this.creatures = new PriorityQueue<Creature>();
	}

	public void addCreature(Creature creature) {
		creatures.add(creature);
	}
	
	public Land getLand() {
		return land;
	}

	public Structure getStruct() {
		return structure;
	}
	
	public void setLand(Land type) {
		land = type;
	}
	
	public void setStruct(Structure type) {
		structure = type;
	}
	
	public boolean isPassable() {
		return land.isPassable() && structure.isPassable();
	}
}
