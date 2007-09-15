
public class Point {
	private int x;
	private int xMin;
	private int y;
	private int yMin;
	
	public Point() {
		x = 0;
		y = 0;
	}

	public Point(int x, int y) {
		this.x = x;
		this.xMin = 0;
		this.y = y;
		this.yMin = 0;
	}
	
	public Point(int x, int xMin, int y, int yMin) {
		this.x = x;
		this.xMin = xMin;
		this.y = y;
		this.yMin = yMin;
	}

	public int getX() {
		return x;
	}
	
	public int getXMin() {
		return xMin;
	}

	public int getY() {
		return y;
	}
	
	public int getYMin() {
		return yMin;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setXMin(int xMin) {
		this.xMin = xMin;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setYMin(int yMin) {
		this.yMin = yMin;
	}
	
	public String toString() {
		return x+","+y;
	}
	
	public static int xDif(Point p1, Point p2) {
		return 100*(p2.x-p1.x)+p2.xMin-p1.xMin;
	}
	
	public static int yDif(Point p1, Point p2) {
		return 100*(p2.y-p1.y)+p2.yMin-p1.yMin;
	}
	
	public static double dist(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.x+p1.xMin/100-p2.x-p2.xMin/100, 2)+Math.pow(p1.y+p1.yMin/100-p2.y-p2.yMin/100, 2));
	}
}
