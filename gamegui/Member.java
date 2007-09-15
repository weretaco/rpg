package gamegui;

import java.awt.*;
import java.awt.event.*;

public class Member {
	private String name;
	private int x;
	private int y;
	private int width;
	private int height;
	private ScrollBar scrollbar;
	
	public Member(String newName, int newX, int newY, int newWidth, int newHeight) {
		name = newName;
		x = newX;
		y = newY;
		width = newWidth;
		height = newHeight;
	}
	
	public void draw(Graphics g) {

	}
	
	public boolean handleEvent(MouseEvent e) {
		return false;
	}
	
	public boolean isClicked(int xCoord, int yCoord) {
		return x <= xCoord && xCoord <= x+width && y <= yCoord && yCoord <= y+height;
	}
	
	public void clear() {
		
	}
	
	public String getName() {
		return name;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public ScrollBar getScrollBar() {
		return scrollbar;
	}

	public void addScrollBar(ScrollBar newBar) {
		newBar.offset(x, y);
		scrollbar = newBar;
	}
	
	protected void offset(int xOffset, int yOffset) {
		x += xOffset;
		y += yOffset;
		
		if(scrollbar != null)
			scrollbar.offset(xOffset, yOffset);
	}
}
