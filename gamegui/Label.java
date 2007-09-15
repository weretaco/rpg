package gamegui;

import java.awt.*;

public class Label extends Member
{
	private String text;
	private Font font;
	private boolean centered;
	private boolean fixed;
	
	public Label(String newName, int newX, int newY, int newWidth, int newHeight, String newText, Font newFont, boolean centered) {
		super(newName, newX, newY, newWidth, newHeight);
		
		text = newText;
		font = newFont;
		this.centered = centered;
		fixed = false;
	}
	
	public Label(String newName, int newX, int newY, int newWidth, int newHeight, String newText, Font newFont, boolean centered, boolean fixed) {
		super(newName, newX, newY, newWidth, newHeight);
		
		text = newText;
		font = newFont;
		this.centered = centered;
		this.fixed = fixed;
	}
	
	public void setText(String s) {
		text = s;
	}
	
	public void draw(Graphics g) {
		FontMetrics metrics = g.getFontMetrics(font);
		
		g.setColor(Color.green);
		g.setFont(font);
		
		if(centered)
			g.drawString(text, getX() + (getWidth() - metrics.stringWidth(text))/2, getY() + (getHeight() + metrics.getHeight())/2 - 2);
		else if(fixed)
			g.drawString(text, getX(), getY());
		else
			g.drawString(text, getX(), getY() + (getHeight() + metrics.getHeight())/2 - 2);
	}
}
