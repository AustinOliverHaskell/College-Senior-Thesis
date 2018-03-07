public class Vec3
{
	public float x;
	public float y;
	public float z;

	public Vec3(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Default constructor
	 * @return Vector with all values set to 0.0f
	 */
	public Vec3()
	{
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
	}

	/**
	 * Subtracts this vector from other, ie this - other (In that order)
	 * @param  other vector to stubtract from the current one
	 * @return       result of the calculation
	 */
	public Vec3 sub(Vec3 other)
	{
		return new Vec3(this.x - other.x, this.y - other.y, this.z - other.z);
	}

	/**
	 * Adds this vector to the one supplied, this is associative so order really
	 *  doesnt matter
	 * @param  other vector to add to the current one
	 * @return       result of the calculation
	 */
	public Vec3 add(Vec3 other)
	{
		return new Vec3(this.x + other.x, this.y + other.y, this.z + other.z);
	}

	/**
	 * Calculates the distance between these two points
	 * @param  one first point
	 * @param  two second point
	 * @return     distance between one and two
	 */
	public static double dist(Vec3 one, Vec3 two)
	{
		double intermediate = Math.pow(two.x - one.x, 2);
		intermediate += Math.pow(two.y - one.y, 2);
		intermediate += Math.pow(two.z - one.z, 2);

		return Math.sqrt(intermediate);
	}

	/**
	 * Finds the midpont between two other verticies
	 * @param  one First point
	 * @param  two Second point
	 * @return     the point that lies inbetween the two supplied verticies
	 */
	public static Vec3 midpoint(Vec3 one, Vec3 two)
	{
		Vec3 retVal = new Vec3();

		retVal.x = (one.x + two.x) / 2.0f;
		retVal.y = (one.y + two.y) / 2.0f;
		retVal.z = (one.z + two.z) / 2.0f;

		return retVal;
	}

	/**
	 * Calculates the cross product of the given verticies
	 * @param  one first vertex
	 * @param  two second vertex
	 * @return     the dot product
	 */
	public static Vec3 cross(Vec3 one, Vec3 two)
	{
		Vec3 retVal = new Vec3();

		retVal.x = one.y * two.z - one.z * two.y;
		retVal.y = one.z * two.x - one.x * two.z;
		retVal.z = one.x * two.y - one.y * two.x;

		return retVal;
	}

	/**
	 * Finds the endpoint of a line given the startpoint, the radius, the angle
	 *  from the x axis, and the angle on the z axis
	 *  <br/>
	 *  NOTE: This method interprets the second vector as (Distance, Angle on X, Angle on Z)
	 *  <br />
	 *  NOTE: The angles given are presumed to be in radians
	 * @param  startPoint Starting point of the line segment
	 * @param  directions Direction vector (Distance, Angle on X, Angle on Z)
	 * @return            The endpoint of the line
	 */
	public static Vec3 findEndpoint(Vec3 startPoint, Vec3 directions)
	{
		Vec3 retVal = new Vec3();

		retVal.x = (float)(Math.cos(directions.z) * directions.x);
		retVal.y = (float)(Math.sin(directions.z) * directions.x);
		retVal.z = (float)(Math.cos(directions.y) * Math.cos(directions.y) * directions.x);

		return retVal;
	}

	/**
	 * Finds the next point along a line based off of two other points and the distance from 
	 *  the endpoint
	 * @param  startPoint the begining of the line
	 * @param  endPoint   end of the line
	 * @param  distance   the distance away from endpoint to calculate the line
	 * @return            the point that is distance away from endPoint, this is known
	 *                     as a t vector in mathmatics. 
	 */
	public static Vec3 pointAlongLine(Vec3 startPoint, Vec3 endPoint, double distance)
	{
		Vec3 retVal = new Vec3();

		// Find the formula for the line, get the subtraction of the two ponts
		Vec3 vec = startPoint.sub(endPoint);

		retVal.x = vec.x * (float)distance;
		retVal.y = vec.y * (float)distance;
		retVal.z = vec.z * (float)distance;

		retVal = retVal.add(startPoint);

		return retVal;
	}

	/**
	 * Normalizes the given vector to some value between -1 and 1
	 * <br />
	 * NOTE: This function will never return NaN, if the output would
	 *  be NaN then instead it outputs a 0 vector.
	 */
	public void normalize()
	{
		// TODO: Implement normalization
		float norm = calcNorm();

		// Make sure that its okay to do these divisions
		if (norm != 0 && norm != Float.NaN)
		{
			this.x = this.x/norm;
			this.y = this.y/norm;
			this.z = this.z/norm;
		}

		// If its NaN or the norm is 0 then do nothing
	}

	// Calculates the norm for use in the normalize function
	private float calcNorm()
	{
		// Gotta make sure that its not negative
		float retVal = this.x * this.x + this.y * this.y + this.z * this.z;

		// Dont want an irrational number, (I think the sqrt method would return
		//  NaN anyways)
		if (retVal < 0)
		{
			retVal = Float.NaN;
			return retVal;
		}

		retVal = (float)Math.sqrt(retVal);

		return retVal;
	}

	@Override
	public String toString()
	{
		StringBuilder retVal = new StringBuilder();

		retVal.append(Float.toString(this.x));
		retVal.append(" ");
		retVal.append(Float.toString(this.y));
		retVal.append(" ");
		retVal.append(Float.toString(this.z));

		return retVal.toString();
	}

	@Override
	public boolean equals(Object obj)
	{
		Vec3 other = (Vec3)obj;

		boolean retVal = false;

		if (this.x == other.x && this.y == other.y && this.z == other.z)
		{
			retVal = true;
		}

		return retVal;
	}

	@Override
	public int hashCode()
	{
		return (int) (x + y + z);
	}
}