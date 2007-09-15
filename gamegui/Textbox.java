package gamegui;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

public class Textbox extends Member
{
	private String label;
	private String text;
	private Font font;
	private int textStart;
	private boolean selected;
	private int cursorState;
	private int blinkInterval;
	private long lastCursorChange;
	private boolean password;
	
	public Textbox(String newName, int newX, int newY, int newWidth, int newHeight, String newLabel, Font newFont, boolean isPass) {
		super(newName, newX, newY, newWidth, newHeight);
		
		label = new String(newLabel);
		text = new String();
		font = newFont;
		textStart = 0;
		selected = false;
		cursorState = 0;
		blinkInterval = 1000/2;
		password = isPass;
	}
	
	public void handleEvent(KeyEvent e) {
		if(32 <= e.getKeyCode() && e.getKeyCode() <= 127)
		{
			if(e.getKeyCode() == 127)
			{
				if(text.length() > 0)
					text = text.substring(0, text.length() - 1);
			}
			else
				text = text + Character.toString(e.getKeyChar());

		}
		else if(e.getKeyCode() == 8)
		{
			if(text.length() > 0)
				text = text.substring(0, text.length() - 1);
		}
	}
	
	public void draw(Graphics g) {
		String drawnString = new String();
		FontMetrics metrics = g.getFontMetrics(font);
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration gc = device.getDefaultConfiguration();
        
        BufferedImage source = gc.createCompatibleImage(getWidth(), getHeight());
        Graphics2D srcGraphics = source.createGraphics();
        
        if(selected && System.currentTimeMillis() - lastCursorChange > blinkInterval)
		{
			if(cursorState == 0)
				cursorState = 1;
			else
				cursorState = 0;
			
			lastCursorChange = System.currentTimeMillis();
		}
		
        if(password)
        {
        	for(int x=0;x<text.length();x++)
				drawnString+="*";
        }
        else
        	drawnString = text;
        	
    	if(metrics.stringWidth(drawnString) + 9 > getWidth())
    		textStart = metrics.stringWidth(drawnString)+9-getWidth();
    	else
    		textStart = 0;
    	
    	g.setColor(Color.green);
		g.setFont(font);
		srcGraphics.setColor(Color.green);
		srcGraphics.setFont(font);

		srcGraphics.drawString(drawnString, 5-textStart, (getHeight() + metrics.getHeight())/2 - 2);
		
		g.drawImage(source, getX(), getY(), null);
		g.drawString(label, getX() - metrics.stringWidth(label) - 10, getY() + (getHeight() + metrics.getHeight())/2 - 2);
		
		if(selected && cursorState == 1)
			g.drawLine(getX() + metrics.stringWidth(drawnString) - textStart + 6, getY() + 5, getX() + metrics.stringWidth(drawnString) - textStart + 6, getY() + getHeight() - 5);
		
		g.setColor(Color.red);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
	}
	
	public boolean isClicked(int xCoord, int yCoord) {
		if(xCoord < getX() || getX() + getWidth() < xCoord)
			return false;
		if(yCoord < getY() || getY() + getHeight() < yCoord)
			return false;
		
			return true;
	}
	
	public void clear() {
		text = "";
		textStart = 0;
		selected = false;
	}

	public int getBlinkInterval() {
		return blinkInterval;
	}

	public int getCursorState() {
		return cursorState;
	}

	public Font getFont() {
		return font;
	}

	public String getLabel() {
		return label;
	}

	public long getLastCursorChange() {
		return lastCursorChange;
	}

	public boolean isSelected() {
		return selected;
	}

	public String getText() {
		return text;
	}

	public int getTextStart() {
		return textStart;
	}
	
	public void setText(String newText) {
		text = newText;
	}
	
	public void setSelected(boolean isSelected) {
		selected = isSelected;
	}

	public void setTextStart(int textStart) {
		this.textStart = textStart;
	}
	
	public void setCursorState(int cursorState) {
		this.cursorState = cursorState;
	}

	public void setLastCursorChange(long lastCursorChange) {
		this.lastCursorChange = lastCursorChange;
	}
}
