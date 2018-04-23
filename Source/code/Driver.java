package austin.structures;

import java.util.*;
import java.io.*;
import java.awt.Color;

public class Driver
{
	public static void main(String[] args)
	{
		System.out.println("Creating initial population ... ");

		int tribeSizes = 100;
		int generationCount = 20;
		
		Tribe orange = new Tribe("OrangeBois", tribeSizes);
		orange.setColor(Color.ORANGE);

		/*Tribe blue = new Tribe("BlueBois", tribeSizes);
		blue.setColor(Color.BLUE);

		Tribe green = new Tribe("GreenBois", tribeSizes);
		green.setColor(Color.GREEN);*/

		orange.setPrintCode("\u001B[38;5;166m");
		System.out.println("Evaluating Orange Tribe");

		for (int i = 0; i < generationCount; i++)
		{
			System.out.println("Generation: " + Integer.toString(i));

			orange.evalTribe();
			Structure best = orange.getBestMemeber();
			System.out.println("Best Member: " + best.getName() + " --> " + Integer.toString(best.getFitness()));
			orange.showBest();

			System.out.println(" ----- ");

			System.out.println("Creating next generation ... ");
			orange.combineAndMutate();
		}

		System.out.println("Best member after " + Integer.toString(generationCount) + " generations ... ");
		orange.showBest();

		/*blue.setPrintCode("\u001B[38;5;27m");
		System.out.println("Evaluating Blue Tribe");
		blue.evalTribe();
		best = blue.getBestMemeber();
		System.out.println("Best Member: " + best.getName() + " --> " + Integer.toString(best.getFitness()));
		blue.showBest();

		System.out.println(" ----- ");

		green.setPrintCode("\u001B[38;5;29m");
		System.out.println("Evaluating Green Tribe");
		green.evalTribe();
		best = green.getBestMemeber();
		System.out.println("Best Member: " + best.getName() + " --> " + Integer.toString(best.getFitness()));
		green.showBest();*/
	}

	
}