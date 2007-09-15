import java.awt.image.*;


public class Structure extends MapElement {
private StructureType type;
	
	public Structure(StructureType type, BufferedImage img, boolean passable) {
		super(img, passable);
		
		this.type = type;
	}
	
	public Structure(StructureType type, String imgFile, boolean passable) {
		super(imgFile, passable);
		
		this.type = type;
	}
	
	public StructureType getType() {
		return type;
	}
}
