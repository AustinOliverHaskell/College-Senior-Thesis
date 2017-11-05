package austin.structures;

import java.util.*;

public class ExtendedVec
{
	private Vec point;
	private int relativePosition;

	ExtendedVec(Vec vec, int pos)
	{
		this.point = vec;
		this.relativePosition = pos;
	}

	ExtendedVec(int x, int y, int z)
	{
		this.relativePosition = 0;
		this.point = new Vec(x, y, z);
	}

	ExtendedVec(int x, int y, int z, int pos)
	{
		this.relativePosition = pos;
		this.point = new Vec(x, y, z);
	}

	public Vec getPoint()
	{
		return this.point;
	}

	public void setPoint(Vec point)
	{
		this.point = point;
	}

	public int getPosition()
	{
		return this.relativePosition;
	}

	public void setPosition(int pos)
	{
		this.relativePosition = pos;
	}

	public String pointToString()
	{
		return this.point.toString();
	}

	@Override
	public String toString()
	{
		return this.point.toString() + " pos " + relativePosition;
	}

}