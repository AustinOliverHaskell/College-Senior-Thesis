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
	private final int xSize;
	private final int ySize;

	private int area = 0;

	private final int white      = 0xFFFFFFFF;
	private final int background = 0xFF0000F0;
	private final int highlight  = 0xFF00FF00;
	private final int remove     = 0xFFFF0000;

	private final int pointCount = 5;

	private enum fillDirection
	{
		UP, DOWN, LEFT, RIGHT
	}

	private BufferedImage img;
	private ArrayList<Vec> points;

	public Layer(int xSize, int ySize)
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
		int z = 0;

		Vec start = new Vec(x, y, z);

		for (int i = 0; i < pointCount; i++)
		{
			Vec startPoint = new Vec(x, y, z);

			x = rand.nextInt(xSize);
			y = rand.nextInt(ySize);

			points.add(startPoint);
		}

		points.add(start);


		rasterize();

		findUsableArea();
	}

	public void mutate(float rate)
	{

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
		}
	}

	private void plot(Vec one, Vec two)
	{
		if (one.x > two.x)
		{
			Vec temp = one;
			one = two;
			two = temp;
		}


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