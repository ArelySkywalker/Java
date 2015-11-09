

/**
 * @author Arely Miramontes
 * @version 08/23/2014
 * 
 * Path Tracing
 *
 * This program draws pixels to create a path in the shape of
 * a flower that loops
 * around in a circle using mathematics.
 */
public class flowerVisual {


    public static void main(String[] args) {
	// Creating the length and size of the pixels 
	Display myDisplay = new Display(7000, 5);
	drawFlower(myDisplay);
    }

    public static void drawFlower(Display myDisplay) {
	// Creating the center of the large path that the loops follow
	int centerX = myDisplay.getWidth() / 2;
	int centerY = myDisplay.getWidth() / 2;

	double degAng = 0;
	double smallAng = 0;
	double radius = 150;
	double SmallRadius = 30;
	double sCenterX,sCenterY,x,y;
	// Creating a while loop for the large path, and creating a for loop 
	// for the smaller loops
	while (true) { 
	            
	    sCenterX = centerX + radius * Math.cos(Math.toRadians(degAng));
	    sCenterY = centerY + radius * Math.sin(Math.toRadians(degAng));

	    for(int i = 0; i<10; i++){
		x = sCenterX + SmallRadius * Math.cos(Math.toRadians(smallAng));
		y = sCenterY + SmallRadius * Math.sin(Math.toRadians(smallAng));
		myDisplay.drawNextPixel((int) x, (int) y);

		smallAng += .4;
	    }
	    degAng += .4;
        }
    }   
}