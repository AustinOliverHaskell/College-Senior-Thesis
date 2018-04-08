import java.util.*;
import Jama.*;

public class Driver
{
	public static void main(String[] args)
	{
		System.out.println("Testing");
		
		Structure s = new Structure();

		System.out.println("Saved object as ./Object.obj");

		s.save("./Object.obj");
	}
}