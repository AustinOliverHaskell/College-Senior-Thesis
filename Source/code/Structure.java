package austin.structures;

import java.util.*;

public class Structure
{
	private int tetraCount;
	private float mutationRate;

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
		tetraCount = 200;
		mutationRate = 0.01f;
		tetraList = new ArrayList<Tetrahedron>();

		System.out.println("Tetrahedron Count: " + tetraCount);

		tetraList.add(makeFirstTetra());
		System.out.println(tetraList.get(0));
		System.out.println("Compleated tetrahedron seed!");

		for (int i = 0; i < tetraCount - 1; i++)
		{

			Random rand = new Random();
			int tetraToBuildOffOf = rand.nextInt(tetraList.size());

			Tetrahedron t;
			try
			{
				t = new Tetrahedron(tetraList.get(tetraToBuildOffOf), tetraList);

				tetraList.add(t);

				System.out.println(t);

			}
			catch(NoValidSpaces err)
			{
				i--;
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

	public void load(String path)
	{
		// TODO: Finish loading from a file
	}

	public void mutate()
	{

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



}