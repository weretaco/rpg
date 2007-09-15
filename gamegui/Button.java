package gamegui;

import java.awt.*;

public class Button extends Member
{
	private String text;
	private Font font;
	
	public Button(String newName, int newX, int newY, int newWidth, int newHeight, String newText, Font newFont)
	{
		super(newName, newX, newY, newWidth, newHeight);
		
		text = newText;
		font = newFont;
	}
	
	public void draw(Graphics g)
	{
		FontMetrics metrics = g.getFontMetrics(font);
		
		g.setColor(Color.red);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		
		g.setColor(Color.green);
		g.setFont(font);
		g.drawString(text, getX() + (getWidth() - metrics.stringWidth(text))/2, getY() + (getHeight() + metrics.getHeight())/2 - 2);
	}
	
	public boolean isClicked(int xCoord, int yCoord)
	{
		if(xCoord < getX() || getX() + getWidth() < xCoord)
			return false;
		if(yCoord < getY() || getY() + getHeight() < yCoord)
			return false;
		
			return true;
	} 
}