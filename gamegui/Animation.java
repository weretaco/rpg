package gamegui;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Animation extends Member
{
	ArrayList<BufferedImage> frames;
	int currentFrame;
	int drawInterval;
	long lastFrameChange;
	
	public Animation(String newName, int newX, int newY, int newWidth, int newHeight, int newInterval)
	{
		super(newName, newX, newY, newWidth, newHeight);
		
		frames = new ArrayList<BufferedImage>();
		currentFrame = 0;
		drawInterval = newInterval;
		lastFrameChange = 0;
	}
	
	public void addFrame(BufferedImage newFrame)
	{
		frames.add(newFrame);
	}
	
	public void draw(Graphics g)
	{
		if(System.currentTimeMillis() - lastFrameChange > drawInterval)
		{
			currentFrame++;
			if(currentFrame >= frames.size())
				currentFrame = 0;
			
			lastFrameChange = System.currentTimeMillis();
		}
		
    	g.drawImage(frames.get(currentFrame), getX(), getY(), null);
	}
}
