package austin.structures;

public class Structure
{

	private Dna dna;
	private int fitness;
	private final float mutationRate = (float)0.02;
	private final int   dnaSize = 10;

	Structure()
	{
		dna = new Dna(mutationRate, dnaSize);
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
	// -----------------------------------------


}
