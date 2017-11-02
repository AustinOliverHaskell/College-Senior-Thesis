package austin.structures;

import java.util.*;
import java.io.*;

/**
 *  This class is in charge of converting the native format
 *   into the .obj file formating
 */
public class ObjFileBuilder
{
	private ArrayList<Layer> sections;

	ObjFileBuilder()
	{
		sections = new ArrayList<Layer>();
	}

	public void add(Layer layer)
	{
		sections.add(layer.clone());
	}

	private String constructHeader()
	{
		StringBuilder retVal = new StringBuilder();

		retVal.append("# Created by Austin Haskell - Evolutionary Structure Simulation - \n");
		retVal.append("mtllib ./vp.mtl\n");
		retVal.append("g\n");

		return retVal.toString();
	}

	private String constructPoints()
	{
		StringBuilder retVal = new StringBuilder();

		for (Layer layer : sections)
		{
			ArrayList <Vec> points = layer.getPoints(); 

			for (Vec item : points)
			{
				retVal.append("v ");
				retVal.append(Integer.toString(item.x));
				retVal.append(" ");
				retVal.append(Integer.toString(item.y));
				retVal.append(" ");
				retVal.append(Integer.toString(item.z));
				retVal.append("\n");
			}
		}

		retVal.append("# " + "?" + " verticies\n");

		return retVal.toString();
	}

	private String constructBody()
	{
		StringBuilder retVal = new StringBuilder();
		int offset = 1;

		for (Layer layer : sections)
		{
			retVal.append("f ");
			List<Vec> points = layer.getSafePoints();

			for (Vec vec : points)
			{
				retVal.append(offset);
				retVal.append(" ");

				offset++;
			}
			retVal.append("\n");
		}

		return retVal.toString();
	}

	public void save(String filepath, String filename)
	{
		if (filepath == null)
		{
			// Defualt file path
			filepath = "./";
		}

		try 
		{
			FileWriter fileWriter = new FileWriter(filepath + filename);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(constructHeader());
			bufferedWriter.write(constructPoints());
			bufferedWriter.write(constructBody());

			bufferedWriter.close();
		}
		catch (Exception error)
		{
			error.printStackTrace();
		}
	}
}