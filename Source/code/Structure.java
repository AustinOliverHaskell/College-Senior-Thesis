package austin.structures;

import java.util.*;

public class Structure implements Member
{

	private Dna dna;
	private int fitness;
	private final float mutationRate = (float)0.02;
	private final int   dnaSize = 10;
	private String idHash;

	// Should be big enough to avoid collisions
	private final int HASH_LENGTH = 25;

	Structure()
	{
		dna = new Dna(mutationRate, dnaSize);
		this.idHash = createHashId();
	}

	/**
	*	This constructor creates a structure object from a file rather than
	*    from thin air. Used to get results from last generation. 
	*
	*	@param the name of the file to create this structure from
	*/
	Structure(String filename)
	{
		// TODO: Finish structure loading from file
	}

	// ---------- Getters and Setters ----------
	public int getFitness()
	{
		return fitness;
	}

	public void setFitness(int fitness)
	{
		this.fitness = fitness;
	}

	public String getId()
	{
		return this.idHash;
	}

	public void setId(String idHash)
	{
		this.idHash = idHash;
	}

	public Dna getDna()
	{
		return this.dna;
	}

	public void setDna(Dna dna)
	{
		this.dna = dna;
	}

	private String createHashId()
	{
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < HASH_LENGTH; i++)
		{
			str.append(getRandomChar());
		}

		return str.toString().replaceAll("[:;<=>?@]", "_");
	}

	private char getRandomChar()
	{
		Random rand = new Random();

		int charNum = rand.nextInt(42);

		char retVal = (char)(charNum + 48);

		return retVal;		
	}
	// -----------------------------------------


	// ---------- Evolution Functions ----------
	public void mutate()
	{
		dna.mutate();
	}
	// -----------------------------------------

	// ---------- Obj functions ----------
	public void save(String path)
	{
		ObjFileBuilder obj = new ObjFileBuilder(this);

		ArrayList <Layer> dnaLayers = this.dna.getLayers();

		for (Layer layer : dnaLayers)
		{
			obj.add(layer);
		}

		obj.save(path, this.idHash);
	}
	// -----------------------------------

	@Override
	public String toString()
	{
		return null;
	}

}
