package austin.structures;

import java.util.*;

public class Vec
{
	public int x;
	public int y;
	public int z;

	Vec()
	{
		x = 0; 
		y = 0;
		z = 0;
	}

	Vec(Vec vec)
	{
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}

	Vec(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void mutate(float rate)
	{
		Random random = new Random();

		if (random.nextFloat() > rate)
		{
			if (random.nextInt(2) > 1)
			{
				this.x--;
			}
			this.x++;
		}

	}

	@Override
	public String toString()
	{
		return ""+x+" "+y+" "+z;
	}

}