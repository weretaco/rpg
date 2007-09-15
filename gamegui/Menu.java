package gamegui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Menu extends Member {
	private ArrayList<String> items;
	private int selectedIndex;
	private String label;
	private Font font;
	private boolean open;	//determines if the menu is pulled down
	private FontMetrics metrics;
	
	public Menu(String newName, int newX, int newY, int newWidth, int newHeight, String newLabel, Font newFont) {
		super(newName, newX, newY, newWidth, newHeight);
		
		items = new ArrayList<String>();
		selectedIndex = -1;
		label = newLabel;
		font = newFont;
		open = false;
	}
	
	public boolean handleEvent(MouseEvent e) {
		if(getX()+metrics.stringWidth(label)+4 <= e.getX() && e.getX() <= getX()+getWidth() && getY()+getHeight() <= e.getY() && e.getY() <= getY()+getHeight()+15*items.size() && open) {
			selectedIndex = (e.getY()-getY()-getHeight())/15;
			open = false;
			return true;
		}
		
		if(getX()+getWidth()-getHeight() <= e.getX() && e.getX() <= getX()+getWidth() && getY() <= e.getY() && e.getY() <= getY()+getHeight()) {
			open = !open;
			return true;
		}
	
		return false;
	}
	
	public void clear() {
		if(selectedIndex != -1)
			selectedIndex = 0;
	}
	
	public void draw(Graphics g)
	{
		metrics = g.getFontMetrics(font);
		
		g.setColor(Color.black);
    	g.fillRect(getX(), getY(), getWidth(), getHeight());
    	
    	g.setColor(Color.red);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		
		g.drawLine(getX()+metrics.stringWidth(label)+4, getY(), getX()+metrics.stringWidth(label)+4, getY()+getHeight());
		g.drawLine(getX()+getWidth()-getHeight(), getY(), getX()+getWidth()-getHeight(), getY()+getHeight());
    	
		g.drawLine(getX()+getWidth()-getHeight()*17/20, getY()+getHeight()*3/20, getX()+getWidth()-getHeight()*3/20, getY()+getHeight()*3/20);
		g.drawLine(getX()+getWidth()-getHeight()*17/20, getY()+getHeight()*3/20, getX()+getWidth()-getHeight()/2, getY()+getHeight()*17/20);
		g.drawLine(getX()+getWidth()-getHeight()/2, getY()+getHeight()*17/20, getX()+getWidth()-getHeight()*3/20, getY()+getHeight()*3/20);
		
		g.setColor(Color.green);
		g.setFont(font);
		g.drawString(label, getX()+2, getY()+(getHeight()+metrics.getHeight())/2-2);
		g.drawString(items.get(selectedIndex), getX()+metrics.stringWidth(label)+8, getY()+(getHeight()+metrics.getHeight())/2-2);
		
		if(open) {
			g.setColor(Color.black);
	    	g.fillRect(getX()+metrics.stringWidth(label)+4, getY()+getHeight(), getWidth()-metrics.stringWidth(label)-4, items.size()*15);
	    	
	    	g.setColor(Color.red);
			g.drawRect(getX()+metrics.stringWidth(label)+4, getY()+getHeight(), getWidth()-metrics.stringWidth(label)-4, items.size()*15);
	    	
	    	if(selectedIndex != -1) {
				g.setColor(Color.blue);
		    	g.fillRect(getX()+metrics.stringWidth(label)+5, getY()+getHeight()+1+15*selectedIndex, getWidth()-metrics.stringWidth(label)-5, 14);
			}
			
	    	g.setColor(Color.green);
			for(int x=0; x<items.size(); x++)
				g.drawString(items.get(x), getX()+metrics.stringWidth(label)+8, getY()+(getHeight()+metrics.getHeight())/2+15*(x+1));
		}
	}
	
	public void add(String newString) {
		selectedIndex = 0;
		items.add(newString);
	}
	
	public String getSelected() {
		return items.get(selectedIndex);
	}
}
