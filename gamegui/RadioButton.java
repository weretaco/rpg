package gamegui;

import java.awt.*;

public class RadioButton extends Member
{
	private String text;
	private Font font;
	private boolean textAfter;
	private boolean selected;
	
	public RadioButton(String newName, int newX, int newY, int newWidth, int newHeight, String newText, Font newFont, boolean isTextAfter)
	{
		super(newName, newX, newY, newWidth, newHeight);
		
		text = newText;
		font = newFont;
		textAfter = isTextAfter;
	}
	
	public void draw(Graphics g)
	{
		FontMetrics metrics = g.getFontMetrics(font);
		
		g.setColor(Color.red);
		g.drawOval(getX(), getY(), getWidth(), getHeight());
		
		if(selected)
		{
			g.setColor(Color.green);
			g.fillOval(getX() + 5, getY() + 5, getWidth() - 10, getHeight() - 10);
		}
		
		g.setColor(Color.green);
		g.setFont(font);
		
		if(textAfter)
			g.drawString(text, getX() + getWidth() + 7, getY() + (getHeight() + metrics.getHeight())/2 - 2);
		else
			g.drawString(text, getX() - metrics.stringWidth(text) - 7, getY() + (getHeight() + metrics.getHeight())/2 - 2);
	}
	
	public void clear()
	{	
		selected = false;
	}
	
	public String getLabel() {
		return text;
	}
	
	public boolean isClicked(int xCoord, int yCoord)
	{
		double distance = Math.sqrt(Math.pow(getX() + getWidth()/2 - xCoord, 2) + Math.pow(getY() +getHeight()/2 - yCoord, 2));
		
		return !(distance > getWidth() / 2);
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public void setSelected(boolean isSelected)
	{
		selected = isSelected;
	}
}