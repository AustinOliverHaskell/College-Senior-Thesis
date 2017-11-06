package austin.structures;

public class Driver
{
	public static int     populationSize = 10;

	public static void main(String[] args)
	{
		if (args.length == 1)
		{
			populationSize = Integer.parseInt(args[0]);
		}
		else if (args.length == 2)
		{
			populationSize = Integer.parseInt(args[0]);
			if (Integer.parseInt(args[1]) == 1)
			{
				Debug.enable();
			}
		}

		System.out.println(" - Thesis - ");

		//Dna dna = new Dna((float)0.02, populationSize);

		//dna.save("../compiled/img");

		TestingSuite.runAllTests();

		Debug.save("../compiled/");


	}
}