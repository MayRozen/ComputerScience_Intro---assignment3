/* ID: 212051007
 *
 */

package Ex3;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class MyMap2DTest {
	private static MyMap2D a;
	int WHITE = Color.WHITE.getRGB();
	int BLUE = Color.BLUE.getRGB();
	int BLACK = Color.BLACK.getRGB();
	int YELLOW = Color.YELLOW.getRGB();
	int GREEN = Color.GREEN.getRGB();
	int RED = Color.RED.getRGB();

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	void testdrawSegment1() { //Check if all the points that we want to draw - really were painted.
		a = new MyMap2D(20);
		Point2D p1 = new Point2D(0,0);
		Point2D p2 = new Point2D(8,0);
		boolean check = false;
		int counter = 0;
		
		a.drawSegment(p1, p2, BLACK);
		
		for(int i=0; i<a.getWidth(); i++) {
			for(int j=0; j<a.getHeight(); j++) {
				if(a.getPixel(i,j)==BLACK) {
					counter++;
				}
			}
		}
		if(counter==9) {
			check = true;
		}
		
		assertEquals(true, check);	
	}
	
	@Test
	void testdrawRect1() { //Check if p3 is in the rect.
		a = new MyMap2D(80);
		Point2D p1 = new Point2D(12,12);
		Point2D p2 = new Point2D(6,6);
		Point2D pcheck = new Point2D(10,10);
		boolean check = false;
		
		a.drawRect(p1, p2, BLACK);
		
		for(int i=0; i<a.getWidth(); i++) {
			for(int j=0; j<a.getHeight(); j++) {
				if(a.getPixel(i,j)==BLACK) {
				}
			}
		}
		
		if(a.getPixel(pcheck)==BLACK) {
			check = true;
		}
		
		assertEquals(true, check);	
	}
	
	@Test
	void testdrawCircle1() { //Checking if pcheck is really in the circle.
		a = new MyMap2D(80);
		Point2D p1 = new Point2D(6,6);
		double rad = 6;
		Point2D pcheck = new Point2D(10,10);
		boolean check = false;
		
		a.drawCircle(p1, rad, BLACK);
		
		if(a.getPixel(pcheck)==BLACK) {
			check = true;
		}
		
		assertEquals(true, check);	
	}
	@Test
	void testdrawCircle2() { //Checking if we get the massage when our circle is out of the page.
		a = new MyMap2D(80);
		Point2D p1 = new Point2D(2,2);
		double rad = 20;
		boolean check = false;
		
		a.drawCircle(p1, rad, BLACK);
		
		if(a.getPixel(p1)!=BLACK) { //If p became null.
			check = true;
		}
		
		assertEquals(true, check);	
	}
	
	@Test
	void testfill1() { //Checking if the points in the rect are painted.
		a = new MyMap2D(80);
		Point2D p1 = new Point2D(6,6);
		Point2D p2 = new Point2D(10,10);
		int counter = 0;
		boolean check = false;
		
		a.drawRect(p1, p2, BLUE); //Draw a blue rect.
		a.fill(p1, GREEN); //Paint the rect green.
		
		for(int i=6; i<=10; i++) {
			for(int j=6; j<=10; j++) {
				if(a.getPixel(i,j)==GREEN) {
					counter++;
				}
			}
		}
		
		if(counter==25) { //If all the rect became green.
			check = true;
		}
		
		assertEquals(true, check);	
	}
	@Test
	void testshortestPath1() { //Checking if the shortest path we get is right.
		a = new MyMap2D(80);
		Point2D p1 = new Point2D(6,6);
		Point2D p2 = new Point2D(10,10);
		Point2D p3 = new Point2D(11,6);
		Point2D p4 = new Point2D(11,11);
		int counter = 0;
		int colorp3 = a.getPixel(p3);
		boolean check = false;
		
		a.drawRect(p1, p2, BLUE); //Draw a blue rect.
		a.shortestPath(p3, p4); //Paint the shortest path with colorp3.
		
		for(int i=11; i<=11; i++) { //Count all the points that making the shortest path.
			for(int j=6; j<=11; j++) {
				if(a.getPixel(i,j)==colorp3) {
					counter++;
				}
			}
		}
		
		if(counter==6) { //If we get the shortest path, we need to get 6 painted points.
			check = true;
		}
		
		assertEquals(true, check);	
	}
	@Test
	void testshortestPathDist1() { //Checking if the shortestpathDist return the length of the array of the shortest path. 
		a = new MyMap2D(80);
		Point2D p1 = new Point2D(6,6);
		Point2D p2 = new Point2D(10,10);
		Point2D p3 = new Point2D(11,6);
		Point2D p4 = new Point2D(11,11);
		boolean check = false;
		
		a.drawRect(p1, p2, BLUE); //Draw a blue rect.
		a.shortestPath(p3, p4); //Paint the shortest path with colorp3.
		
		if(a.shortestPathDist(p1,p2)==6) { //If we get the shortest path, we need to get 6 painted points.
			check = true;
		}
		
		assertEquals(true, check);	
	}
	
	@Test
	void testnextGenGol1() { //Checking if the points in the rect are painted..
		a = new MyMap2D(80);
		Point2D p1 = new Point2D(6,6);
		int counter = 0;
		boolean check = false;
		 
		a.fill(p1, GREEN); //Pain all the page green.
		a.nextGenGol();
		
		if(a.getPixel(0,0)==BLACK){
			counter++;
		}
		if(a.getPixel(79,0)==BLACK){
			counter++;
		}
		if(a.getPixel(0,79)==BLACK) {
			counter++;
		}
		if(a.getPixel(79,79)==BLACK) {
			counter++;
		}
		if(counter==4) { //If all the rect became green.
			check = true;
		}
		
		assertEquals(true, check);	
	}
	
}
