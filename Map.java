import java.io.*;
import java.util.*;

public class Map {
	private Location[][] grid;
	
	public Map(int x, int y) {
		grid = new Location[x][y];
	}
	
	public Map(String landFile, String structFile, HashMap<LandType, Land> landMap, HashMap<StructureType, Structure> structMap) {
		try {
			int length, height, x, y;
			String str, loc;
			BufferedReader in = new BufferedReader(new FileReader(landFile));
			
			str = in.readLine();
			length = Integer.parseInt(str.substring(0, str.indexOf("x")));
			height = Integer.parseInt(str.substring(str.indexOf("x")+1));
			grid = new Location[length][height];
			
			for(x=0; x<height; x++) {
				str = in.readLine();
				for(y=0; y<length; y++) {
					if(str.indexOf(",") == -1)
						loc = str;
					else {
						loc = str.substring(0, str.indexOf(","));
						str = str.substring(str.indexOf(",")+1);
					}
					
					if(loc.equals("o")) {
						loc = "Ocean";
					}else if(loc.equals("1")) {
						loc = "Grass";
					}
					
					grid[y][x] = new Location(landMap.get(LandType.valueOf(loc)), structMap.get(StructureType.None));
				}
			}
			
			in.close();
			
			in = new BufferedReader(new FileReader(structFile));
			
			while((loc = in.readLine()) != null) {
				str = in.readLine();
				x = Integer.valueOf(str.substring(0, str.indexOf(",")));
				y = Integer.valueOf(str.substring(str.indexOf(",")+1));
				
				grid[x-1][y-1].setStruct(structMap.get(StructureType.valueOf(loc)));
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public Location getLoc(int x, int y) {
		return grid[x][y];
	}
	
	public void setLoc(Location loc, int x, int y) {
		grid[x][y] = loc;
	}
	
	public int getLength() {
		return grid.length;
	}
	
	public int getHeight() {
		if(grid.length>0)
			return grid[0].length;
		else
			return 0;
	}
}
