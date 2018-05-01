package austin.structures;

import java.util.*;
import java.io.*;
import java.awt.Color;

public class Driver
{
	public static void main(String[] args)
	{
		Structure a = new Structure();

		Structure b = new Structure();

		Structure c = new Structure(a, b, "NO_COLLIDE_QUARTERS");

		c.save("../compiled/obj/Test.obj");


		System.out.println("Creating initial population ... ");

		int tribeSizes = 500;
		int generationCount = 40;
		
		Tribe orange = new Tribe("ASEXUAL", tribeSizes);
		orange.setColor(Color.ORANGE);

		for (int i = 0; i < generationCount; i++)
		{
			orange.evalTribe();

			orange.saveBest(i+1);

			orange.print();

			orange.combineAndMutate();
		}

		/*
		orange.setPrintCode("\u001B[38;5;166m");

		for (int i = 0; i < generationCount; i++)
		{
			System.out.println("Generation: " + Integer.toString(i));

			orange.evalTribe();

			orange.print();


			Structure best = orange.getBestMemeber();
			System.out.println("Best Member: " + best.getName() + " --> " + Integer.toString(best.getFitness()));

			orange.saveBest(i);

			Thread display = new Thread(new Runnable()
			{
				public void run() {
					orange.showBest();
				}
			});
			display.setDaemon(true);

			display.start();

			best = blue.getBestMemeber();
			System.out.println("Best Member: " + best.getName() + " --> " + Integer.toString(best.getFitness()));
			blue.showBest();

			best = green.getBestMemeber();
			System.out.println("Best Member: " + best.getName() + " --> " + Integer.toString(best.getFitness()));
			green.showBest();

			System.out.println(" ----- ");

			System.out.println("Creating next generation ... ");

			green.combineAndMutate();
			blue.combineAndMutate();
			orange.combineAndMutate();
		}

		System.out.println("Best member after " + Integer.toString(generationCount) + " generations ... ");
		orange.showBest();
	*/
	}


	
}