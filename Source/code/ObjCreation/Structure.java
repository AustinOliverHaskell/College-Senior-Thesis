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
		tetraCount = 5;
		mutationRate = 0.01f;
		tetraList = new ArrayList<Tetrahedron>();

		tetraList.add(makeFirstTetra());

		for (int i = 0, q = 0; i < tetraCount - 1; i++, q++)
		{
			Random rand = new Random();
			int tetraToBuildOffOf = rand.nextInt(tetraList.size());

			System.out.println(Integer.toString(q));

			if (q > 1000)
			{
				System.out.println("Exausted list or Infinite Loop...");
				break;
			}

			Tetrahedron t;
			try
			{
				t = new Tetrahedron(tetraList.get(tetraToBuildOffOf), tetraList);

				tetraList.add(t);
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
		return new Tetrahedron(new Triangle(new Vec3(0.00f, 0.00f, 0.00f), new Vec3(3.00f, 5.196f, 0.00f), new Vec3(6.00f, 0.00f, 0.00f)));
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