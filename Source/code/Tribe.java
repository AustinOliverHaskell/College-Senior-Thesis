package austin.structures;

import java.util.*;
import java.io.*;
import java.awt.Color;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tribe
{
	public static int THREAD_COUNT = 8;
	public static float deletePercentage = 0.90f;
	private static String clearPrintCode = "\u001B[0m";
	ArrayList <Structure> tribe;

	String name;
	int    size;

	Color color;
	String printCode;

	Tribe(String name, int size)
	{
		this.name = name;
		this.size = size;

		printCode = " ";

		tribe = createTribe();

		color = Color.WHITE;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public Color getColor()
	{
		return this.color;
	}

	public void setPrintCode(String s)
	{
		printCode = s;
	}

	public Structure getBestMemeber()
	{
		sort();
		return tribe.get(0);
	}

	public void sort()
	{
		Collections.sort(tribe);
	}

	// TODO: make a simulation builder to cut this argument list down
	private int evaluate(String path, String mode, String windowName)
	{
		int fitness = 0;

		try 
		{
			ProcessBuilder builder = new ProcessBuilder("./PhysicsEngine/Driver", 
				 "-f" ,path,
				 mode,
				 "-n", windowName,
				 "-r", Float.toString((float) color.getRed() / 255.0f),
				 "-g", Float.toString((float) color.getGreen() / 255.0f),
				 "-b", Float.toString((float) color.getBlue() / 255.0f));

			//builder.inheritIO();

			Process proc = builder.start();

			proc.waitFor();

			fitness = proc.exitValue();
		}
		catch (IOException error)
		{
		}
		catch (InterruptedException error)
		{
		}
		catch (Exception error)
		{
			error.printStackTrace();
		}

		return fitness;
	}

	public static int staticEvaluate(String path)
	{
		int fitness = 0;

		try 
		{
			ProcessBuilder builder = new ProcessBuilder("./PhysicsEngine/Driver", 
				 "-f" ,path, "-c");

			//builder.inheritIO();

			Process proc = builder.start();

			proc.waitFor();

			fitness = proc.exitValue();
		}
		catch (IOException error)
		{
		}
		catch (InterruptedException error)
		{
		}
		catch (Exception error)
		{
			error.printStackTrace();
		}

		return fitness;
	}

	public void evalTribe()
	{
		ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);  

		for (Structure s : tribe) 
		{
			// Getting rid of evaluating ones that already have a fitness value
			if (s.getFitness() == Integer.MAX_VALUE)
			{
				pool.execute(s);
			}
		}

		pool.shutdown();

		while(!pool.isTerminated())
		{
			try
			{
				Thread.sleep(1000);
			}
			catch(Exception error)
			{
				error.printStackTrace();
			}
		}

		sort();
	}

	public void combineAndMutate()
	{
		sort();

		// Delete worst 90%
		for (int i = (int)((float)tribe.size() * (1.0f - deletePercentage)); i < tribe.size(); i++)
		{
			tribe.remove(i);
			i--;
		}

		int breedingRange = (int)((float)tribe.size() * (deletePercentage));

		if (breedingRange >= tribe.size())
		{
			breedingRange = tribe.size()-1;
		}

		while (tribe.size() < size)
		{
			int i = 0; 

			int d = 0;
			int m = 0;

			Random rand = new Random();
			while (rand.nextBoolean())
			{
				i++;
			}

			if (i >= breedingRange)
			{
				i = breedingRange;
			}

			d = i;
			// Reset
			i = 0;

			while (rand.nextBoolean())
			{
				i++;
			}

			if (i >= breedingRange)
			{
				i = breedingRange;
			}

			m = i;

			Structure dad = tribe.get(d);
			Structure mom = tribe.get(m);

			String memberName = name + "_" + UUID.randomUUID().toString();

			Structure temp = new Structure(mom, dad, "ASEXUAL");

			temp.setName(memberName);

			if (d == m)
			{
				// It was basically asexual so this mutates it for sure
				temp.mutate();
			}

			System.out.println(" Object: " + memberName + " added to population." + clearPrintCode + ":" + Integer.toString(d) + "-" + Integer.toString(m));

			temp.save("../compiled/obj/" + memberName + ".obj");

			i++;

			tribe.add(temp);
		}

		sort();
	}

	public void showBest()
	{
		Structure s = tribe.get(0);
		int fitness = evaluate(s.getName() + ".obj", "-v", s.getName());
	}

	public void print()
	{
		sort();

		for (Structure s : tribe)
		{
			System.out.println(printCode + s.getName() + " score of " + Integer.toString(s.getFitness()) + clearPrintCode);
		}
	}

	public void saveBest(int generation)
	{
		sort();

		tribe.get(0).save("../compiled/obj/000_CHAMPION_"+ Integer.toString(generation) + "_" + tribe.get(0).getName() + ".obj");
	}

	public ArrayList<Structure> createTribe()
	{
		ArrayList<Structure> retVal = new ArrayList<Structure>();

		for (int i = 0; i < size; i++)
		{
			Structure s = new Structure();

			String memberName = name + "_" + UUID.randomUUID().toString();

			s.setName(memberName);

			System.out.println(printCode + "Object: " + memberName + " added to population." + clearPrintCode);

			s.save("../compiled/obj/" + memberName + ".obj");

			retVal.add(s);
		}

		return retVal;
	}



}