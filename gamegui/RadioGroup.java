package gamegui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class RadioGroup extends Member
{
	private ArrayList<RadioButton> buttons;
	private RadioButton selected;
	private String text;
	private Font font;
	
	public RadioGroup(String newName, int newX, int newY, int newWidth, int newHeight, String newText, Font newFont) {
		super(newName, newX, newY, newWidth, newHeight);
		
		buttons = new ArrayList<RadioButton>();
		selected = null;
		text = newText;
		font = newFont;
	}
	
	public boolean handleEvent(MouseEvent e) {
		if(selected != null)
			selected.clear();
		
		for(int x=0; x < buttons.size(); x++)
			if(((RadioButton)buttons.get(x)).isClicked(e.getX(), e.getY())) {
				selected = buttons.get(x);
				selected.setSelected(true);
				return true;
			}
		
		return false;
	}
	
	public void draw(Graphics g)
	{
		FontMetrics metrics = g.getFontMetrics(font);
		
		g.setColor(Color.green);
		g.setFont(font);
		
		g.drawString(text, getX() - metrics.stringWidth(text) - 10, getY() + (getHeight() + metrics.getHeight())/2 - 2);
		
    	for(int x=0; x < buttons.size(); x++)
    		((RadioButton)(buttons.get(x))).draw(g);
	}
	
	public void clear()
	{	
		for(int x=0; x < buttons.size(); x++)
    		((RadioButton)(buttons.get(x))).clear();
	}
	
	public void add(RadioButton aButton) {
		buttons.add(aButton);
	}
	
	public RadioButton getButton(String aName) {
		for(int x=0; x < buttons.size(); x++)
    		if(buttons.get(x).getName().equals(aName))
    			return (RadioButton)buttons.get(x);
		
		return null;
	}
	
	public String getSelected() {
		if(selected != null)
			return selected.getName();
		else
			return "None";
	}
	
	public void setSelected(String button) {
		clear();
		
		for(int x=0; x < buttons.size(); x++)
    		if(buttons.get(x).getName().equals(button))
    			buttons.get(x).setSelected(true);
	}
}
