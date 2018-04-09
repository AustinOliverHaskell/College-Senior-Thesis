package austin.structures;

import java.util.*;
import java.io.*;

public class ObjFileBuilder
{
	ArrayList<String> normals;
	ArrayList<String> verticies;
	ArrayList<String> faces;

	public ObjFileBuilder()
	{
		this.normals = new ArrayList<String>();
		this.verticies = new ArrayList<String>();
		this.faces = new ArrayList<String>();
	}

	public void add(Tetrahedron t)
	{
		// For each triangle in the tetrahedron
		for (int i = 0; i < 4; i++)
		{
			Triangle face = t.getFace(i);

			// Gets the position of the vertex if it exists, if not then
			//  add it and return the index of the newly added vertex
			int one   = addVertex(face.a.toString());
			int two   = addVertex(face.b.toString());
			int three = addVertex(face.c.toString());

			normals.add("vn " + face.getNormal().toString() + "\n");

			StringBuilder str = new StringBuilder();

			str.append("f ");
			str.append(one   + "//" + normals.size() + " ");
			str.append(two   + "//" + normals.size() + " ");
			str.append(three + "//" + normals.size() + "\n");

			faces.add(str.toString());
		}
	}

	public void save(String path)
	{
		if (path == null)
		{
			// Defualt file path
			path = "./Object.obj";
		}

		try 
		{
			FileWriter fileWriter = new FileWriter(path);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			for (String s : verticies)
			{
				bufferedWriter.write(s);
			}

			for (String s : normals)
			{
				bufferedWriter.write(s);
			}

			for (String s : faces)
			{
				bufferedWriter.write(s);
			}

			bufferedWriter.close();
		}
		catch (Exception error)
		{
			error.printStackTrace();
		}
	}

	private int addVertex(String vertex)
	{
		int retVal = 0;

		vertex = "v " + vertex + "\n";

		if (verticies.contains(vertex))
		{
			retVal = verticies.indexOf(vertex) + 1;
		}
		else
		{
			verticies.add(vertex);
			retVal = verticies.size();
		}

		return retVal;
	}
	
}