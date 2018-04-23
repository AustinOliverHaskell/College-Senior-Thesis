package austin.structures;

import java.util.*;
import java.io.*;
import java.awt.Color;

public class Tribe
{
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

	public void evalTribe()
	{
		for (Structure s : tribe) 
		{
			// Getting rid of evaluating ones that already have a fitness value
			if (s.getFitness() == Integer.MAX_VALUE)
			{
				int fitness = evaluate(s.getName() + ".obj", "-c", s.getName());

				s.setFitness(fitness);
			}

			System.out.println(printCode + s.getName() + " ... Completed Evaluation with a score of: " + Integer.toString(s.getFitness()) + clearPrintCode);
		}

		sort();
	}

	public void combineAndMutate()
	{
		sort();

		// Delete worst half
		for (int i = tribe.size() / 2; i < tribe.size(); i++)
		{
			tribe.remove(i);
			i--;
		}

		int i = 1; 
		while (tribe.size() < size)
		{
			String memberName = name + "_" + UUID.randomUUID().toString();

			Structure temp = new Structure(tribe.get(i), tribe.get(i), "NO_COLLIDE_QUARTERS");

			temp.setName(memberName);

			System.out.println(" Object: " + memberName + " welcome to the tribe." + clearPrintCode);

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

	public void saveBest()
	{
		sort();

		tribe.get(0).save("../compiled/obj/000_CHAMPION_" + tribe.get(0).getName() + ".obj");
	}

	public ArrayList<Structure> createTribe()
	{
		ArrayList<Structure> retVal = new ArrayList<Structure>();

		for (int i = 0; i < size; i++)
		{
			Structure s = new Structure();

			String memberName = name + "_" + UUID.randomUUID().toString();

			s.setName(memberName);

			System.out.println(printCode + "Object: " + memberName + " welcome to the tribe." + clearPrintCode);

			s.save("../compiled/obj/" + memberName + ".obj");

			retVal.add(s);
		}

		return retVal;
	}



}