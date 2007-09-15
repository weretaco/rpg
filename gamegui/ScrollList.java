package gamegui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class ScrollList extends Member {
	private ArrayList<Listable> lstObjects;
	private Listable selectedItem;
	private Font font;
	private FontMetrics metrics;
	private int textStart;
	private boolean change;
	
	public ScrollList(String newName, int newX, int newY, int newWidth, int newHeight, Font font, FontMetrics metrics) {
		super(newName, newX, newY, newWidth, newHeight);
		
		lstObjects = new ArrayList<Listable>();
		selectedItem = null;
		this.font = font;
		this.metrics = metrics;
		textStart = 0;
		change = false;
	}
	
	public ArrayList<Listable> getList() {
		return lstObjects;
	}
	
	public Listable getSelected() {
		return selectedItem;
	}
	
	public boolean isChanged() {
		return change;
	}
	
	public void changeHandled() {
		change = false;
	}
	
	public void deselect() {
		selectedItem = null;
	}
	
	public void clear() {
		lstObjects = new ArrayList<Listable>();
		selectedItem = null;
		textStart = 0;
		changeHandled();
	}
	
	public boolean handleEvent(MouseEvent e) {
		if(getX() < e.getX() && e.getX() < getX()+getWidth()) {
			if(getY() < e.getY() && getY()-textStart+2 < e.getY() && e.getY() < getY()+getHeight() && e.getY() < getY()+lstObjects.size()*15-textStart+2) {
				selectedItem = lstObjects.get((e.getY()-getY()+textStart-2)/15);
				change = true;
				return true;
			}
		}
		
		if(!getScrollBar().handleEvent(e))
			return false;
		
		if(e.getY() < getY()+getScrollBar().getWidth()) {
			changeTextStart(-30);
		}else if(getY()+getHeight()-getScrollBar().getWidth() < e.getY()) {
			changeTextStart(30);
		}
		
		return true;
	}
	
	private void changeTextStart(int increment) {
		textStart += increment;
		
		if(lstObjects.size()*15+6>getHeight() && textStart >= lstObjects.size()*15+6-getHeight()) {
			textStart = lstObjects.size()*15+6-getHeight();
			getScrollBar().setPosition(getScrollBar().getMaxSize()-getScrollBar().getSize());
		}else if(textStart < 0 || lstObjects.size()*15+6<=getHeight()) {
			textStart = 0;
			getScrollBar().setPosition(0);
		}else
			getScrollBar().setPosition(textStart*getScrollBar().getMaxSize()/(lstObjects.size()*15+6));
	}
	
	public void draw(Graphics g) {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration gc = device.getDefaultConfiguration();
        
        BufferedImage source = gc.createCompatibleImage(getWidth(), getHeight());
        Graphics2D srcGraphics = source.createGraphics();
        
        srcGraphics.setColor(Color.green);
		srcGraphics.setFont(font);
		
        for(int x=0; x<lstObjects.size(); x++) {
        	if(selectedItem != null && selectedItem.equals(lstObjects.get(x))) {
        		srcGraphics.setColor(Color.blue);
        		srcGraphics.fillRect(0, x*15-textStart+3, getWidth(), 15);
        		srcGraphics.setColor(Color.green);
        	}
        	
        	lstObjects.get(x).drawListString(5, metrics.getHeight()+x*15-textStart, srcGraphics);
        }
        	
		g.drawImage(source, getX(), getY(), null);
		
		g.setColor(Color.red);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		
		if(lstObjects.size()*15+6 > getHeight())
			getScrollBar().setSize(getScrollBar().getMaxSize()*getHeight()/(lstObjects.size()*15+6));
		else
			getScrollBar().setSize(getScrollBar().getMaxSize());
		
		getScrollBar().draw(g);
	}
}
