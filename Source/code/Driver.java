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
		int generationCount = 80;
		
		Tribe orange = new Tribe("OrangeBois", tribeSizes);
		orange.setPrintCode("\u001B[38;5;166m");
		orange.setColor(Color.ORANGE);

		Tribe green = new Tribe("GreenBois", tribeSizes);
		green.setPrintCode("\u001B[38;5;46m");
		green.setColor(Color.GREEN);

		Tribe pink = new Tribe("PurpleBois", tribeSizes);
		pink.setPrintCode("\u001B[38;5;146m");
		pink.setColor(Color.PINK);

		Tribe red = new Tribe("RedBois", tribeSizes);
		red.setPrintCode("\u001B[38;5;88m");
		red.setColor(Color.RED);

		Tribe blue = new Tribe("RedBois", tribeSizes);
		blue.setPrintCode("\u001B[38;5;26m");
		blue.setColor(Color.BLUE);

		ArrayList<Tribe> tribes = new ArrayList<Tribe>();

		tribes.add(orange);
		tribes.add(pink);
		tribes.add(green);
		tribes.add(red);
		tribes.add(blue);

		for (Tribe t : tribes)
		{
			for (int i = 0; i < generationCount; i++)
			{
				System.out.println(t.getName() + " --  Generation " + Integer.toString(i));

				t.evalTribe();

				t.saveBest(i+1);

				t.print();

				t.showBest();

				t.combineAndMutate();
			}
		}
	}
}