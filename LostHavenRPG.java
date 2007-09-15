import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import java.text.*;

import gamegui.*;

/* 
 * This is the main class in the project. It initializes wide-screen mode and is responsible for drawing to the screen and handling
 * input.
 * 
 * The classes in the gamegui folder are similar to the Swing classes in that they help in building a gui. They are all extended from
 * the Member class and instances of each of them can be added to a Window class. They also have input-handling functionality.
 */

public class LostHavenRPG implements KeyListener, MouseListener {
	private static DisplayMode[] BEST_DISPLAY_MODES = new DisplayMode[] {
        new DisplayMode(800, 600, 32, 0),
        new DisplayMode(800, 600, 16, 0),
        new DisplayMode(800, 600, 8, 0)
    };
	
	boolean done;
	boolean changePending;
	
	Player player;
	Map map;
	
	//all "types" should be enums so it's easier to see all possible values while programming
	HashMap<LandType, Land> landMap;
	HashMap<StructureType, Structure> structMap;
	HashMap<CreatureType, Creature> creatureMap;
	
	BufferedImage girl;
	BufferedImage guy;
    
    public GameState gameState;
    public AuxState auxState;
    
    //GUI elements
    Frame frmMain;
    
    gamegui.Window wndMain;
    gamegui.Window wndCreateAccount;
    gamegui.Window wndChooseClass;
    gamegui.Window wndGameInfo;
    gamegui.Window wndCredits;
    
    RadioGroup rdgGenderSelection;
    RadioGroup rdgClassSelection;
    
    gamegui.Window wndMessage;
    gamegui.Window wndProgress;
    gamegui.Window wndConnecting;
    
    Textbox selectedText;

    public LostHavenRPG(GraphicsDevice device) {
        try {
            GraphicsConfiguration gc = device.getDefaultConfiguration();
            frmMain = new Frame(gc);
            frmMain.setUndecorated(true);
            frmMain.setIgnoreRepaint(true);
            device.setFullScreenWindow(frmMain);
            
            if (device.isDisplayChangeSupported()) {
                chooseBestDisplayMode(device);
            }
            
            frmMain.addMouseListener(this);
            frmMain.addKeyListener(this);
            frmMain.createBufferStrategy(2);
            BufferStrategy bufferStrategy = frmMain.getBufferStrategy();

            player = new Player();
            done = false;
            changePending = false;
            
            gameState = GameState.Main;
            auxState = AuxState.None;
            
            loadMap();
        	map = new Map("mapInfo.txt", "structInfo.txt", landMap, structMap);
        	map.getLoc(10, 10).addCreature(new Creature());
            initGUIElements();
            
            while (!done) {
                Graphics g = bufferStrategy.getDrawGraphics();
                move();
                render(g);
                g.dispose();
                bufferStrategy.show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            device.setFullScreenWindow(null);
        }
    }
    
    private void initGUIElements() {    	
    	Font font10 = new Font("Arial", Font.PLAIN, 10);
    	Font font11 = new Font("Arial", Font.PLAIN, 11);
    	Font font12 = new Font("Arial", Font.PLAIN, 12);
    	Font font14 = new Font("Arial", Font.PLAIN, 14);
    	Font font24 = new Font("Arial", Font.PLAIN, 24);
    	
    	wndMain = new gamegui.Window("main", 0, 0, 800, 600, true);
    	
    	Animation anmTitle = new Animation("title", 144, 0, 512, 95, 1000/12);
    	
    	try {
    		anmTitle.addFrame(ImageIO.read(getClass().getResource("images/Frame1.png")));
    		anmTitle.addFrame(ImageIO.read(getClass().getResource("images/Frame2.png")));
    		anmTitle.addFrame(ImageIO.read(getClass().getResource("images/Frame3.png")));
    		anmTitle.addFrame(ImageIO.read(getClass().getResource("images/Frame4.png")));
    		anmTitle.addFrame(ImageIO.read(getClass().getResource("images/Frame5.png")));
    	}catch(IOException ioe) {
    		ioe.printStackTrace();
    	}
    	wndMain.add(anmTitle);
        
    	wndMain.add(new gamegui.Button("new game", 500, 140, 200, 40, "New Game", font12));
    	wndMain.add(new gamegui.Button("load game", 500, 230, 200, 40, "Load Game", font12));
    	wndMain.add(new gamegui.Button("game info", 500, 320, 200, 40, "Game Information", font12));
    	wndMain.add(new gamegui.Button("credits", 500, 410, 200, 40, "Credits", font12));
    	wndMain.add(new gamegui.Button("quit", 500, 500, 200, 40, "Quit", font12));
    	
    	wndCreateAccount = new gamegui.Window("create account", 0, 0, 800, 600, true);
    	
    	rdgGenderSelection = new RadioGroup("gender selection", 400, 315, 190, 30, "Gender:", font12);
    	
    	rdgGenderSelection.add(new RadioButton("male", 438, 318, 24, 24, "Male", font11, false));
    	rdgGenderSelection.add(new RadioButton("female", 528, 318, 24, 24, "Female", font11, false));
    	
    	wndCreateAccount.add(new gamegui.Label("title", 250, 15, 300, 20, "Create an Account", font24, true));
    	wndCreateAccount.add(new Textbox("user", 400, 150, 190, 30, "Username:", font12, false));
    	wndCreateAccount.add(rdgGenderSelection);
    	wndCreateAccount.add(new gamegui.Label("show class", 330, 370, 70, 30, "None", font12, false));
    	wndCreateAccount.add(new gamegui.Button("choose class", 400, 370, 190, 30, "Choose Your Class", font12));
    	wndCreateAccount.add(new gamegui.Button("create", 245, 520, 140, 30, "Create", font12));
    	wndCreateAccount.add(new gamegui.Button("cancel", 415, 520, 140, 30, "Cancel", font12));
    
    	wndChooseClass = new gamegui.Window("choose class", 0, 0, 800, 600, true);
    	
    	rdgClassSelection = new RadioGroup("class selection", 0, 0, 0, 0, "", font12);
    	
    	rdgClassSelection.add(new RadioButton("fighter", 138, 88, 24, 24, "Fighter", font14, true));
    	rdgClassSelection.add(new RadioButton("ranger", 138, 158, 24, 24, "Ranger", font14, true));
    	rdgClassSelection.add(new RadioButton("barbarian", 138, 228, 24, 24, "Barbarian", font14, true));
    	rdgClassSelection.add(new RadioButton("sorceror", 138, 298, 24, 24, "Sorceror", font14, true));
    	rdgClassSelection.add(new RadioButton("druid", 138, 368, 24, 24, "Druid", font14, true));
    	rdgClassSelection.add(new RadioButton("wizard", 138, 438, 24, 24, "Wizard", font14, true));
    	
    	wndChooseClass.add(new gamegui.Label("title", 250, 15, 300, 20, "Choose a Character", font24, true));
    	wndChooseClass.add(rdgClassSelection);
    	wndChooseClass.add(new gamegui.Label("fighter", 170, 114, 170, 0, "A resolute and steadfast champion who has perfected the art of battle and his skill in melee weapons", font10, false));
    	wndChooseClass.add(new gamegui.Label("ranger", 170, 184, 170, 0, "A skilled combatant who sneaks up on his opponents or shoots them from afar before they know it", font10, false));
    	wndChooseClass.add(new gamegui.Label("barbarian", 170, 254, 170, 0, "A wild warrior who is unstoppable in battle and uses his own fury to strengthen his attacks", font10, false));
    	wndChooseClass.add(new gamegui.Label("sorceror", 170, 324, 170, 0, "A chaotic spellcaster who uses his charisma and force of will to power his spells", font10, false));
    	wndChooseClass.add(new gamegui.Label("druid", 170, 394, 170, 0, "A mystical enchanter who relies on the power of nature and his wisdom to work his magic", font10, false));
    	wndChooseClass.add(new gamegui.Label("wizard", 170, 464, 170, 0, "A methodical and studious character who studies his opponents to know how to best attack them", font10, false));
    	wndChooseClass.add(new gamegui.Button("select", 245, 520, 140, 30, "Select", font12));
    	wndChooseClass.add(new gamegui.Button("cancel", 415, 520, 140, 30, "Cancel", font12));
    	
    	wndMessage = new gamegui.Window("message", 290, 135, 220, 160, false);
		wndMessage.add(new gamegui.Label("label", 70, 15, 80, 12, "none", font12, true));
		wndMessage.add(new gamegui.Button("button", 70, 115, 80, 30, "OK", font12));
    }
    
    private void loadMap() {
    	landMap = new HashMap<LandType, Land>();
    	structMap = new HashMap<StructureType, Structure>();
    	BufferedImage nullImg = null;
    	
    	try {
    		girl = ImageIO.read(getClass().getResource("images/ArmoredGirl.png"));
    		guy = ImageIO.read(getClass().getResource("images/ArmoredGuy.png"));
    	}catch(IOException ioe) {
    		ioe.printStackTrace();
    	}
    		
    	landMap.put(LandType.Ocean, new Land(LandType.Ocean, "Ocean.png", false));
    	landMap.put(LandType.Grass, new Land(LandType.Grass, "Grass.png", true));
    	
    	structMap.put(StructureType.None, new Structure(StructureType.None, nullImg, true));
    	structMap.put(StructureType.BlueOrb, new Structure(StructureType.BlueOrb, "Blue Orb.png", false));
    	structMap.put(StructureType.Cave, new Structure(StructureType.Cave, "Cave.png", false));
    	structMap.put(StructureType.Gravestone, new Structure(StructureType.Gravestone, "Gravestone.png", false));
    	structMap.put(StructureType.GraveyardFence1, new Structure(StructureType.GraveyardFence1, "HorGrave.png", false));
    	structMap.put(StructureType.GraveyardFence2, new Structure(StructureType.GraveyardFence2, "VerGrave.png", false));
    	structMap.put(StructureType.PicketFence1, new Structure(StructureType.PicketFence1, "HorPalisade.png", false));
    	structMap.put(StructureType.PicketFence2, new Structure(StructureType.PicketFence2, "VerPalisade.png", false));
    	structMap.put(StructureType.Hut, new Structure(StructureType.Hut, "Hut.png", false));
    	structMap.put(StructureType.WitchHut, new Structure(StructureType.WitchHut, "Witch Hut.png", false));
    	structMap.put(StructureType.Tent, new Structure(StructureType.Tent, "Tent.png", false));
    	structMap.put(StructureType.LargeTent, new Structure(StructureType.LargeTent, "LargeTent.png", false));
    	structMap.put(StructureType.House, new Structure(StructureType.House, "House.png", false));
    	structMap.put(StructureType.Tree, new Structure(StructureType.Tree, "Trees.png", false));
    	structMap.put(StructureType.BlueOrb, new Structure(StructureType.BlueOrb, "Blue Orb.png", false));
    	structMap.put(StructureType.RedOrb, new Structure(StructureType.RedOrb, "Red Orb.png", false));
    	structMap.put(StructureType.LoginPedestal, new Structure(StructureType.LoginPedestal, "YellowPedestal.png", true));
    	structMap.put(StructureType.RejuvenationPedestal, new Structure(StructureType.RejuvenationPedestal, "PurplePedestal.png", true));
    	structMap.put(StructureType.LifePedestal, new Structure(StructureType.LifePedestal, "RedPedestal.png", true));
    	structMap.put(StructureType.ManaPedestal, new Structure(StructureType.ManaPedestal, "BluePedestal.png", true));
    }
    
    private void move() {
    	double dist = player.getSpeed()*(System.currentTimeMillis()-player.getLastMoved())/1000;
    	Point lastLoc = player.getLoc();
    	Point target = player.getTarget();
    	Point newLoc;
    	
    	player.setLastMoved(System.currentTimeMillis());
    	if(Point.dist(lastLoc, player.getTarget()) <= dist)
    		player.setLoc(player.getTarget());
    	else {
    		int xDif = (int)(Point.xDif(lastLoc, target)*dist/Point.dist(lastLoc, target));
    		int yDif = (int)(Point.yDif(lastLoc, target)*dist/Point.dist(lastLoc, target));
    		newLoc = new Point(lastLoc.getX(), lastLoc.getXMin()+xDif, lastLoc.getY(), lastLoc.getYMin()+yDif);
    		newLoc.setX(newLoc.getX()+newLoc.getXMin()/100);
    		newLoc.setXMin(newLoc.getXMin()%100);
    		newLoc.setY(newLoc.getY()+newLoc.getYMin()/100);
    		newLoc.setYMin(newLoc.getYMin()%100);
    		if(newLoc.getXMin()<0) {
    			newLoc.setX(newLoc.getX()-1);
    			newLoc.setXMin(newLoc.getXMin()+100);
    		}else if(newLoc.getYMin()<0) {
    			newLoc.setY(newLoc.getY()-1);
    			newLoc.setYMin(newLoc.getYMin()+100);
    		}
    		if(map.getLoc(newLoc.getX()/100, newLoc.getY()/100).isPassable())
    			player.setLoc(newLoc);
    		else
    			player.setTarget(player.getLoc());
    	}
    }
    
    private void render(Graphics g) {
    	g.setColor(Color.black);
        g.fillRect(0, 0, 800, 600);
        
        switch(gameState) {
		case Main:
			drawMain(g);
			break;
		case CreateAccount:
			drawCreateAccount(g);
			break;
		case CreateClass:
			drawCreateClass(g);
			break;
		case LoadGame:
			drawLoadGame(g);
			break;
		case Info:
			drawInfo(g);
			break;
		case Credits:
			drawCredits(g);
			break;
		case Game:
			calculateMapVertices();
            drawMap(g);
            drawItems(g);
            drawCreatures(g);
            drawChar(g);
            drawStatDisplay(g);
            drawChat(g);
			break;
		case GameMenu:
			calculateMapVertices();
            drawMap(g);
            drawItems(g);
            drawCreatures(g);
            drawChar(g);
            drawStatDisplay(g);
            drawChat(g);
            drawGameMenu(g);
			break;
		case GameInventory:
			calculateMapVertices();
            drawMap(g);
            drawItems(g);
            drawCreatures(g);
            drawChar(g);
            drawStatDisplay(g);
            drawChat(g);
            drawGameInventory(g);
			break;
		case GameStats:
			calculateMapVertices();
            drawMap(g);
            drawItems(g);
            drawCreatures(g);
            drawChar(g);
            drawStatDisplay(g);
            drawChat(g);
            drawGameStats(g);
			break;
		}
        
        switch(auxState) {
		case None:
			break;
		case MsgBox:
			wndMessage.draw(g);
			break;
		}
    }
    
    public static String dateString() {
		return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
	}
    
    public void showMessage(String text) {
    	auxState = AuxState.MsgBox;
    	((gamegui.Label)wndMessage.getMember("label")).setText(text);
    }
    
    private void calculateMapVertices() {
    
    }
    
    private void drawMain(Graphics g) {
    	wndMain.draw(g);
    	
    	g.setColor(Color.red);
		g.drawRect(10, 100, 380, 490);
		g.drawRect(410, 100, 380, 490);
    }
    
    private void drawCreateAccount(Graphics g) {    	
    	wndCreateAccount.draw(g);
    }
    
    private void drawCreateClass(Graphics g) {
    	wndChooseClass.draw(g);
    }
    
    private void drawLoadGame(Graphics g) {
    	Font tempFont = new Font("Arial", Font.PLAIN, 12);
    	FontMetrics metrics = g.getFontMetrics(tempFont);
    	
    	g.setFont(tempFont);
    	g.setColor(Color.green);
    	g.drawString("There is not a whole lot here right now. You can click anywhere on the screen to get back to the main menu.", 0, metrics.getHeight());
    }
    
    private void drawInfo(Graphics g) {
    	Font tempFont = new Font("Arial", Font.PLAIN, 12);
    	FontMetrics metrics = g.getFontMetrics(tempFont);
    	
    	g.setFont(tempFont);
    	g.setColor(Color.green);
    	g.drawString("There is not a whole lot here right now. You can click anywhere on the screen to get back to the main menu.", 0, metrics.getHeight());
    }
    
    private void drawCredits(Graphics g) {
    	Font tempFont = new Font("Arial", Font.PLAIN, 12);
    	FontMetrics metrics = g.getFontMetrics(tempFont);
    	
    	g.setFont(tempFont);
    	g.setColor(Color.green);
    	g.drawString("There is not a whole lot here right now. You can click anywhere on the screen to get back to the main menu.", 0, metrics.getHeight());
    }
    
    private void drawMap(Graphics g) {
    	int locX = player.getLoc().getX();
    	int locY = player.getLoc().getY();
    	int xLow = locX/100-4;
    	int xHigh = xLow+9;
    	int yLow = locY/100-3;
    	int yHigh = yLow+7;
    	
    	if(xLow<0)
    		xLow = 0;
    	if(xHigh>=map.getLength())
    		xHigh = map.getLength()-1;
    	if(yLow<0)
    		yLow = 0;
    	if(yHigh>=map.getHeight())
    		yHigh = map.getHeight()-1;

		for(int x=xLow; x<xHigh; x++) {
			for(int y=yLow; y<yHigh; y++) {
				g.drawImage(map.getLoc(x, y).getLand().getImg(), 400+x*100-locX, 300+y*100-locY, null);				
				g.drawImage(map.getLoc(x, y).getStruct().getImg(), 400+x*100-locX, 300+y*100-locY, null);
			}
		}
    }
    
    private void drawItems(Graphics g) {
    
    }
    
    private void drawCreatures(Graphics g) {
    
    }
    
    private void drawChar(Graphics g) {
    	switch(player.getGender()) {
		case Female:
			g.drawImage(girl, 375, 200, null);
			break;
		case Male:
			g.drawImage(guy, 375, 200, null);
			break;
		}
    }
    
    private void drawStatDisplay(Graphics g) {
    
    }
    
    private void drawChat(Graphics g) {
    
    }
    
    private void drawGameMenu(Graphics g) {
    
    }
    
    private void drawGameInventory(Graphics g) {
    
    }
    
    private void drawGameStats(Graphics g) {
    
    }
    
    private void selectText(Textbox text) {
    	if(selectedText != null)
			selectedText.setSelected(false);
		selectedText = text;
		
		if(text != null)
			text.setSelected(true);
    }
    
    private static DisplayMode getBestDisplayMode(GraphicsDevice device) {
        for (int x = 0; x < BEST_DISPLAY_MODES.length; x++) {
            DisplayMode[] modes = device.getDisplayModes();
            for (int i = 0; i < modes.length; i++) {
                if (modes[i].getWidth() == BEST_DISPLAY_MODES[x].getWidth()
                   && modes[i].getHeight() == BEST_DISPLAY_MODES[x].getHeight()
                   && modes[i].getBitDepth() == BEST_DISPLAY_MODES[x].getBitDepth()) 
                {
                    return BEST_DISPLAY_MODES[x];
                }
            }
        }
        return null;
    }
    
    public static void chooseBestDisplayMode(GraphicsDevice device) {
        DisplayMode best = getBestDisplayMode(device);
        if (best != null) {
            device.setDisplayMode(best);
        }
    }
    
    public void mousePressed(MouseEvent e) {
    	switch(auxState) {
		case None:
			switch(gameState) {
			case Main:				
				if(wndMain.getMember("new game").isClicked(e.getX(),e.getY()))
					gameState = GameState.CreateAccount;
				else if(wndMain.getMember("load game").isClicked(e.getX(),e.getY()))
					gameState = GameState.LoadGame;
				else if(wndMain.getMember("game info").isClicked(e.getX(),e.getY()))
					gameState = GameState.Info;
				else if(wndMain.getMember("credits").isClicked(e.getX(),e.getY()))
					gameState = GameState.Credits;
				else if(wndMain.getMember("quit").isClicked(e.getX(),e.getY()))
					done = true;
				break;
			case CreateAccount:
				if(wndCreateAccount.getMember("user").isClicked(e.getX(),e.getY()))
					selectText((Textbox)wndCreateAccount.getMember("user"));
				else if(wndCreateAccount.getMember("choose class").isClicked(e.getX(),e.getY())) {
					selectText(null);
					gameState = GameState.CreateClass;
				}else if(wndCreateAccount.getMember("create").isClicked(e.getX(),e.getY())) {
					String user = ((Textbox)wndCreateAccount.getMember("user")).getText();
					Gender gender = Gender.valueOf(rdgGenderSelection.getButton(rdgGenderSelection.getSelected()).getLabel());
					
					if(user.equals("")) {
						showMessage("The username is empty");
					}else if(gender == Gender.None) {
						showMessage("No gender has been selected");
					}else{
						player = new Player(user, gender);
						player.setSpeed(200);
						player.setLoc(new Point(750, 860));
						player.setTarget(player.getLoc());
						gameState = GameState.Game;
					}
				}else if(wndCreateAccount.getMember("cancel").isClicked(e.getX(),e.getY())) {
					selectText(null);
					wndCreateAccount.clear();
					wndChooseClass.clear();
					gameState = GameState.Main;
				}else if(wndCreateAccount.handleEvent(e)) {
				}
				break;
			case CreateClass:
				if(wndChooseClass.getMember("select").isClicked(e.getX(),e.getY())) {
					gameState = GameState.CreateAccount;
				}
				else if(wndChooseClass.getMember("cancel").isClicked(e.getX(),e.getY())) {
					gameState = GameState.CreateAccount;
				}else if(wndChooseClass.handleEvent(e)) {
				}
				break;
			case LoadGame:
				gameState = GameState.Main;
				break;
			case Info:
				gameState = GameState.Main;
				break;
			case Credits:
				gameState = GameState.Main;
				break;
			case Game:
				int newX = player.getLoc().getX()+e.getX()-400;
				int newY = player.getLoc().getY()+e.getY()-300;
				if(map.getLoc((int)(Math.floor(newX/100)), (int)(Math.floor(newY/100))).isPassable()) {
					player.setTarget(new Point(newX, newY));
					player.setLastMoved(System.currentTimeMillis());
				}
				break;
			case GameMenu:
				break;
			case GameInventory:
				break;
			case GameStats:
				break;
			}
			break;
		case MsgBox:
			if(wndMessage.getMember("button").isClicked(e.getX(), e.getY())) {
				auxState = AuxState.None;
			}
			break;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {

	}
	
	public void mouseExited(MouseEvent e) {
	    
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
 
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		if(selectedText != null)
			selectedText.handleEvent(e);
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) 
			if(gameState == GameState.Game)
				gameState = GameState.Main;
			else
				done = true;
	}
	
	public void keyReleased(KeyEvent e) {
	
	}
    
	public static void main(String[] args) {
		try {
			PrintStream st = new PrintStream(new FileOutputStream("err.txt", true));
			System.setErr(st);
			System.setOut(st);
			System.out.println("-----[ Session started on " + dateString() + " ]-----");
			
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice device = env.getDefaultScreenDevice();
			new LostHavenRPG(device);
        }
		catch (Exception e) {
			e.printStackTrace();
        }
		System.exit(0);
	}
}
