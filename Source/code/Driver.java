package austin.structures;

import java.util.*;
import java.util.concurrent.*;

public class Driver extends Thread
{
	// Default Values
	public static int populationSize  = 1000;
	public static int numberOfCycles  = 100;
	public static float mutationRate  = 0.02f;

	// How many structures to keep in memory, the higher this number
	//  the larger the memory consumption but the lower the disk usage
	//  going to have to find a good balance with this one
	public static int MAX_IN_MEMORY = 100;
	public static String graphicsMode = "Concise"; //"Verbose";

	public static ConcurrentLinkedQueue<Structure> workQueue;

	private static int threadCount = 4;

	public static void main(String[] args)
	{
		ArrayList<Driver> threadList = new ArrayList<Driver>();
		workQueue = new ConcurrentLinkedQueue<Structure>();

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

		//gui.showGUI();

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
		//Evaluator evaluator = new Evaluator();


		// Inner Class to make another thread that just creates the inital set of
		//  buildings. This needs to be a new thread so that not all of the buildings
		//  at the same time. Eventually this will need to read in the structures from
		//  the file. But I might make that another seperate thread
		Thread builderThread = new Thread() {
			public void run()
			{
				// TODO: Get rid of these magic numbers
				int remaining = populationSize;

				while(remaining > 0)
				{
					// Only keep MAX_IN_MEMORY count 
					if (workQueue.size() < MAX_IN_MEMORY)
					{
						Structure newStructure = new Structure();
						workQueue.add(newStructure);
						remaining--;
					}
				}
			}
		};

		builderThread.start();

		// Give the builderThread 100ms head start, creating the structures is
		//  VERY quick in comparison to the evaluation. 
		try
		{
			// Simulate the simulation
			Thread.sleep(100);
		}
		catch (InterruptedException error)
		{
			error.printStackTrace();
		}

		System.out.println("Memory used: ");
		// The 1,000,000 is to covert the Bytes into MegaBytes
		System.out.println(Runtime.getRuntime().totalMemory()/1000000 + " MB");

		for (int i = 0; i < threadCount; i++)
		{
			Driver d = new Driver();
			threadList.add(d);
			d.start();  	
		}
		
		boolean threadsAlive = false;

		// Make sure that the main thread doesnt exit
		do 
		{
			threadsAlive = false;

			for (int i = 0; i < threadList.size(); i++)
			{
				if (threadList.get(i).isAlive())
				{
					threadsAlive = true;
				}
			}
		}
		while(threadsAlive);

		Debug.save("../compiled/");

		System.exit(0);
	}
	// TODO: If graphics mode is concise, show the best member of that run


	public void run()
	{
		int count = 0;

		// Evaluation code here
		//  this method will be called the same number of threads
		//  as threadCount. This will make that meany thread in a 
		//  "Pool". 
		while (!workQueue.isEmpty())
		{
			Structure s = workQueue.poll();
			Evaluator eval = new Evaluator(s);

			System.out.println("Working on: " + s.getId());
			//s.save("../compiled/obj/");
			count++;

			// TODO: Uncomment this when the openGL stuffs works
			// eval.evaluate();

			try
			{
				// Simulate the simulation
				Thread.sleep(500);
			}
			catch (InterruptedException error)
			{
				error.printStackTrace();
			}
		}
 
		System.out.println(" #" + this.getId() + " thread processed " + count + " items.");
	}
}