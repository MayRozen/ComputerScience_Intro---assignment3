/* ID: 212051007
 *
 */
 package Ex3;
 
import java.util.ArrayList;

/**
 * This class implements the Map2D interface. You should change (implement) this
 * class as part of Ex3.
 */
public class MyMap2D implements Map2D {
	private int[][] _map;

	public MyMap2D(int w, int h) {
		init(w, h);
	}

	public MyMap2D(int size) {
		this(size, size);
	}

	public MyMap2D(int[][] data) {
		this(data.length, data[0].length);
		init(data);
	}

	@Override
	public void init(int w, int h) {
		_map = new int[w][h];

	}

	@Override
	public void init(int[][] arr) {
		init(arr.length, arr[0].length);
		for (int x = 0; x < this.getWidth() && x < arr.length; x++) {
			for (int y = 0; y < this.getHeight() && y < arr[0].length; y++) {
				this.setPixel(x, y, arr[x][y]);
			}
		}
	}

	@Override
	public int getWidth() {
		return _map.length;
	}

	@Override
	public int getHeight() {
		return _map[0].length;
	}

	@Override
	public int getPixel(int x, int y) {
		return _map[x][y];
	}

	@Override
	public int getPixel(Point2D p) {
		return this.getPixel(p.ix(), p.iy());
	}

	public void setPixel(int x, int y, int v) {
		_map[x][y] = v;
	}

	public void setPixel(Point2D p, int v) {
		setPixel(p.ix(), p.iy(), v);
	}

	@Override
	public void drawSegment(Point2D p1, Point2D p2, int v) {
		setPixel(p1, v);
		setPixel(p2, v);
		Point2D p3;

		if (p1.ix() < p2.ix()) {
			if (p1.iy() < p2.iy()) { // p1 is higher and left.
				p3 = new Point2D(p1.ix() + 1, p1.iy() + 1); // Move towards p2.
				setPixel(p3, v);
				drawSegment(p3, p2, v);
			} else {
				if (p1.iy() == p2.iy()) { // p1 and p2 are at the same high and p1 is left.
					p3 = new Point2D(p1.ix() + 1, p1.iy()); // Move towards p2.
					setPixel(p3, v);
					drawSegment(p3, p2, v);
				} else { // p1.iy()>p2.iy(), p1 higher but at the right side.
					p3 = new Point2D(p1.ix() + 1, p1.iy() - 1); // Move towards p2.
					setPixel(p3, v);
					drawSegment(p3, p2, v);
				}
			}
		} else {
			if (p1.ix() > p2.ix()) {
				if (p1.iy() > p2.iy()) { // p2 is higher and left.
					p3 = new Point2D(p2.ix() + 1, p2.iy() + 1); // Move towards p1.
					setPixel(p3, v);
					drawSegment(p1, p3, v);
				} else {
					if (p1.iy() == p2.iy()) {
						p3 = new Point2D(p2.ix() + 1, p2.iy()); // Move towards p1.
						setPixel(p3, v);
						drawSegment(p1, p3, v);
					} else {// p1.iy()<p2.iy(), p1 higher but at the right side.
						p3 = new Point2D(p2.ix() + 1, p2.iy() - 1); // Move towards p1.
						setPixel(p3, v);
						drawSegment(p1, p3, v);
					}
				}
			}
			if (p1.ix() == p2.ix()) {
				if (p1.iy() < p2.iy()) { // p1 is over p2.
					p3 = new Point2D(p1.ix(), p1.iy() + 1); // Move towards p2.
					setPixel(p3, v);
					drawSegment(p3, p2, v);
				} else {
					if (p1.iy() > p2.iy()) {// p1 is under p2.
						p3 = new Point2D(p2.ix(), p2.iy() + 1); // Move towards p1.
						setPixel(p3, v);
						drawSegment(p1, p3, v);
					}
				}
			}
		}

	}

	@Override
	public void drawRect(Point2D p1, Point2D p2, int col) {
		int x1 = p1.ix(), x2 = p2.ix();
		int y1 = p1.iy(), y2 = p2.iy();
		int xmax = Math.max(x1, x2);
		int xmin = Math.min(x1, x2);
		int ymax = Math.max(y1, y2);
		int ymin = Math.min(y1, y2);
		for (int i = xmin; i <= xmax; i++) {
			for (int j = ymin; j <= ymax; j++) {
				setPixel(i, j, col);
			}
		}
	}

	@Override
	public void drawCircle(Point2D p, double rad, int col) {
		Point2D a = new Point2D(p.ix() - rad, p.iy() - rad); // The edges of the rectangle that block the circle - left
																// and high.
		Point2D c = new Point2D(p.ix() + rad, p.iy() + rad); // Right and down.
		int w = getWidth(), h = getHeight();

		if ((p.ix() - rad) < 0 || (p.ix() + rad) > w || (p.iy() - rad) < 0 || (p.iy() + rad) > h) { // If the circumference of the circle is beyond the board - do not draw anything.
			p = null;
			System.out.println("Your circle beyond the borders of the page");
		} else {
			for (int i = a.ix(); i <= c.ix(); i++) { // Run all over the points in the rectangle that block the circle.
				for (int j = a.iy(); j <= c.iy(); j++) {
					Point2D p2 = new Point2D(i, j);
					while (p2.distance(p) <= rad) { // As long as the point is in the circle - color it and continue.
						setPixel(i, j, col);
						break;
					}
				}
			}
		}
	}

	@Override
	public int fill(Point2D p, int new_v) {		
		ArrayList<Point2D> a = new ArrayList<Point2D>();
		a.add(p); //The first point in the list is P.
		
		int colorp = getPixel(p);
		int w = getWidth(), h = getHeight();
		
		Point2D up, down, right, left;

		while (!a.isEmpty()) { //As long as "a" isn't empty, continue running all over the points near p.
			if( p.ix()>w || p.iy()>h || p.ix()<0 || p.iy()<0) { //Checking if p is in the page.
				a.remove(0); //Erase the point from the list.
				}
			if(getPixel(p)!=colorp) {
				a.remove(0); //Erase the point from the list.
				}
			else { //Checking all the points near p.
				if (p.iy()+1<h && getPixel(p.ix(), p.iy()+1)==colorp) {
					up = new Point2D(p.ix(), p.iy()+1);
					a.add(up);
					}
				if (p.iy()-1>=0 && getPixel(p.ix(), p.iy()-1)==colorp) {
					down = new Point2D(p.ix(), p.iy()-1);
					a.add(down);
					}
				if (p.ix()+1<w && getPixel(p.ix()+1, p.iy())==colorp) {
					right = new Point2D(p.ix()+1, p.iy());	
					a.add(right);
					}
				if (p.ix()-1>=0 && getPixel(p.ix()-1, p.iy())==colorp) {
					left = new Point2D(p.ix()-1, p.iy());	
					a.add(left);
					}
				setPixel(p,new_v); //Drawing the point p.
				a.remove(0); //Erase the point from the list.
				p = a.get(0); //Change the point which in the first place in the list.
				}
			if(!a.isEmpty()) { //Ensure the list is not empty.
				p = a.get(0); //Put p in the first place in c.
				}			
			}	
		return 0;
	}

	@Override
	public int fill(int x, int y, int new_v) {
		Point2D p = new Point2D(x, y);
		fill(p, new_v);
		return 0;
	}

	@Override
	public Point2D[] shortestPath(Point2D p1, Point2D p2) {
		
		ArrayList<Point2D> answer = new ArrayList<Point2D>();
		
		int w = getWidth(), h = getHeight();
		int [][] array = new int[w][h];
		int colorp1 = getPixel(p1);
		
		Point2D up, down, right, left;
		Point2D p3;
		
		for (int i=0; i<w; i++) { //Running on all the page.
			for (int j=0; j<h; j++) {
				p3 = new Point2D(i,j);
				if(getPixel(p3)==colorp1) { //All the points which has the same color like p1.
					array[i][j] = -1;
				}
				else { //The points that has another color.
					array[i][j] = -5;
				}
			}
		}
		
		array[p1.ix()][p1.iy()]=0;
		answer.add(p1);
		while(!answer.isEmpty() || array[p2.ix()][p2.iy()]==-1) {  //Checking all the points near p1.
			if (p1.iy()-1>=0 && array[p1.ix()][ p1.iy()-1]==-1) { //up
				up = new Point2D(p1.ix(), p1.iy()-1);
				array[p1.ix()][p1.iy()-1] = array[p1.ix()][p1.iy()]+1;
				answer.add(up);
			}				
			if (p1.iy()+1<h && array[p1.ix()][p1.iy()+1]==-1) { //down.
				down = new Point2D(p1.ix(), p1.iy()+1);
				array[p1.ix()][p1.iy()+1] = array[p1.ix()][p1.iy()]+1;
				answer.add(down);
			}				
			if (p1.ix()+1<w && array[p1.ix()+1][p1.iy()]==-1) { //right.
				right = new Point2D(p1.ix()+1, p1.iy());
				array[p1.ix()+1][p1.iy()] = array[p1.ix()][p1.iy()]+1;
				answer.add(right);
			}
			if (p1.ix()-1>=0 && array[p1.ix()-1][p1.iy()]==-1) { //left.					
				left = new Point2D(p1.ix()-1, p1.iy());	
				array[p1.ix()-1][p1.iy()] = array[p1.ix()][p1.iy()]+1;
				answer.add(left);
			}
			answer.remove(0); //Erase the first value from the list.
			if(!answer.isEmpty()) { //Ensure the list is not empty.
				p1 = answer.get(0); //Put p in the first place in c.
			}			
		}
		
		if((array[p2.ix()][p2.iy()]!=-1)){ //If we success to get to p2.
			Point2D [] ans = new Point2D[array[p2.ix()][p2.iy()]]; //A new array which including all the points we need.
			
			int i=p2.ix();
			int j=p2.iy();
			while(array[i][j]!=0) {
				if(j-1>=0 && array[i][j-1]==array[i][j]+1) { //up.
					j=j-1;
				}
				else if(j+1<h && array[i][j+1]==array[i][j]+1) { //down.
						j=j+1;
					}
					else if(i+1<w && array[i+1][j]==array[i][j]+1) { //right.
							i=i+1;
						}
						else if(i-1>=0 && array[i-1][j]==array[i][j]+1) { //left.
								i=i-1;
								}
						
			p3 = new Point2D(i,j);
			ans[i] = p3;
			}
			
			return ans;
		}
		return null; //We don't success to get to p2.
	}

	@Override
	public int shortestPathDist(Point2D p1, Point2D p2) {
		Point2D [] array;
		array = shortestPath(p1, p2);
		return array.length;
	}

	@Override
	public void nextGenGol() {
		int w = getWidth(), h = getHeight();
		int counter = 0;
		Point2D p; 
		int [][] array = new int[w][h];
		
		for (int i=0; i<w; i++) { //Running on all the page.
			for (int j=0; j<h; j++) {
				p = new Point2D(i,j);
				if(getPixel(p)!=WHITE) {
					array[i][j] = 1; //If the point in the page is not white - put 1 in the array.
				}
				else { //Else put 0.
					array[i][j] = 0;
				}
			}
		}
		p = null; //Reset p.
		
		for (int i=0; i<w; i++) {
			for (int j=0; j<h; j++) {
				counter = 0;
				if(j+1<h) {
					if(array[i][j+1]==1) { //The point which above p.
						counter++;
					}
					if(i-1>=0) {
						if(array[i-1][j+1]==1) { //The point in the diagonal at the top - left for p.
							counter++;
						}
					}
					if(i+1<w) {
						if(array[i+1][j+1]==1) { //The point in the diagonal at the top - right for p.
						counter++;
						}
					}
				}
				if(j-1>=0) {
					if(array[i][j-1]==1) { //The point which under p.
						counter++;
					}
					if(i-1>=0) {
						if(array[i-1][j-1]==1) { //The point in the lower - left diagonal to p.
							counter++;
						}
					}
					if(i+1<w) { 
						if(array[i+1][j-1]==1) { //The point in the lower - right diagonal to p.
							counter++;
						}
					}
				}
				if(i+1<w) {
					if(array[i+1][j]==1) { //The point that is to the right of p.
						counter++;
					}
				}
				if(i-1>=0) {
					if(array[i-1][j]==1) { //The point that is to the left of p.
						counter++;
						}
					}

				p = new Point2D(i,j);
				if(array[i][j]==1) {
					if(counter<2 || counter>3) { //The cell with less than two living neighbors or more than three living neighbors then it is dead.
						setPixel(p,WHITE);
					}
					if(counter==2 || counter==3) { //The cell with two or three living neighbors is then alive.
						setPixel(p,BLACK);
					}
				}
				else { //getPixel(p)==WHITE.
					if(counter==3) { //If the cell is dead and there are three living neighbors around it - it is alive.
						setPixel(p,BLACK);
					}
				}
			}
		}

	}

	@Override
	public void fill(int c) {
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				this.setPixel(x, y, c);
			}
		}

	}

}
