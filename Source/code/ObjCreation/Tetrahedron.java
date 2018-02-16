import java.util.*;
import Jama.*;

public class Tetrahedron
{
	private ArrayList<Triangle> faceList;

	/**
	 * 	Create a Tetrahedron with all four required triangles
	 */
	Tetrahedron(Triangle a, Triangle b, Triangle c, Triangle d)
	{
		faceList = new ArrayList<Triangle>();

		faceList.add(a);
		faceList.add(b);
		faceList.add(c);
		faceList.add(d);
	}

	/**
	 *  This one is the special constructor, given one triangle this constructor
	 *   will create the other triangles from a point generated to be equadistant
	 *   from the verticies of the supplied triangle
	 */
	Tetrahedron(Triangle a)
	{
		faceList = new ArrayList<Triangle>();

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

		point.z += 5;

		// Create the other triangles
		Triangle b = new Triangle(a.a, a.b, point);
		Triangle c = new Triangle(a.a, a.c, point);
		Triangle d = new Triangle(a.b, a.c, point);

		// Mark a as covered
		a.setCovered(true);

		// Add the triangles to the facelist
		faceList.add(a);
		faceList.add(b);
		faceList.add(c);
		faceList.add(d);
		
		// Done!
	}

	Tetrahedron(Tetrahedron tetrahedron, ArrayList<Tetrahedron> list) throws NoValidSpaces
	{
		faceList = new ArrayList<Triangle>();

		Vec3 point = new Vec3();
		Triangle a;

		int attempts = 0;

		do 
		{
			// Its random, so it can pick the same faces over and over again, so this number is high
			//  to ensure that all faces have been checked
			// MAGIC NUMBER
			// TODO: Figure out a way to make this much much more elegant
			if (attempts > 100)
			{
				throw new NoValidSpaces();
			}

			attempts++;

			Random rand = new Random();

			a = tetrahedron.getFace(rand.nextInt(3));

			// First figure out how long a single side of the triangle is
			double distance = Vec3.dist(a.a, a.b);

			// The missing point to complete the tetrahedron
			point = new Vec3();

			point = Vec3.pointAlongLine(a.getCentroid(), getFourthPoint(a, tetrahedron) , 1);

			// Make sure that we arnt covering up a face thats already being used
			if (a.isCovered())
			{
				continue;
			}

			if (checkCollisionOfLine(a.a, point, list) || checkCollisionOfLine(a.b, point, list) 
				|| checkCollisionOfLine(a.c, point, list) || checkCollisionOfLine(getFourthPoint(a, tetrahedron), point,  list) 
				|| checkCollisionOfLine(a.getCentroid(), point, list))
			{
				continue;
			}

			Vec3 midpont = Vec3.pointAlongLine(a.getCentroid(), getFourthPoint(a, tetrahedron) , 0.5);

			if (isPointContained(midpont, list))
			{
				continue;
			}




		}
		while(!isPointContained(point, list));
		// Currently the vertex point needs to be translated x units away perpendicular to 
		//  the plane of the triangle. Im not sure how this is done. 

		// Create the other triangles
		Triangle b = new Triangle(a.a, a.b, point);
		Triangle c = new Triangle(a.a, a.c, point);
		Triangle d = new Triangle(a.b, a.c, point);

		// Mark a as covered
		a.setCovered(true);

		// Add the triangles to the facelist
		faceList.add(a);
		faceList.add(b);
		faceList.add(c);
		faceList.add(d);
	}

	/**
	 *  Default Constructor, All four triangles use the default constructor
	 */
	Tetrahedron()
	{
		faceList = new ArrayList<Triangle>();

		// Could be a loop but I dont think thats important
		faceList.add(new Triangle());
		faceList.add(new Triangle());
		faceList.add(new Triangle());
		faceList.add(new Triangle());
	}

	/**
	 * Method that determines wheather a given point is within the bounds
	 *  of any of the tetrahedrons in the ArrayList list
	 *  
	 *  NOTE: If the point is coplanar it is considered within the bounds
	 * @param point point to check against this shape
	 * @return             true if point is on or within this tetrahedron
	 */
	public static boolean isPointContained(Vec3 point, ArrayList<Tetrahedron> list)
	{
		// TODO: Finish this, check the bookmark on determantants
		boolean retVal = true;

		Matrix D0 = new Matrix(4, 4);
		Matrix D1 = new Matrix(4, 4);
		Matrix D2 = new Matrix(4, 4);
		Matrix D3 = new Matrix(4, 4);
		Matrix D4 = new Matrix(4, 4);

		for (Tetrahedron t : list)
		{

			Triangle tri = t.getFace(0);

			boolean inThisTet = true;
			// Get all of the points to make this quicker
			Vec3 a = tri.a;
			Vec3 b = tri.b;
			Vec3 c = tri.c;
			Vec3 d = getFourthPoint(tri, t);

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

			for (int i = 1; i < 5; i++)
			{
				// If one of them has a differing sign then the point isnt fully in the triangle
				if (!areSignsSame(determantants.get(0), determantants.get(i)) && determantants.get(i) != 0)
				{
					inThisTet = false;
				}
			}

			if (inThisTet)
			{
				retVal = false;
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
	public static Vec3 getFourthPoint(Triangle t, Tetrahedron tet)
	{
		for (int i = 0; i < 4; i++)
		{
			Triangle tri = tet.getFace(i);

			if (t.equals(tri))
			{
				continue;
			}
			else
			{
				if (!t.doesTriangleHaveVertex(tri.a))
				{
					return tri.a;
				}
				else if (!t.doesTriangleHaveVertex(tri.b))
				{
					return tri.b;
				}
				else if (!t.doesTriangleHaveVertex(tri.c))
				{
					return tri.c;
				}
			}
		}

		return null;
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
		for (float ap = 0.0f; ap < 1.0f; ap += 0.5f)
		{
			Vec3 pointOnLine = Vec3.pointAlongLine(a, b, 1.0f-ap);

			System.out.println("Checking line of : " + a + " " + b);
			System.out.println(pointOnLine);

			if (isPointContained(pointOnLine, list))
			{
				return true;
			}
		}

		return false;
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

}