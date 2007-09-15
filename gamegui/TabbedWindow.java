package gamegui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TabbedWindow extends Member {
	private ArrayList<Window> windows;
	private ArrayList<String> windowLabels;
	private Window activeWindow;
	private int tabHeight;
	private Font tabFont;
	
	public TabbedWindow(String newName, int newX, int newY, int newWidth, int newHeight, int newTabHeight, Font newFont) {
		super(newName, newX, newY, newWidth, newHeight);
		
		windows = new ArrayList<Window>();
		windowLabels = new ArrayList<String>();
		tabHeight = newTabHeight;
		activeWindow = null;
		tabFont = newFont;
	}
	
	public void add(Window newWindow, String name) {
		newWindow.offset(getX(), getY()+tabHeight);
		
		if(activeWindow == null)
			activeWindow = newWindow;
		windows.add(newWindow);
		windowLabels.add(name);
	}
	
	public void clear() {
		activeWindow = windows.get(0);
		for(int x=0; x < windows.size(); x++)
    		windows.get(x).clear();
	}
	
	public void offset(int xOffset, int yOffset) {
		super.offset(xOffset, yOffset);
		
		for(int x=0; x < windows.size(); x++)
    		windows.get(x).offset(xOffset, yOffset);
	}
	
	public Window getWindow(String aName) {
		for(int x=0; x < windows.size(); x++)
    		if(windows.get(x).getName().equals(aName))
    			return (Window)windows.get(x);
		
		return null;
	}
	
	public boolean handleEvent(MouseEvent e) {
		for(int x=0; x < windows.size(); x++) {
    		if(isClicked(getX() + x*getWidth()/windows.size(), getY(), getWidth()/windows.size(), tabHeight, e.getX(), e.getY())) {
    			activeWindow = windows.get(x);
    			return true;
			}
		}
    			
		return activeWindow.handleEvent(e);
	}
	
	private boolean isClicked(int x, int y, int width, int height, int mouseX, int mouseY) {
		return x <= mouseX && mouseX <= x+width && y <= mouseY && mouseY <= y+height;
	}
	
	public void draw(Graphics g) {
		FontMetrics metrics = g.getFontMetrics(tabFont);
		
		g.setColor(Color.black);
    	g.fillRect(getX(), getY(), getWidth(), getHeight());
    	
		g.setFont(tabFont);
    	for(int x=0; x < windows.size(); x++)
    	{
    		g.setColor(Color.green);
    		g.drawString(windowLabels.get(x), getX()+x*getWidth()/windows.size()+(getWidth()/windows.size()-metrics.stringWidth(windowLabels.get(x)))/2, getY() + (tabHeight + metrics.getHeight())/2 - 2);
    		
    		g.setColor(Color.red);
			g.drawLine(getX()+x*getWidth()/windows.size(), getY(), getX()+x*getWidth()/windows.size(), getY()+tabHeight);
			
    		if(windows.get(x).equals(activeWindow)) {
    			((Member)(windows.get(x))).draw(g);
    			g.setColor(Color.red);
    			g.drawRect(getX(), getY(), getWidth(), getHeight());
    	    	g.drawLine(getX(), getY()+tabHeight, getX()+x*getWidth()/windows.size(), getY()+tabHeight);
    	    	g.drawLine(getX()+(x+1)*getWidth()/windows.size(), getY()+tabHeight, getX()+getWidth(), getY()+tabHeight);
    		}
    	}
	}
	
	public String getActive() {
		if(activeWindow != null)
			return activeWindow.getName();
		else
			return "";
	}
}
