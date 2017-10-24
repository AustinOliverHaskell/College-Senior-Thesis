public class Driver
{
	public static void main(String[] args)
	{
		System.out.println(" - Thesis - ");

		//Structure structure = new Structure();

		Dna dna = new Dna((float)0.02, 100);
		dna.save("./img");
	}
}