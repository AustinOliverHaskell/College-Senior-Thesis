package austin.structures;

import java.util.*;
import java.lang.Math.*;

public class Vec
{
	public float x;
	public float y;
	public float z;

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

	Vec(float x, float y, float z)
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

	public Vec subtract(Vec other)
	{
		Vec retVal = new Vec();

		retVal.x = this.x - other.x;
		retVal.y = this.y - other.y;
		retVal.z = this.z - other.z;

		return retVal;
	}

	public Vec add(Vec other)
	{
		Vec retVal = new Vec();

		retVal.x = this.x + other.x;
		retVal.y = this.y + other.y;
		retVal.z = this.z + other.z;

		return retVal;
	}

	public Vec cross(Vec other)
	{
		/*	Set Normal.x to (multiply U.y by V.z) minus (multiply U.z by V.y)
			Set Normal.y to (multiply U.z by V.x) minus (multiply U.x by V.z)
			Set Normal.z to (multiply U.x by V.y) minus (multiply U.y by V.x)  */

		Vec retVal = new Vec();

		retVal.x = this.y * other.z - this.z * other.y;
		retVal.y = this.z * other.x - this.x * other.z;
		retVal.z = this.x * other.y - this.x * other.y;

		return retVal;
	}

	public void normalize()
	{
		double distance = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);

		this.x = this.x / (float)distance;
		this.y = this.y / (float)distance;
		this.z = this.z / (float)distance;
	}

	@Override
	public String toString()
	{
		return ""+x+" "+y+" "+z;
	}

}