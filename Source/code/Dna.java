package austin.structures;

import java.util.*;	

public class Dna
{
	private ArrayList<Layer> genes;
	private final float mutationRate;
	private int size;

	// Size of the individual slices
	private final int xDimention = 200;
	private final int yDimention = 200;

	Dna(float rate, int size)
	{
		this.genes        = new ArrayList<Layer>();
		this.mutationRate = rate;
		this.size         = size;

		initDNA();

	}

	private void mutate()
	{
		for (int i = 0; i < genes.size(); i++)
		{
			this.genes.get(i).mutate(mutationRate);
		}
	}

	private void initDNA()
	{
		Layer temp = new Layer(xDimention, yDimention, 0);

		for (int i = 0; i < size; i++)
		{
			Layer clone = temp.clone();
			genes.add(clone);
		}

		System.out.println("Init DNA for size "+ size +" Complete");
	}

	public void save(String filePath)
	{
		for (int i = 0; i < genes.size(); i++)
		{
			genes.get(i).save(filePath + "/" + i + ".png");

			Debug.logf("Area of img #" + i + " is " + genes.get(i).getArea());
		}
	}

	public void printLayers()
	{
		for (int i = 0; i < genes.size(); i++)
		{
			Debug.logf("Layer: " + (i+1));
			Debug.logf(genes.get(i).toString());
		}
	}

	@Override
	public String toString()
	{

		return "";
	}
}