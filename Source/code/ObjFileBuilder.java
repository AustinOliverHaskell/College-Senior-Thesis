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
	private ArrayList<Vec>   points;

	ObjFileBuilder()
	{
		sections = new ArrayList<Layer>();
		points = new ArrayList<Vec>();
	}

	public void add(Layer layer)
	{
		sections.add(layer.clone());
	}

	private String constructHeader()
	{
		StringBuilder retVal = new StringBuilder();

		retVal.append("# Created by Austin Haskell - Evolutionary Structure Simulation - \n");
		retVal.append("mtllib ./vp.mtl\n\n");
		retVal.append("g\n");

		return retVal.toString();
	}

	private String constructPoints()
	{
		StringBuilder retVal = new StringBuilder();

		for (Layer layer : sections)
		{
			List <Vec> tempPoints = layer.getSafePoints(); 

			for (Vec item : tempPoints)
			{
				retVal.append("v ");
				retVal.append(item.toString());
				retVal.append("\n");

				points.add(new Vec(item));
			}
		}

		retVal.append("\n");
		retVal.append("# " + "?" + " verticies\n");
		retVal.append("\n");

		return retVal.toString();
	}

	private String constructBody()
	{
		StringBuilder retVal = new StringBuilder();
		int offset = 1;


		for (int i = 0; i < sections.size(); i++)
		{
			retVal.append(constructLayer(i));

			int size = sections.get(i).getSafePoints().size();

			for (int q = 0; q < size; q++)
			{
				retVal.append("f ");
				retVal.append(q +   (size * i+1));
				retVal.append(" ");
				retVal.append(q+1 + (size * (i+1)));
				retVal.append(" ");
				retVal.append(q +   (size * (i)));
				retVal.append(" ");
				retVal.append(q+1 + (size * (i)));
				retVal.append(" ");
				
				retVal.append("\n");
			}
		}

		return retVal.toString();
	}

	private String constructLayer(int layerNumber)
	{
		int size = sections.get(0).getSafePoints().size();

		StringBuilder retVal = new StringBuilder();

		retVal.append("f ");

		for (int i = 1; i <= size; i++)
		{
			retVal.append(Integer.toString(i+(size*layerNumber)));
			retVal.append(" ");
		}

		retVal.append("\n");

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