package austin.structures;

import java.util.*;

public class Driver
{
	public static int populationSize  = 10;
	public static int numberOfCycles  = 100;
	public static float mutationRate  = 0.02f;
	public static String graphicsMode = "Verbose";

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
		System.out.println("");
		System.out.println("-> Population Size: " + Integer.toString(populationSize));
		System.out.println("-> Number of Cycles: " + Integer.toString(numberOfCycles));
		System.out.println("-> Mutation Rate: " + Float.toString(mutationRate));
		System.out.println("-> Debugging information: " + ((Debug.isEnabled()) ? "Enabled" : "Disabled"));
		System.out.println("-> Graphics Mode: " + graphicsMode);
		System.out.println("");



		if (Debug.isEnabled())
		{
			Debug.logs("Running Test Suite...");
			TestingSuite.runAllTests();
			Debug.logs("Testing suite complete!");
			Debug.logs("");
		}

		System.out.println("Simulation starting...");
		Evaluator evaluator = new Evaluator();

		// Run full simulation for population
		for (int i = 0; i < populationSize; i++)
		{
			System.out.print("\r" + blankBar(120) + "\r");

			Structure structure = new Structure();

			evaluator.setStructure(structure);

			System.out.println("Processing " + structure.getId());
			System.out.print(createBar(i+1, populationSize, 100));

			evaluator.evaluate();

			structure.save("../compiled/obj/");

		}

		System.out.print("\r" + createBar(populationSize, populationSize, 100));



		Debug.save("../compiled/");


	}


	private static String createBar(int current, int total, int barLength)
	{
		StringBuilder str = new StringBuilder();

		float percent = ((float)current/(float)total);

		int numBars = (int)((float)barLength*percent);

		str.append("[");
		for (int i = 0; i < numBars; i++)
		{
			str.append("#");
		}

		for (int i = numBars; i < barLength; i++)
		{
			str.append("-");
		}

		str.append("] -> ");

		str.append((int)(percent*100f));
		str.append("%");

		str.append(" ");
		str.append(current);
		str.append("/");
		str.append(total);

		return str.toString();
	}

	private static String blankBar(int size)
	{
		StringBuilder retVal = new StringBuilder();

		for (int i = 0; i < size; i++)
		{
			retVal.append(" ");
		}

		return retVal.toString();
	}
}