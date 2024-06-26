/* ID: 212051007
 *
 */

package Ex3;

import java.awt.Color;

/**
 * This class is a simple "inter-layer" connecting (aka simplifing) the
 * StdDraw_Ex3 with the Map2D interface.
 * Written for 101 java course it uses simple static functions to allow a 
 * "Singleton-like" implementation.
 * You should change this class!
 *
 */
public class Ex3 {
	private static  Map2D _map = null;
	private static Color _color = Color.blue;
	private static String _mode = "";
	private static Point2D p2 = null;
	public static final int WHITE = Color.WHITE.getRGB();
	public static final int BLUE = Color.BLUE.getRGB();
	public static final int BLACK = Color.BLACK.getRGB();
	public static final int YELLOW = Color.YELLOW.getRGB();
	public static final int GREEN = Color.GREEN.getRGB();
	public static final int RED = Color.RED.getRGB();
	

	public static void main(String[] args) {
		int dim = 10;  // init matrix (map) 10*10
		init(dim);
	}
	private static void init(int x) {
		StdDraw_Ex3.clear();
		_map = new MyMap2D(x);
		StdDraw_Ex3.setScale(-0.5, _map.getHeight()-0.5);
		StdDraw_Ex3.enableDoubleBuffering();
		_map.fill(WHITE);
		drawArray(_map);		
	}
	
	 public static void drawGrid(Map2D map) {
		 int w = map.getWidth();
		 int h = map.getHeight();
		 for(int i=0;i<w;i++) {
			 StdDraw_Ex3.line(i, 0, i, h);
		 }
		 for(int i=0;i<h;i++) {
			 StdDraw_Ex3.line(0, i, w, i);
		 }
	}
	static public void drawArray(Map2D a) {
		StdDraw_Ex3.clear();
		StdDraw_Ex3.setPenColor(Color.gray);
		drawGrid(_map);
		for(int y=0;y<a.getWidth();y++) {
			for(int x=0;x<a.getHeight();x++) {
				int c = a.getPixel(x, y);
				StdDraw_Ex3.setPenColor(new Color(c));
				drawPixel(x,y);
			}
		}		
		StdDraw_Ex3.show();
	}
	public static void actionPerformed(String p) {
		_mode = p;
		if(p.equals("Blue")) {_color = Color.BLUE; } //Defining all the colors.
		if(p.equals("White")) {_color = Color.WHITE; }
		if(p.equals("Black")) {_color = Color.BLACK; }
		if(p.equals("Red")) {_color = Color.RED; }
		if(p.equals("Yellow")) {_color = Color.YELLOW; }
		if(p.equals("Green")) {_color = Color.GREEN; }
		
		if(p.equals("20x20")) {init(20);} 	//Defining the page size.
		if(p.equals("40x40")) {init(40);}
		if(p.equals("80x80")) {init(80);}
		if(p.equals("160x160")) {init(160);}

		drawArray(_map);
		
	}
	public static void mouseClicked(Point2D p) {
		System.out.println(p);
		System.out.println(p2);
		int col = _color.getRGB();
		
		if(_mode.equals("Point")) {
			_map.setPixel(p,col); //Drawing the point.
		}
		
		if(_mode.equals("Segment")) {
			if(p2 == null) { //Getting the second point.
				p2 = p; //Defining p2.
			}
			else {
				_map.drawSegment(p2,p,col); //Making the drawing.
				p2 = null; //Reset the point.
			}
		}

		if(_mode.equals("Rect")) {
			if(p2 == null) { //Getting the second point.
				p2 = p; //Defining p2.
			}
			else {
				_map.drawRect(p2,p,col); //Making the drawing.
				p2 = null; //Reset the point.
			}
		}
		
		if(_mode.equals("Circle")) {
			if(p2 == null) { //Getting the second point.
				p2 = p; //Defining p2.
			}
			else {
				double rad = p2.distance(p); //Defining the radius as the distance between the two points.
				_map.drawCircle(p2,rad,col); //Making the drawing.
				p2 = null; //Reset the point.
				_mode = "none";
			}
		}
		
		if(_mode.equals("Fill")) {
			_map.fill(p,col); //Play fill.
			_mode = "none";
		}
		if(_mode.equals("Fill")) {
		 	int x = p.ix(); //Defining 
		    int y = p.iy();
			_map.fill(x,y,col); //Making the drawing by x and y - and not by a point.
		    p2 = null; //Reset the point.
			_mode = "none";
			}
		
		if(_mode.equals("ShortestPath")) {
			if(p2 == null) { //Getting the second point.
				p2 = p; //Defining p2.
			}
			else {
				Point2D [] ans = _map.shortestPath(p2,p); //Get the array from shortestPath.
				for(int i=0; i<ans.length; i++) { //Running all the array and drawing the relevant points.
					_map.setPixel(ans[i], col);
				}
				p2 = null;
				_mode = "none";
			}
		}

		
		if(_mode.equals("Gol")) {
			_map.nextGenGol(); //Play gol.	
		}
		
		drawArray(_map);
	}
	static private void drawPixel(int x, int y) {
		StdDraw_Ex3.filledCircle(x, y, 0.3);
	}
}
