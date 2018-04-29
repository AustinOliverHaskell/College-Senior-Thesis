package austin.structures;

import java.util.*;
import java.io.*;
import java.awt.Color;

public class Driver
{
	public static void main(String[] args)
	{
		System.out.println("Creating initial population ... ");

		int tribeSizes = 200;
		int generationCount = 30;
		
		Tribe orange = new Tribe("OrangeBois", tribeSizes);
		orange.setColor(Color.ORANGE);

		/*Tribe blue = new Tribe("BlueBois", tribeSizes);
		blue.setColor(Color.BLUE);

		Tribe green = new Tribe("GreenBois", tribeSizes);
		green.setColor(Color.GREEN);*/

		orange.setPrintCode("\u001B[38;5;166m");
		/*blue.setPrintCode("\u001B[38;5;27m");
		green.setPrintCode("\u001B[38;5;29m");*/


		for (int i = 0; i < generationCount; i++)
		{
			System.out.println("Generation: " + Integer.toString(i));

			orange.evalTribe();

			orange.print();

			/*Thread b = new Thread(new Runnable() {

			      public void run() {
			         blue.evalTribe();
			      }
			});

			Thread g = new Thread(new Runnable() {

			      public void run() {
			         green.evalTribe();
			      }
			});
*/
			//o.start();
			/*b.start();
			g.start();*/

			/*try
			{
				o.join();
				/*b.join();
				g.join();
			}
			catch(Exception error)
			{
				error.printStackTrace();
			}*/

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

			/*best = blue.getBestMemeber();
			System.out.println("Best Member: " + best.getName() + " --> " + Integer.toString(best.getFitness()));
			blue.showBest();

			best = green.getBestMemeber();
			System.out.println("Best Member: " + best.getName() + " --> " + Integer.toString(best.getFitness()));
			green.showBest();

			System.out.println(" ----- ");

			System.out.println("Creating next generation ... ");

			green.combineAndMutate();
			blue.combineAndMutate();*/
			orange.combineAndMutate();
		}

		System.out.println("Best member after " + Integer.toString(generationCount) + " generations ... ");
		orange.showBest();
	}

	
}