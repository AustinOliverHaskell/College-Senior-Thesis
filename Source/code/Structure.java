package austin.structures;

import java.util.*;

public class Structure implements Comparable<Structure>, Runnable
{
	private int tetraCount;
	private float mutationRate;
	private int fitness;
	private String name;
	private String CREATION_MODE = "LINEAR";
	private int collisionCount = 0;
	private int breakCount = 0;

	private ArrayList<Tetrahedron> tetraList;
	private ArrayList<Integer> dnaList;

	/**
	 *  Deafult constructor sets values at <br />
	 *
	 * 	Tetrahedron count -> 10
	 * 	Mutation Rate -> 1%
	 */
	Structure()
	{
		// Deafult Values
		tetraCount = 100;
		mutationRate = 0.01f;
		fitness = Integer.MAX_VALUE;
		name = "";
		tetraList = new ArrayList<Tetrahedron>();
		collisionCount = 1;

		Random rand = new Random();

		//System.out.println("Tetrahedron Count: " + tetraCount);

		tetraList.add(makeFirstTetra());
		//System.out.println(tetraList.get(0));
		//System.out.println("Compleated tetrahedron seed!");

		int tetraToBuildOffOf;

		if (CREATION_MODE.equals("LINEAR"))
		{
			tetraToBuildOffOf = tetraList.size() - 1;
		}
		else
		{
			tetraToBuildOffOf = rand.nextInt(tetraList.size());
		}

		for (int i = 0; i < tetraCount - 1; i++)
		{

			
			Tetrahedron t;
			try
			{
				t = new Tetrahedron(tetraList.get(tetraToBuildOffOf), tetraList);

				tetraList.add(t);

				//System.out.println(t);
				
				if (CREATION_MODE.equals("LINEAR"))
				{
					tetraToBuildOffOf = tetraList.size() - 1;
				}
				else
				{
					tetraToBuildOffOf = rand.nextInt(tetraList.size());
				}
			}
			catch(NoValidSpaces err)
			{
				i--;

				if (CREATION_MODE.equals("LINEAR"))
				{
					tetraToBuildOffOf -= 1;
				}

				continue;
			}
		}
	}

	/**
	 *This constructor will create this struct object from a file,
	 * this just calls the underying load functon. 
	 * 
	 * 	@param path path to the file to be 
	 */
	Structure(String path)
	{
		load(path);
	}

	/**
	 *  Recombination Constructor
	 *  @param mom first Structure to combine
	 *  @param dad Second Structure to combine
	 *  @param method What type of combination to use (ASEXUAL, ALTERNATING, etc)
	 */
	Structure(Structure mom, Structure dad, String method)
	{
		fitness = Integer.MAX_VALUE;
		name = "";

		// Just copies mom with a high chance of mutation
		if (method.equals("ASEXUAL"))
		{
			
		}
		else if (method.equals("QUARTERS"))
		{

		}
	}

	public static Structure breed(Structure mom, Structure dad, String method)
	{
		Structure retVal = null;

		ArrayList<Integer> m = mom.getDNA();
		ArrayList<Integer> d = dad.getDNA();

		ArrayList<Integer> offspring = new ArrayList<Integer>();

		offspring.ensureCapacity(m.size());

		// Just copies mom with a high chance of mutation
		if (method.equals("ASEXUAL"))
		{
			
		}
		else if (method.equals("QUARTERS"))
		{
			int i = 0;
			for (; i < m.size() / 4; i++)
			{
				offspring.add(m.get(i));
			}

			for (; i < (int)((float)d.size() * 3.0f/4.0f); i++)
			{
				offspring.add(d.get(i));
			}

			for (; i < m.size(); i++)
			{
				offspring.add(m.get(i));
			}
		}

		retVal = new Structure(offspring);

		return retVal;
	}

	/**
	*	This constructor is to facilitate a new way of generating, 
	*	 mutating, and mating structures. In this constructor a list
	*	 of numbers from 0-2 is taken and that data is used to cover
	*	 the corresponding faces of the previous. So for example from
	* 	 the first tetrahedron, if the first number is 0 then the 0 
	*	 face of the first tetrahedron is then used to create the next.
	*	 Think of it like budding, one tetrahedron buds off of a 
	*	 specific face of the last. (I hope that makes sense). This
	*	 (potentially) will simplify the genetic material. 
	*/
	Structure(ArrayList<Integer> dnaList)
	{
		this.dnaList = dnaList;
		tetraList = new ArrayList<Tetrahedron>();
		// Create the first tetrahedron
		tetraList.add(makeFirstTetra());

		name = "";
		fitness = Integer.MAX_VALUE;
		this.collisionCount = 1;

		// The first tetrahedron wont have any faces covered. 
		//  So the first item in the dna list can be between
		//  0-3
		for (int i = 0; i < dnaList.size(); i++)
		{
			Tetrahedron t = new Tetrahedron(tetraList.get(tetraList.size()-1), dnaList.get(i));

			if (Tetrahedron.doesTetraCollideWithList(t, tetraList))
			{
				this.collisionCount++;
			}

			tetraList.add(t);
		}

	}

	public int getCollisionCount()
	{
		return this.collisionCount;
	}

	public void load(String path)
	{
		// TODO: Finish loading from a file
	}

	public int getFitness()
	{
		return fitness;
	}

	public void setFitness(int fitness)
	{
		this.fitness = fitness;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public double getBoundingVolume()
	{
		double retVal = 0;

		float minX = Float.MAX_VALUE;
		float maxX = Float.MIN_VALUE;

		float minY = Float.MAX_VALUE;
		float maxY = Float.MIN_VALUE;

		float minZ = Float.MAX_VALUE;
		float maxZ = Float.MIN_VALUE;

		for (Tetrahedron t : tetraList)
		{
			ArrayList<Vec3> points = t.getPointList();
		
			for (Vec3 v : points)
			{
				minX = minCheck(minX, v.x);
				maxX = maxCheck(minX, v.x);

				minY = minCheck(minY, v.y);
				maxY = maxCheck(minY, v.y);

				minZ = minCheck(minZ, v.z);
				maxZ = maxCheck(minZ, v.z);
			}
		}

		float x = maxX - minX;
		float y = maxY - minY;
		float z = maxZ - minZ;

		retVal = (double)x * (double)y * (double)z;

		//System.out.println("MinX:" + Float.toString(minX));
		//System.out.println("MaxX:" + Float.toString(maxX));
		//System.out.println("MinY:" + Float.toString(minY));
		//System.out.println("MaxY:" + Float.toString(maxY));
		//System.out.println("MinZ:" + Float.toString(minZ));
		//System.out.println("MaxZ:" + Float.toString(maxZ));


		return retVal;
	}

	private Tetrahedron makeFirstTetra()
	{
		Triangle tri = new Triangle(new Vec3(0.00f, 0.00f, 0.00f), new Vec3(1.00f, 0.00f, 0.00f), new Vec3(0.50f, 0.86f, 0.00f));

		tri.setCovered(true);

		return new Tetrahedron(tri);
	}

	/**
	 * Saves the structure as a .obj file
	 * 
	 * @param path path AND filename to save. IE: "./obj/test.obj"
	 */
	public void save(String path)
	{
		ObjFileBuilder obj = new ObjFileBuilder();

		for (Tetrahedron t : tetraList)
		{
			obj.add(t);
		}

		obj.save(path);
	}

	public int compareTo(Structure structure)
	{ 
		
		//ascending order
		return this.fitness - structure.getFitness();
		
		//descending order
		//return compareQuantity - this.quantity;
	}

	private ArrayList<Integer> getDNA()
	{
		ArrayList <Integer> retVal = new ArrayList<Integer>();

		for (Integer t : dnaList)
		{
			retVal.add(t);
		}

		return retVal;
	}

	public void mutate(boolean flip)
	{
		Random rand = new Random();

		// Heads
		// Add Tetrahedron
		if (flip)
		{
			int pos = rand.nextInt(dnaList.size());

			int newVal = dnaList.get(pos);
			newVal++;

			if (newVal >= 3)
			{
				newVal = 0;
			}

			dnaList.set(pos, newVal);
		}

		// Tails
		// Remove Tetrahedron
		else
		{
			
		}

	}

	public void mutate()
	{
		Random rand = new Random();

		boolean flip = rand.nextBoolean();

		mutate(flip);
	}

	public void saveSteps(String path, int num)
	{
		ObjFileBuilder obj = new ObjFileBuilder();

		int i = 0;

		for (Tetrahedron t : tetraList)
		{
			if (i >= num)
			{
				break;
			}

			obj.add(t);

			obj.save(path + "CreationStep_" + Integer.toString(i) + ".obj");

			i++;
		}
	}

	public void run()
	{
		String printCode = "\u001B[38;5;9m";

		this.breakCount = Tribe.staticEvaluate(this.name + ".obj");

		calcFitness();

		if (this.fitness < 15)
		{
			printCode = "\u001B[38;5;76m";
		}
		else if (this.fitness < 40)
		{
			printCode = "\u001B[38;5;226m";
		}

		System.out.println(printCode + this.name + " compleated evaluation: " + Integer.toString(this.fitness));
	}

	private void calcFitness()
	{
		double volume = getBoundingVolume();

		
	}

	private float minCheck(float old, float check)
	{
		if (old < check)
		{
			return old;
		}
		else
		{
			return check;
		}
	}

	private float maxCheck(float old, float check)
	{
		if (old > check)
		{
			return old;
		}
		else
		{
			return check;
		}
	}

}