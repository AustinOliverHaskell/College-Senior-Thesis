package austin.structures;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

/**
 *  The Layer class is a single "Slice" of a structure
 *   the class can output itself to a file aswell as 
 *   mutate its underlying data structure
 */
public class Layer
{
	// Range of coordanates that are valid
	// Additionally deturmines the size of the
	// points when they are rasterized
	private final int xSize;
	private final int ySize;

	// Area (In pixels) of closed areas
	private int area = 0;

	// Color values defined as constants
	private final int white      = 0xFFFFFFFF;
	private final int background = 0xFF0000F0;
	private final int highlight  = 0xFF00FF00;
	private final int remove     = 0xFFFF0000;

	// How many points are in each layer
	private final int pointCount = 5;

	// Directions, this will eventually be removed
	// upon the creation of a better fill algorithm
	private enum fillDirection
	{
		UP, DOWN, LEFT, RIGHT
	}

	// Data member to contain the rasterized form of
	//  this layer
	private BufferedImage img;

	// List of points
	private ArrayList<Vec> points;

	public Layer(int xSize, int ySize, int zIndex)
	{
		this.xSize = xSize;
		this.ySize = ySize;

		img = new BufferedImage(this.xSize, this.ySize, BufferedImage.TYPE_INT_ARGB);
		points = new ArrayList<Vec>();

		for (int x = 0; x < xSize; x++)
		{
			for (int y = 0 ; y < ySize; y++)
			{
				img.setRGB(x, y, background);
			}
		}

		Random rand = new Random();

		int x = rand.nextInt(xSize);
		int y = rand.nextInt(ySize);
		int z = zIndex;

		Vec start = new Vec(x, y, z);

		for (int i = 0; i < pointCount; i++)
		{
			Vec startPoint = new Vec(x, y, z);

			x = rand.nextInt(xSize);
			y = rand.nextInt(ySize);

			points.add(startPoint);
		}

		points.add(start);


		// Create the 2d representation (in pixels)
		//  of this layer, this is not needed for 
		//  rendering (in 3d)
		rasterize();

		// Calculate the area (in pixels)
		//  of all the closed in shapes
		findUsableArea();
	}

	/**
	*   Copy constructor
	*/
	private Layer(int xSize, int ySize, ArrayList<Vec> points)
	{
		this.points = points;
		this.xSize = xSize;
		this.ySize = ySize;

		img = new BufferedImage(this.xSize, this.ySize, BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < xSize; x++)
		{
			for (int y = 0 ; y < ySize; y++)
			{
				img.setRGB(x, y, background);
			}
		}

		rasterize();
		findUsableArea();
	}


	public void mutate(float rate)
	{

	}

	/**
	*	This function does a full copy of the calling layer	
	*    (including ArrayList data)
	*
	*	@return full copy of this object
	*/
	public Layer clone()
	{
		Layer retVal = new Layer(xSize, ySize, getPoints());
		return retVal;
	}

	public void findUsableArea()
	{
		int usableArea = 0;

		for (int y = 0; y < ySize; y++)
		{
			fill(0, y, remove, fillDirection.RIGHT);
			fill(xSize-1, y, remove, fillDirection.LEFT);

			for (int x = 0; x < xSize; x++)
			{
				if (img.getRGB(x, y) == remove)
				{
					usableArea++;
				}
			}
		}

		area = usableArea;
	}

	public int getArea()
	{
		return this.area;
	}

	private void fill(int x, int y, int color, fillDirection direction)
	{
		if (direction == fillDirection.LEFT)
		{
			while((x >= 0) && (img.getRGB(x, y) != white))
			{
				img.setRGB(x, y, color);
				x--;
			}
		}
		else if (direction == fillDirection.RIGHT)
		{
			while((x < xSize) && (img.getRGB(x, y) != white))
			{
				img.setRGB(x, y, color);
				x++;
			}
		}
		else if (direction == fillDirection.UP)
		{
			while((y >= 0) && (img.getRGB(x, y) != white))
			{
				img.setRGB(x, y, color);
				y--;
			}
		}
		else
		{
			while((y < ySize) && (img.getRGB(x, y) != white))
			{
				img.setRGB(x, y, color);
				y++;
			}
		}


	}

	private void rasterize()
	{
		for (int i = 0; i < points.size()-1; i++)
		{
			//img.setRGB(points.get(i).x, points.get(i).y, remove);
			plot(points.get(i), points.get(i+1));
			//Debug.logf("Drawing line from " + points.get(i).toString() + " to point " + points.get(i+1).toString());
		}
		Debug.logf(" -------------------- ");
	}

	private void plot(Vec one, Vec two)
	{
		if (one.x > two.x)
		{
			Vec temp = one;
			one = two;
			two = temp;
		}

		Debug.logf("Drawing line from " + one.toString() + " to point " + two.toString());


		int deltaX = one.x - two.x;
		int deltaY = one.y - two.y;

		if (deltaY == 0)
		{
			// Horizontal case
			for (int x = one.x; x < two.x; x++)
			{
				//Debug.logs("Plotting: " + one.toString() + " to " + two.toString());
				//Debug.logs("X IS ->" + x + " AND Y IS ->" + one.y);

				img.setRGB(x, one.y, white);
			}
		}
		else if (deltaX == 0)
		{
			if (one.y > two.y)
			{
				Vec temp = one;
				one = two;
				two = temp;
			}
			// Plot vertical line here
			for (int y = one.y; y < two.y; y++)
			{
				img.setRGB(one.x, y, white);
			}
		}
		else
		{
			float deltaError = Math.abs((float)deltaY / (float)deltaX);

			float error = (float)0.0;

			if (one.y > two.y)
			{
				int y = one.y;

				for (int x = one.x; x < two.x; x++)
				{
					img.setRGB(x, y, white);

					error += deltaError;

					if (error >= 0.5)
					{
						y--;
						error = error - (float)1.0;
					}
				}
			}
			else
			{
				int y = one.y;

				for (int x = one.x; x < two.x; x++)
				{
					img.setRGB(x, y, white);

					error += deltaError;

					if (error >= 0.5)
					{
						y++;
						error = error - (float)1.0;
					}
				}
			}
		}

	}

	/**
	*   This function returns a copy of the underlying array   
	*    it returns a copy of the array as to not allow for 
	*    a change in data
	*
	*   @return A COPY of the array of points
	*/
	public ArrayList<Vec> getPoints()
	{
		return new ArrayList<Vec>(points);
	}

	/**
	*    This function will create a read only copy of the data, this copy can be
	*     relyably used by any number of threads
	*
	*    @return A copy of the list of points that is unable to be modifyed
	*/
	public List<Vec> getSafePoints()
	{
		return Collections.unmodifiableList(points);
	}

	/**
	*	Save will take the pixel data after rasterization and then save it to a ".png" file
	*
	*   @param filePath - The location of the file to be saved, including the name of the file
	*/
	public void save(String filePath)
	{
		try 
		{
			File file = new File(filePath);
			ImageIO.write(img, "png", file);
		}
		catch (Exception error)
		{
			error.printStackTrace();
		}
	}

	@Override
	public String toString()
	{
		StringBuilder retVal = new StringBuilder();

		retVal.append("Area: ");
		retVal.append(Integer.toString(getArea()));
		retVal.append("\n");

		for (int i = 0; i < points.size(); i++)
		{
			retVal.append(points.get(i).toString());
			retVal.append("\n");
		}

		return retVal.toString();
	}
}