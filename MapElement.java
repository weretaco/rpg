import java.awt.image.*;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapElement {
	private BufferedImage img;
	private boolean passable;
	
	public MapElement(BufferedImage img, boolean passable) {
		this.img = img;
		this.passable = passable;
	}
	
	public MapElement(String imgFile, boolean passable) {
		try {
			img = ImageIO.read(getClass().getResource("images/"+imgFile));
			this.passable = passable;
		}catch(IOException ioe) {
    		ioe.printStackTrace();
    	}
	}
	
	public BufferedImage getImg() {
		return img;
	}
	
	public boolean isPassable() {
		return passable;
	}
	
	public void setImg(BufferedImage img) {
		this.img = img;
	}
	
	public void setPassable(boolean passable) {
		this.passable = passable;
	}
}
