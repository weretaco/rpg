package gamegui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;

public class Window extends Member
{
	ArrayList<Member> members;
	boolean fullscreen;
	
	public Window(String newName, int newX, int newY, int newWidth, int newHeight) {
		super(newName, newX, newY, newWidth, newHeight);
		
		members = new ArrayList<Member>();
	}
	
	public Window(String newName, int newX, int newY, int newWidth, int newHeight, boolean full) {
		super(newName, newX, newY, newWidth, newHeight);
		
		members = new ArrayList<Member>();
		fullscreen = full;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.black);
    	g.fillRect(getX(), getY(), getWidth(), getHeight());
    	
    	if(!fullscreen)
    	{
    		g.setColor(Color.red);
    		g.drawRect(getX(), getY(), getWidth(), getHeight());
    	}
    	
    	for(int x=0; x < members.size(); x++)
    		members.get(x).draw(g);
	}
	
	public boolean handleEvent(MouseEvent e) {
		boolean val = false;
		
		for(int x=0; x < members.size(); x++)
    		val = val || members.get(x).handleEvent(e);
		
		return val;
	}
	
	public void clear() {
		for(int x=0; x < members.size(); x++)
    		members.get(x).clear();
	}
	
	public void add(Member aMember) {
		aMember.offset(getX(), getY());
		
		members.add(aMember);
	}
	
	public void offset(int xOffset, int yOffset) {
		super.offset(xOffset, yOffset);
		
		for(int x=0; x < members.size(); x++)
			members.get(x).offset(xOffset, yOffset);
	}
	
	public Member getMember(String aName) {
		for(int x=0; x < members.size(); x++)
    		if(members.get(x).getName().equals(aName))
    			return (Member)members.get(x);
		
		return null;
	}
}
