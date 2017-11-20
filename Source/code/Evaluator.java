package austin.structures;

import java.util.*;	
import java.io.*;

public class Evaluator
{
	Structure structure;

	public Evaluator(Structure structure)
	{
		this.structure = structure;
	}

	/**
	*   This function creates and launches the c++ component of this program
	*    it also passes the path of the obj file to be evaluated to the process
	*/
	public void evaluate()
	{
		int fitness = 0;

		try 
		{
			ProcessBuilder builder = new ProcessBuilder("./PhysicsEngine/o/physics", "Howdy");
			builder.inheritIO();

			Process proc = builder.start();
		}
		catch (IOException error)
		{
			Debug.logs("Could not launch external C process");
		}
		catch (Exception error)
		{
			Debug.logs("Unexpected Error");
			error.printStackTrace();
		}

		structure.setFitness(fitness);
	}
}