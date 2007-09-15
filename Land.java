import java.awt.image.*;

public class Land extends MapElement {
	private LandType type;
	
	public Land(LandType type, BufferedImage img, boolean passable) {
		super(img, passable);

		this.type = type;
	}
	
	public Land(LandType type, String imgFile, boolean passable) {
		super(imgFile, passable);
		
		this.type = type;
	}
	
	public LandType getType() {
		return type;
	}
}
