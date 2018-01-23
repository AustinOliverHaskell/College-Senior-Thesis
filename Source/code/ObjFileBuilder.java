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
	private ArrayList<ExtendedVec> points;
	private Structure structure;

	ObjFileBuilder()
	{
		this.structure = null;
		this.sections = new ArrayList<Layer>();
		this.points = new ArrayList<ExtendedVec>();
	}

	ObjFileBuilder(Structure structure)
	{
		this.structure = structure;
		this.sections = new ArrayList<Layer>();
		this.points = new ArrayList<ExtendedVec>();
	}

	/**
	*   This constructor takes an already filled list
	*
	*   @param sections Pre-filled list of layers
	*/
	ObjFileBuilder(ArrayList <Layer> sections)
	{
		this.sections = sections;
		this.points = new ArrayList<ExtendedVec>();
		this.structure = null;
	}

	/**
	*   Adds a new layer to the internal list of layers
	*
	*   @param layer New layer to append to the list
	*/
	public void add(Layer layer)
	{
		sections.add(layer.clone());
	}

	private String constructHeader()
	{
		StringBuilder retVal = new StringBuilder();

		retVal.append("# Created by Austin Haskell - Evolutionary Structure Simulation - \n");

		if (this.structure == null)
		{
			// No structure loaded
			retVal.append("# No structure information available...\n");
		}
		else
		{
			// Append the correct information
			retVal.append("# Fitness value: " + Integer.toString(this.structure.getFitness()) + "\n");
			retVal.append("# Structure  ID: " + this.structure.getId() + "\n");
		}


		retVal.append("mtllib ./vp.mtl\n\n");
		retVal.append("g\n");

		return retVal.toString();
	}

	private String constructPoints()
	{
		StringBuilder retVal = new StringBuilder();
		int vectorNumber = 1;

		for (Layer layer : sections)
		{
			List <Vec> tempPoints = layer.getSafePoints(); 

			for (Vec item : tempPoints)
			{
				retVal.append("v ");
				retVal.append(item.toString());
				retVal.append("\n");

				points.add(new ExtendedVec(new Vec(item), vectorNumber));

				vectorNumber++;
			}
		}

		retVal.append("\n");
		retVal.append("# " + Integer.toString(vectorNumber-1) + " verticies\n");
		retVal.append("\n");

		return retVal.toString();
	}

	private String constructBody()
	{
		StringBuilder retVal = new StringBuilder();

		retVal.append(constructLayer(0));
		retVal.append(constructLayer(sections.size()-1));

		// Get the length of the layer, theyre all the same length
		int layerLength = sections.get(0).getSafePoints().size();

		for (int i = 0; i < points.size()-1; i++)
		{
			if ((i + 1 + layerLength) >= points.size())
			{				
				retVal.append("f ");
				retVal.append(points.get(layerLength-1).getPosition());
				retVal.append(" ");
				retVal.append(points.get(0).getPosition());
				retVal.append(" ");
				retVal.append(points.get(layerLength).getPosition());
				retVal.append(" ");
				retVal.append(points.get(points.size()-1).getPosition());
				retVal.append("\n");

				break;
			}

			retVal.append("f ");
			retVal.append(points.get(i+1).getPosition());
			retVal.append(" ");
			retVal.append(points.get(i).getPosition());
			retVal.append(" ");
			retVal.append(points.get(i+layerLength).getPosition());
			retVal.append(" ");
			retVal.append(points.get(i+1+layerLength).getPosition());
			retVal.append(" ");
			retVal.append("\n");
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

	/**
	*    @param filepath the path to save the file, if null then assumes current directory "./"
	*	 @param filename the name of the file without the ".obj" ending, if null assumes "Object"
	*
	*    save() will create and save this file as a .obj file 
	*    <br />
	*	 NOTE: This operation is destructive - It will overwrite a file sharing the same name
	*/
	public void save(String filepath, String filename)
	{
		if (filepath == null)
		{
			// Defualt file path
			filepath = "./";
		}

		if (filename == null)
		{
			filename = "Object";
		}

		try 
		{
			FileWriter fileWriter = new FileWriter(filepath + filename + ".obj");

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

	public void calculateNormal()
}