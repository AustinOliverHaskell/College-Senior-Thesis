import java.util.*;

public class Triangle
{
	// Three points
	public Vec3 a, b, c;
	private Vec3 normal;
	private boolean covered;

	private ArrayList<ArrayList<Vec3>> edgeList;

	Triangle(Vec3 a, Vec3 b, Vec3 c)
	{
		this.a = a;
		this.b = b;
		this.c = c;

		this.covered = false;

		edgeList = new ArrayList<ArrayList<Vec3>>();

		CalcNormal();
		addEdges();
	}

	Triangle()
	{
		this.a = new Vec3();
		this.b = new Vec3();
		this.c = new Vec3();

		this.covered = false;

		edgeList = new ArrayList<ArrayList<Vec3>>();

		CalcNormal();
		addEdges();
	}

	/**
	 * Get the normal of this triangle
	 * @return the normal of the triangle
	 */
	public Vec3 getNormal()
	{
		return this.normal;
	}

	/**
	 * returns wheather or not this triangle is visible
	 * @return true if the triangle isnt visible
	 */
	public boolean isCovered()
	{
		return this.covered;
	}

	/**
	 * Sets the covered condition of this triangle to the value provided
	 * @param b true or false, is this triangle covered
	 */
	public void setCovered(boolean b)
	{
		this.covered = b;
	}

	/**
	 * Calculates the normal and sets the private feild of the triangle
	 */
	private void CalcNormal()
	{
		Vec3 edgeOne = b.sub(a);
		Vec3 edgeTwo = c.sub(a);

		Vec3 retVal = Vec3.cross(edgeOne, edgeTwo);

		retVal.normalize();

		this.normal = retVal;
	}

	private void addEdges()
	{
		ArrayList<Vec3> first  = new ArrayList<Vec3>();
		ArrayList<Vec3> second = new ArrayList<Vec3>();
		ArrayList<Vec3> third  = new ArrayList<Vec3>();

		first.add(a);
		first.add(b);

		second.add(a);
		second.add(c);

		third.add(b);
		third.add(c);

		edgeList.add(first);
		edgeList.add(second);
		edgeList.add(third);
	}

	public void markEdgeAsUsed(Vec3 one, Vec3 two)
	{
		for (ArrayList<Vec3> t : edgeList)
		{
			// This is really ugly but it gets the job done. Check if those points exist as an edge
			if (t.get(0).equals(one) && t.get(1).equals(two) || t.get(0).equals(two) && t.get(1).equals(one))
			{
				// Remove that pair
				edgeList.remove(edgeList.indexOf(t));
			}
		}
	}

	public ArrayList<Vec3> getFreeEdge()
	{
		ArrayList<Vec3> retVal = null;
		// Make sure that there is a free edge
		if (edgeList.size() > 0)
		{
			retVal = edgeList.get(0);
			edgeList.remove(0);
		}

		return retVal;
	}

	/**
	 * Calcuates the Centroid (The center point) of the triangle
	 * @return A Vec3 containing the 3D coordanates of the center
	 */
	public Vec3 getCentroid()
	{
		Vec3 retVal = new Vec3();

		retVal.x = (a.x + b.x + c.x) / 3.0f;
		retVal.y = (a.y + b.y + c.y) / 3.0f;
		retVal.z = (a.z + b.z + c.z) / 3.0f;

		return retVal;
	}

	/**
	 * 
	 * @param  a [description]
	 * @param  b [description]
	 * @return   [description]
	 */
	public double pathagorian(float a, float b)
	{
		return Math.sqrt((a * a)+(b * b));
	}

	public boolean doesPointExist(Vec3 point)
	{
		boolean retVal = false;

		if (a.equals(point) || b.equals(point) || c.equals(point))
		{
			retVal = true;
		}

		return retVal;
	}

	public double getArea()
	{
		double retVal = -1;

		double alen = Vec3.dist(a, b);
		double blen = Vec3.dist(b, c);
		double clen = Vec3.dist(c, a);

		retVal  = (alen + blen + clen)/2;
		retVal *= ((alen + blen + clen)/2) - alen;
		retVal *= ((alen + blen + clen)/2) - blen;
		retVal *= ((alen + blen + clen)/2) - clen;

		retVal = Math.sqrt(retVal);

		return retVal;
	}

	@Override
	public String toString()
	{
		return "-> "+a.toString() + b.toString() + c.toString() + "<-";
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean retVal = false;

		// Cast for use
		Triangle other = (Triangle)obj;

		// Brute Force, compare every point to every other point
		if ((a.equals(other.a) || b.equals(other.a) || c.equals(other.a)) &&
			(a.equals(other.b) || b.equals(other.b) || c.equals(other.b)) &&
			(a.equals(other.c) || b.equals(other.c) || c.equals(other.c)))
		{
			retVal = true;
		}

		return retVal;
	}

	@Override
	public int hashCode()
	{
		return (int) a.add(b.add(c)).x;
	}

	public boolean doesTriangleHaveVertex(Vec3 point)
	{
		boolean retVal = (point.equals(a) || point.equals(b) || point.equals(c));

		return retVal;
	}
}