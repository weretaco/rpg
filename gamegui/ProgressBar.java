package gamegui;

import java.awt.Color;
import java.awt.Graphics;

public class ProgressBar extends Member {
	private int max;
	private int current;
	
	public ProgressBar(String newName, int newX, int newY, int newWidth, int newHeight) {
		super(newName, newX, newY, newWidth, newHeight);
		
		max = 1;
		current = 0;
	}
	
	public int getMax() {
		return max;
	}
	
	public int getCurrent() {
		return current;
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	public void setCurrent(int current) {
		this.current = current;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.black);
    	g.fillRect(getX(), getY(), getWidth(), getHeight());
    	
    	g.setColor(Color.blue);
		g.fillRect(getX(), getY(), getWidth()*current/max, getHeight());
		g.setColor(Color.red);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
	}
}
