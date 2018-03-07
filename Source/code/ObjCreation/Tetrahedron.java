import java.util.*;
import Jama.*;

public class Tetrahedron
{
	private ArrayList<Triangle> faceList;
	private ArrayList<Vec3> pointList;

	/**
	 *  This one is the special constructor, given one triangle this constructor
	 *   will create the other triangles from a point generated to be equadistant
	 *   from the verticies of the supplied triangle
	 */
	Tetrahedron(Triangle a)
	{
		faceList = new ArrayList<Triangle>();
		pointList = new ArrayList<Vec3>();

		// First figure out how long a single side of the triangle is
		double distance = Vec3.dist(a.a, a.b);
		System.out.println("Distance:" + distance);

		// The missing point to complete the tetrahedron
		Vec3 point = new Vec3();

		// Every angle in these triangles is 60 degrees, theyre equalateral triangles
		Vec3 direction = new Vec3((float)distance, (float)Math.toRadians(60), (float)Math.toRadians(60));

		point = a.getCentroid();

		double baseLength = Vec3.dist(a.a, point);

		// Currently the vertex point needs to be translated x units away perpendicular to 
		//  the plane of the triangle. Im not sure how this is done. 

		System.out.println(point);

		float height = ( (float)Math.abs(Math.pow((float)Vec3.dist(a.a, a.getCentroid()),2) -  Math.pow((float)Vec3.dist(a.a, a.b),2)));

		//point.z += 0.7517f;
		point.z += height;

		// Create the other triangles
		Triangle b = new Triangle(a.a, a.b, point);
		Triangle c = new Triangle(a.a, a.c, point);
		Triangle d = new Triangle(a.b, a.c, point);

		// Add the triangles to the facelist
		faceList.add(a);
		faceList.add(b);
		faceList.add(c);
		faceList.add(d);
		
		pointList.add(a.a);
		pointList.add(a.b);
		pointList.add(a.c);
		pointList.add(point);
		//assert(false);
		// Done!
	}

	Tetrahedron(Tetrahedron tetrahedron, ArrayList<Tetrahedron> list) throws NoValidSpaces
	{
		faceList = new ArrayList<Triangle>();
		pointList = new ArrayList<Vec3>();

		Vec3 point = new Vec3();
		Triangle tri = null;
		NoValidSpaces exception = null;

		// I want to pick a random face to build off of, this array is filled
		//  with the facenumbers that are checked. 
		ArrayList<Integer> randomFaceOrder = new ArrayList<Integer>();

		for (int i = 0; i < 4; i++)
		{
			randomFaceOrder.add(i);
		}

		Collections.shuffle(randomFaceOrder);

		// Attempt to build off the first face from the array, 
		//  if this fails too much then an exception is thrown 
		//  telling the structure class that the tetrahedron picked
		//  cant be grown off of
		for (int i = 0; i < randomFaceOrder.size(); i++)
		{
			// The face that we are attempting to build off of
			tri = tetrahedron.getFace(randomFaceOrder.get(i));

			// Make sure that we arnt covering up a face thats already being used
			if (tri.isCovered())
			{
				if (i == 3)
				{
					System.out.println("Continue on last iteration. " + exception);
				}
				continue;
			}

			Vec3 oppositePoint = tetrahedron.getFourthPoint(tri);
			Vec3 centroid      = tri.getCentroid();

			// The missing point to complete the tetrahedron
			point = Vec3.pointAlongLine(centroid, oppositePoint, 1);

			if (tri.doesPointExist(point))
			{ 
				// Was getting some "Flat" tetrahedrons earlier, this is to nip that in the bud
				System.out.println(tetrahedron);
				System.out.println(point);
				assert(false);
			}

			boolean breakCondition;

			breakCondition = (doesPointCollideWithStructure(point, list));
			breakCondition = (breakCondition || doesPointCollideWithStructure(Vec3.midpoint(point, tri.a), list));
			breakCondition = (breakCondition || doesPointCollideWithStructure(Vec3.midpoint(point, tri.b), list));
			breakCondition = (breakCondition || doesPointCollideWithStructure(Vec3.midpoint(point, tri.c), list));

			if (!breakCondition)
			{
				break;
			}

			// If we get to this point then there isnt a way to build off of this tetrahedron
			//  so we need to create an exception and then throw it
			if (i == 3)
			{
				exception = new NoValidSpaces();
			}
		}

		// If we exit the loop and we happened to make an exception then we need to throw it now
		if (exception != null)
		{
			throw exception;
		}

		//System.out.println(tetrahedron);
		//System.out.println(tri);
		//System.out.println(tri.isCovered());
		//System.out.println(tri.getCentroid());
		//System.out.println(tetrahedron.getFourthPoint(tri));

		// If a is null at this point then there is a big problem
		assert(tri != null);

		// Create the other triangles
		Triangle b = new Triangle(tri.a, tri.b, point);
		Triangle c = new Triangle(tri.a, tri.c, point);
		Triangle d = new Triangle(tri.b, tri.c, point);

		// Mark the triangle as covered
		tri.setCovered(true);

		// Add the triangles to the facelist
		faceList.add(tri);
		faceList.add(b);
		faceList.add(c);
		faceList.add(d);

		pointList.add(tri.a);
		pointList.add(tri.b);
		pointList.add(tri.c);
		pointList.add(point);
	}

	/**
	 * Method that determines wheather a given point is within the bounds
	 *  of any of the tetrahedrons in the ArrayList list
	 *  
	 *  NOTE: If the point is coplanar it is considered within the bounds
	 * @param point point to check against this shape
	 * @return      true if point is on or within this tetrahedron
	 */
	public static boolean doesPointCollideWithStructure(Vec3 point, ArrayList<Tetrahedron> list)
	{
		// TODO: Finish this, check the bookmark on determantants
		boolean retVal = false;

		for (Tetrahedron t : list)
		{
			if (t.pointContained(point))
			{
				retVal = true;
			}
		}

		return retVal;
	}

	/**
	 * Checks if the given point falls withing this tetrahedron
	 * @param  point point to check
	 * @return       true if point is contained within this tetrahedron
	 */
	public boolean pointContained(Vec3 point)
	{
		boolean retVal = true;

		Matrix D0 = new Matrix(4, 4);
		Matrix D1 = new Matrix(4, 4);
		Matrix D2 = new Matrix(4, 4);
		Matrix D3 = new Matrix(4, 4);
		Matrix D4 = new Matrix(4, 4);

		// Get all of the points to make this quicker
		Vec3 a = pointList.get(0);
		Vec3 b = pointList.get(1);
		Vec3 c = pointList.get(2);
		Vec3 d = pointList.get(3);

		// Alot of this is setup
		D0.set(0, 0, a.x); D0.set(1, 0, a.y); D0.set(2, 0, a.z); D0.set(3, 0, 1.0);
		D0.set(0, 1, b.x); D0.set(1, 1, b.y); D0.set(2, 1, b.z); D0.set(3, 1, 1.0); 
		D0.set(0, 2, c.x); D0.set(1, 2, c.y); D0.set(2, 2, c.z); D0.set(3, 2, 1.0); 
		D0.set(0, 3, d.x); D0.set(1, 3, d.y); D0.set(2, 3, d.z); D0.set(3, 3, 1.0);  

		D1.set(0, 0, point.x); D1.set(1, 0, point.y); D1.set(2, 0, point.z); D1.set(3, 0, 1.0);
		D1.set(0, 1, b.x); D1.set(1, 1, b.y); D1.set(2, 1, b.z); D1.set(3, 1, 1.0); 
		D1.set(0, 2, c.x); D1.set(1, 2, c.y); D1.set(2, 2, c.z); D1.set(3, 2, 1.0); 
		D1.set(0, 3, d.x); D1.set(1, 3, d.y); D1.set(2, 3, d.z); D1.set(3, 3, 1.0);

		D2.set(0, 0, a.x); D2.set(1, 0, a.y); D2.set(2, 0, a.z); D2.set(3, 0, 1.0);
		D2.set(0, 1, point.x); D2.set(1, 1, point.y); D2.set(2, 1, point.z); D2.set(3, 1, 1.0); 
		D2.set(0, 2, c.x); D2.set(1, 2, c.y); D2.set(2, 2, c.z); D2.set(3, 2, 1.0); 
		D2.set(0, 3, d.x); D2.set(1, 3, d.y); D2.set(2, 3, d.z); D2.set(3, 3, 1.0);

		D3.set(0, 0, a.x); D3.set(1, 0, a.y); D3.set(2, 0, a.z); D3.set(3, 0, 1.0);
		D3.set(0, 1, b.x); D3.set(1, 1, b.y); D3.set(2, 1, b.z); D3.set(3, 1, 1.0); 
		D3.set(0, 2, point.x); D3.set(1, 2, point.y); D3.set(2, 2, point.z); D3.set(3, 2, 1.0); 
		D3.set(0, 3, d.x); D3.set(1, 3, d.y); D3.set(2, 3, d.z); D3.set(3, 3, 1.0);

		D4.set(0, 0, a.x); D4.set(1, 0, a.y); D4.set(2, 0, a.z); D4.set(3, 0, 1.0);
		D4.set(0, 1, b.x); D4.set(1, 1, b.y); D4.set(2, 1, b.z); D4.set(3, 1, 1.0); 
		D4.set(0, 2, c.x); D4.set(1, 2, c.y); D4.set(2, 2, c.z); D4.set(3, 2, 1.0); 
		D4.set(0, 3, point.x); D4.set(1, 3, point.y); D4.set(2, 3, point.z); D4.set(3, 3, 1.0);

		double d0 = D0.det();
		double d1 = D1.det();
		double d2 = D2.det();
		double d3 = D3.det();
		double d4 = D4.det();

		ArrayList<Double> determantants = new ArrayList<Double>();

		determantants.add(d0);
		determantants.add(d1);
		determantants.add(d2);
		determantants.add(d3);
		determantants.add(d4);

		double total = d1 + d2 + d3 + d4;

		// Make sure that the sum is close to the value of d0
		//  0.001 is a tollerance to check because of loss of
		//  precision. 
		if (Math.abs(d0 - total) > 0.001)
		{
			System.out.println("There's something very wrong: sum is not equal to D0.");
			assert(false);
		}

		for (int i = 1; i < determantants.size(); i++)
		{
			if (determantants.get(i) != 0)
			{
				// If one of them has a differing sign then the point isnt fully in the triangle
				if (!areSignsSame(determantants.get(0), determantants.get(i)))
				{
					retVal = false;
					break;
				}
			}
			else
			{
				// Coplanar
				break;
			}			
		}

		return retVal;
	}

	/**
	 * Gets one of the face triangles of the Tetrahedron, if the
	 *  index param is out of range then this method returns null
	 * @param  index index to return
	 * @return       face triangle of the tetrahedron, null if index is out of range
	 */
	public Triangle getFace(int index)
	{
		if (index < 4 && index >= 0)
		{
			return faceList.get(index);
		}
		else
		{
			return null;
		}
	}

	/**
	 * Sets one of the faces of the Tetrahedron, if the index given is
	 *  outside of the valid range of the array then this method is a 
	 *  no op
	 * @param t     triangle to add to the list
	 * @param index index to add to 0-3
	 */
	public void setFace(Triangle t, int index)
	{
		if (index < 4 && index >= 0)
		{
			faceList.set(index, t);
		}
	}

	/**
	 * Gets the missing point from the tetraherdron that is not contained within
	 *  t
	 * @param  t three points used
	 * @return   4th point
	 */
	public Vec3 getFourthPoint(Triangle t)
	{
		Vec3 retVal = null;

		for (Vec3 p : pointList)
		{
			if (!t.doesPointExist(p))
			{
				retVal = p;
				break;
			}
		}

		if (retVal == null)
		{
			System.out.println("");
			System.out.println(" ----- Breaking on: ----- ");
			System.out.println(pointList);
			System.out.println(t);
			System.out.println(" -------------------- ");
			System.out.println("");

			assert(false);
		}

		return retVal;
	}

	private static boolean areSignsSame(double a, double b)
	{
		boolean retVal = false;

		if (a > 0 && b > 0)
		{
			retVal = true;
		}
		else if (a < 0 && b < 0)
		{
			retVal = true;
		}

		return retVal;
	}

	private boolean checkCollisionOfLine(Vec3 a, Vec3 b, ArrayList<Tetrahedron> list)
	{
		// Check if the resulting tetrahedron isnt conflicting with another
		
		boolean retVal = false;

		Vec3 midpoint = Vec3.midpoint(a, b);

		if (doesPointCollideWithStructure(midpoint, list))
		{
			retVal = true;
			return retVal;
		}

		return retVal;
	}

	private boolean doesPointExist(Vec3 point)
	{
		return pointList.contains(point);
	}

	public static boolean test()
	{
		// TODO: Finish this test
		Tetrahedron t = new Tetrahedron(new Triangle(new Vec3(0.00f, 0.00f, 0.00f), new Vec3(3.00f, 5.196f, 0.00f), new Vec3(6.00f, 0.00f, 0.00f)));

		ArrayList<Tetrahedron> list = new ArrayList<Tetrahedron>();

		list.add(t);

		Vec3 a = new Vec3();
		Vec3 b = new Vec3();

		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder();

		s.append("Tetrahedron\n");

		s.append(pointList.toString());

		return s.toString();
	}

}