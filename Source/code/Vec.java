package austin.structures;

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

	Vec(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString()
	{
		return "\""+x+","+y+","+z+"\"";
	}

}