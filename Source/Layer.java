import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

public class Layer
{
	private final int xSize;
	private final int ySize;

	private int area = 0;

	private final int white      = 0xFFFFFFFF;
	private final int background = 0xFF0000F0;
	private final int highlight  = 0xFF00FF00;
	private final int remove     = 0xFFFF0000;

	private int numLines = 20;

	private BufferedImage img;

	public Layer(int xSize, int ySize)
	{
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

		Random rand = new Random();

		int startX = rand.nextInt(xSize);
		int startY = rand.nextInt(ySize);

		// Create the randomized image data
		for (int i = 0; i < numLines; i++)
		{
			int endX = rand.nextInt(xSize);
			int endY = rand.nextInt(ySize);

			if (i % 2 == 0)
			{
				for (int x = startX; x < endX; x++)
				{
					img.setRGB(x, endY, white);
				}
			}
			else
			{
				for (int y = startY; y < endY; y++)
				{
					img.setRGB(endX, y, white);
				}
			}

			startX = endX;
			startY = endY;
		}

		findUsableArea();
	}

	public void mutate(float rate)
	{

	}

	public void findUsableArea()
	{
		int xCenter = xSize/2;
		int yCenter = ySize/2;

		floodFill(xCenter, yCenter, highlight);

		int usableArea = 0;
		boolean hasGreen = false;
		boolean hasBlue  = false;


		// TODO: This is really inefficiant
		for (int x = 0; x < xSize; x++)
		{
			for (int y = 0; y < ySize; y++)
			{
				int color = img.getRGB(x, y);

				if (color == background)
				{
					hasBlue = true;
				}
				else if (color == highlight)
				{
					hasGreen = true;
					usableArea++;
				}
			}
		}

		if (hasGreen && hasBlue)
		{
			area = usableArea;
		}

		// Remove perimiter
		for (int i = 0; i < xSize; i++)
		{
			floodFill(i, 0, remove);
			floodFill(i, ySize-1, remove);
		}

		for (int i = 0; i < ySize; i++)
		{
			floodFill(0, i, remove);
			floodFill(xSize-1, i, remove);
		}
	}

	public int getArea()
	{
		return this.area;
	}

	private void floodFill(int x, int y, int color)
	{

		if ((x < img.getWidth() && x >= 0) && (y < img.getHeight() && y >= 0))
		{
			// Recursive flood
			if (img.getRGB(x, y) != white)
			{
				img.setRGB(x, y, color);
				floodFill(x+1, y-1, color);
				floodFill(x+1, y+1, color);
				floodFill(x-1, y-1, color);
				floodFill(x-1, y+1, color);
				floodFill(x,   y-1, color);
				floodFill(x,   y+1, color);
				floodFill(x+1, y, color);
				floodFill(x-1, y, color);
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
}