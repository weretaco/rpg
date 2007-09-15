package gamegui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

public class MultiTextbox extends Textbox {
	ArrayList<String> lstStrings;
	FontMetrics metrics;
	boolean active;
	
	public MultiTextbox(String newName, int newX, int newY, int newWidth, int newHeight, String newLabel, boolean isActive, Font newFont, FontMetrics newMetrics) {
		super(newName, newX, newY, newWidth, newHeight, newLabel, newFont, false);
	
		lstStrings = new ArrayList<String>();
		metrics = newMetrics;
		active = isActive;
		
		splitString();
	}
	
	public void append(String str) {
		if(getText().equals(""))
			setText(str);
		else
			setText(getText() + "\n" + str);
		
		splitString();
		
		if(lstStrings.size()*15+6 > getHeight())
			getScrollBar().setSize(getScrollBar().getMaxSize()*getHeight()/(lstStrings.size()*15+6));
		else
			getScrollBar().setSize(getScrollBar().getMaxSize());
	
		getScrollBar().setPosition(getScrollBar().getMaxSize()-getScrollBar().getSize());
	}
	
	public void clear() {
		super.clear();
		lstStrings = new ArrayList<String>();
	}

	public boolean handleEvent(MouseEvent e) {
		if(!getScrollBar().handleEvent(e))
			return false;
		
		if(e.getY() < getY()+getWidth()) {
			changeTextStart(-30);
		}else if(getY()+getHeight()-getWidth() < e.getY()) {
			changeTextStart(30);
		}
		
		return true;
	}
	
	public void handleEvent(KeyEvent e) {
		if(!active)
			return;
		
		super.handleEvent(e);
		
		splitString();
		
		if(lstStrings.size()*15+6 > getHeight())
			getScrollBar().setSize(getScrollBar().getMaxSize()*getHeight()/(lstStrings.size()*15+6));
		else
			getScrollBar().setSize(getScrollBar().getMaxSize());
	
		getScrollBar().setPosition(getScrollBar().getMaxSize()-getScrollBar().getSize());
	}
	
	private void changeTextStart(int increment) {
		setTextStart(getTextStart()+increment);
		
		if(lstStrings.size()*15+6>getHeight() && getTextStart() >= lstStrings.size()*15+6-getHeight()) {
			setTextStart(lstStrings.size()*15+6-getHeight());
			getScrollBar().setPosition(getScrollBar().getMaxSize()-getScrollBar().getSize());
		}else if(getTextStart() < 0 || lstStrings.size()*15+6<=getHeight()) {
			setTextStart(0);
			getScrollBar().setPosition(0);
		}else
			getScrollBar().setPosition(getTextStart()*getScrollBar().getMaxSize()/(lstStrings.size()*15+6));
	}

	private void splitString() {
		String drawnString = getText();
		
		ArrayList<String> lstTemp = new ArrayList<String>();
        do {
        	int x = 0;
        	while(x<drawnString.length() && metrics.stringWidth(drawnString.substring(0, x+1))<=getWidth()-10 && !drawnString.substring(x, x+1).equals("\n")) {
        		x++;
        	}
        	
        	lstTemp.add(drawnString.substring(0, x));
        	
        	if(drawnString.length()>x && drawnString.substring(x, x+1).equals("\n"))
        		drawnString = drawnString.substring(x+1);
        	else
        		drawnString = drawnString.substring(x);
        }while(metrics.stringWidth(drawnString)>0);
		
        if(lstTemp.size()*15-getHeight()+6 > 0)
    		setTextStart(lstTemp.size()*15-getHeight()+6);
    	else
    		setTextStart(0);
        
        lstStrings = lstTemp;
	}
	
	public void draw(Graphics g) {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration gc = device.getDefaultConfiguration();
        
        BufferedImage source = gc.createCompatibleImage(getWidth(), getHeight());
        Graphics2D srcGraphics = source.createGraphics();
        
        if(isSelected() && System.currentTimeMillis() - getLastCursorChange() > getBlinkInterval())
		{
			if(getCursorState() == 0)
				setCursorState(1);
			else
				setCursorState(0);
			
			setLastCursorChange(System.currentTimeMillis());
		}
        
        srcGraphics.setColor(Color.green);
		srcGraphics.setFont(getFont());
        
        int x;
        for(x=0; x<lstStrings.size(); x++)
        	srcGraphics.drawString(lstStrings.get(x), 5, metrics.getHeight()+x*15-getTextStart());

        x--;
        if(isSelected() && getCursorState() == 1)
			srcGraphics.drawLine(metrics.stringWidth(lstStrings.get(x))+6, 5+x*15-getTextStart(), metrics.stringWidth(lstStrings.get(x))+6, metrics.getHeight()+x*15-getTextStart());
        
		g.setColor(Color.green);
		g.setFont(getFont());
		
		g.drawImage(source, getX(), getY(), null);
		g.drawString(getLabel(), getX() - metrics.stringWidth(getLabel()) - 10, getY() + (getHeight() + metrics.getHeight())/2 - 2);
		
		g.setColor(Color.red);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		
		getScrollBar().draw(g);
	}
}
