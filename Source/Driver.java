public class Driver
{
	public static void main(String[] args)
	{
		int populationSize = Integer.parseInt(args[0]);

		System.out.println(" - Thesis - ");

		//Structure structure = new Structure();

		Dna dna = new Dna((float)0.02, populationSize);
		dna.purge(20);
		dna.save("./img");
	}
}