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

		GUI gui = new GUI(numberOfCycles * populationSize);

		gui.showGUI();

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
			Structure structure = new Structure();

			evaluator.setStructure(structure);

			gui.increment();

			evaluator.evaluate();


			structure.save("../compiled/obj/");

		}

		Debug.save("../compiled/");


	}
}