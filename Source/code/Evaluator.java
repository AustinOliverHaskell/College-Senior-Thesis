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

	public Evaluator()
	{
		this.structure = null;
	}

	// ----- Getters and Setters -----
	public Structure getStructure()
	{
		return this.structure;
	}

	public void setStructure(Structure structure)
	{
		this.structure = structure;
	}
	// -------------------------------

	/**
	*   This function creates and launches the c++ component of this program
	*    it also passes the path of the obj file to be evaluated to the process
	*/
	public void evaluate()
	{
		int fitness = 0;

		try 
		{
			ProcessBuilder builder = new ProcessBuilder("./PhysicsEngine/o/Driver", "../compiled/obj/" + structure.getId() + ".obj", Driver.graphicsMode);
			builder.inheritIO();

			Process proc = builder.start();

			proc.waitFor();

			structure.setFitness(proc.exitValue());

		}
		catch (IOException error)
		{
			Debug.loga("Could not launch external C process for structure " + structure.getId());
		}
		catch (InterruptedException error)
		{
			Debug.loga("Process Interrupted");
		}
		catch (Exception error)
		{
			Debug.loga("Unexpected Error");
			error.printStackTrace();
		}

		structure.setFitness(fitness);
	}
}