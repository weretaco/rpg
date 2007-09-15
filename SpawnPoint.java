
public class SpawnPoint {
	Point loc;
	long lastSpawned;
	long interval;
	Creature creature;
	
	public SpawnPoint(Point loc, long interval, Creature creature) {
		this.loc = loc;
		this.interval = interval;
		this.creature = creature;
		lastSpawned = 0;
	}
	
	//calling class handles the actual spawning of the creature if this method returns true
	public boolean spawn() {
		if((System.currentTimeMillis()-lastSpawned) >= interval) {
			lastSpawned = System.currentTimeMillis();
			return true;
		}else {
			return false;
		}
	}
}
