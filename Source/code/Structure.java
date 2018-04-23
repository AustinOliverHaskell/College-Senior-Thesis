package austin.structures;

import java.util.*;

public class Structure implements Comparable<Structure>
{
	private int tetraCount;
	private float mutationRate;
	private int fitness;
	private String name;
	private String CREATION_MODE = "LINEAR";

	private ArrayList<Tetrahedron> tetraList;

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
			tetraList = mom.getDNA();

			// Needs to be a random chance
			mutate();
		}
		else if (method.equals("QUARTERS"))
		{
			ArrayList <Tetrahedron> momList = mom.getDNA();
			ArrayList <Tetrahedron> dadList = dad.getDNA();

			tetraList = new ArrayList<Tetrahedron>();

			int i = 0;

			for (; i < momList.size()/4; i++)
			{
				tetraList.add(momList.get(i));
			}

			for (; i < dadList.size() * (3/4); i++)
			{
				tetraList.add(dadList.get(i));
			}

			for (; i < momList.size(); i++)
			{
				tetraList.add(momList.get(i));
			}

			tetraCount = tetraList.size();
		}
		else if (method.equals("NO_COLLIDE_QUARTERS"))
		{
			tetraList = new ArrayList<Tetrahedron>();

			ArrayList <Tetrahedron> momList = mom.getDNA();
			ArrayList <Tetrahedron> dadList = dad.getDNA();

			int i = 0;

			for (; i < momList.size()/4; i++)
			{
				tetraList.add(momList.get(i));
			}

			for (; i < dadList.size() * (3/4); i++)
			{
				Tetrahedron t = dadList.get(i);

				if (Tetrahedron.doesTetraCollideWithList(t, tetraList))
				{
					continue;
				}

				tetraList.add(t);
			}

			for (; i < momList.size(); i++)
			{
				Tetrahedron t = momList.get(i);

				if (Tetrahedron.doesTetraCollideWithList(t, tetraList))
				{
					continue;
				}

				tetraList.add(t);
			}

			tetraCount = tetraList.size();

			mutate();
		}
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

	private Tetrahedron makeFirstTetra()
	{
		Triangle tri = new Triangle(new Vec3(0.00f, 0.00f, 0.00f), new Vec3(1.00f, 0.00f, 0.00f), new Vec3(0.50f, 0.86f, 0.00f));

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

	private ArrayList<Tetrahedron> getDNA()
	{
		ArrayList <Tetrahedron> retVal = new ArrayList<Tetrahedron>();

		for (Tetrahedron t : tetraList)
		{
			retVal.add(new Tetrahedron(t));
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
			boolean succes = false;

			while (succes == false)
			{
				int tetraToBuildOffOf = rand.nextInt(tetraList.size());
				Tetrahedron t;

				try
				{
					t = new Tetrahedron(tetraList.get(tetraToBuildOffOf), tetraList);

					tetraList.add(t);

					succes = true;
				}
				catch (NoValidSpaces error)
				{
					// Do nothing
				}
			}

		}

		// Tails
		// Remove Tetrahedron
		else
		{
			int tetToRemove = rand.nextInt(tetraList.size());

			tetraList.remove(tetToRemove);
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

}