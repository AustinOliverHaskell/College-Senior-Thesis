package austin.structures;

import java.io.*;
import java.util.*;

/**
 *  This mostly static class deals will debug text, each 
 *   call to the functions logf or logs will either print
 *   the debug text to the screen or it will store the text
 *   to print to that it can be saved to a file later
 */
public class Debug
{
	private static boolean debuggingEnabled = false;
	private static String filename = "log.txt";
	private static ArrayList<String> log = new ArrayList<String>();

	public static void enable()
	{
		debuggingEnabled = true;
	}

	public static void disable()
	{
		debuggingEnabled = false;
	}

	public static void logf(String logText)
	{
		if (debuggingEnabled)
		{
			log.add(logText);
		}
	}

	public static void logs(String logText)
	{
		if (debuggingEnabled)
		{
			System.out.println(logText);
		}
	}

	public static void loga(String logText)
	{
		logs(logText);
		logf(logText);
	}

	public static void save(String filepath)
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

			for (int i = 0; i < log.size(); i++)
			{
				bufferedWriter.write(log.get(i));
				bufferedWriter.newLine();
			}

			bufferedWriter.close();
		}
		catch (Exception error)
		{
			error.printStackTrace();
		}
	}
}