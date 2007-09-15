package gamegui;

import java.awt.*;
import java.awt.event.*;

public class ScrollBar extends Member {
	int size;
	int position;
	int scrollSpeed;
	
	public ScrollBar(String newName, int newX, int newY, int newWidth, int newHeight, int newScrollSpeed) {
		super(newName, newX, newY, newWidth, newHeight);
		
		size = 0;
		position = 0;
		scrollSpeed = newScrollSpeed;
	}
	
	public void clear() {
		size = 0;
		position = 0;
	}
	
	public boolean handleEvent(MouseEvent e) {
		if(!(getX() < e.getX() && e.getX() < getX()+getWidth() && getY() < e.getY() && e.getY() < getY()+getHeight()))
			return false;
		else
			return true;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.black);
    	g.fillRect(getX(), getY(), getWidth(), getHeight());
    	
    	g.setColor(Color.red);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		
		g.drawLine(getX(), getY()+getWidth(), getX()+getWidth(), getY()+getWidth());
		g.drawLine(getX(), getY()+getHeight()-getWidth(), getX()+getWidth(), getY()+getHeight()-getWidth());
    	
		g.drawLine(getX(), getY()+getWidth()+position, getX()+getWidth(), getY()+getWidth()+position);
		g.drawLine(getX(), getY()+getWidth()+position+size, getX()+getWidth(), getY()+getWidth()+position+size);
		
		g.drawLine(getX()+getWidth()*3/20, getY()+getWidth()*17/20, getX()+getWidth()*17/20, getY()+getWidth()*17/20);
		g.drawLine(getX()+getWidth()*17/20, getY()+getWidth()*17/20, getX()+getWidth()/2, getY()+getWidth()*3/20);
		g.drawLine(getX()+getWidth()/2, getY()+getWidth()*3/20, getX()+getWidth()*3/20, getY()+getWidth()*17/20);
		
		g.drawLine(getX()+getWidth()*3/20, getY()+getHeight()-getWidth()*17/20, getX()+getWidth()*17/20, getY()+getHeight()-getWidth()*17/20);
		g.drawLine(getX()+getWidth()*17/20, getY()+getHeight()-getWidth()*17/20, getX()+getWidth()/2, getY()+getHeight()-getWidth()*3/20);
		g.drawLine(getX()+getWidth()/2, getY()+getHeight()-getWidth()*3/20, getX()+getWidth()*3/20, getY()+getHeight()-getWidth()*17/20);
	}

	public int getPosition() {
		return position;
	}

	public int getScrollSpeed() {
		return scrollSpeed;
	}

	public int getSize() {
		return size;
	}
	
	public int getMaxSize() {
		return getHeight()-2*getWidth();
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
}
