package austin.structures;

import java.util.*;
import java.io.*;
import java.awt.Color;

public class Driver
{
	public static void main(String[] args)
	{
		System.out.println("Creating initial population ... ");

		int tribeSizes = 128;
		//int tribeSizes = 12;
		int generationCount = 40;
		
		Tribe orange = new Tribe("HaskiesChildren", tribeSizes);
		orange.setPrintCode("\u001B[38;5;166m");
		orange.setColor(Color.ORANGE);

		for (int i = 0; i < generationCount; i++)
		{
			System.out.println("Generation " + Integer.toString(i));

			orange.evalTribe();

			orange.saveBest(i+1);

			orange.print();

			orange.showBest();

			orange.combineAndMutate();
		}
	}
}