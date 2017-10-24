import java.util.*;	

public class Dna
{
	private ArrayList<Layer> genes;
	private final float mutationRate;
	private int size;

	// Size of the individual slices
	private final int xDimention = 20;
	private final int yDimention = 20;

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
		for (int i = 0; i < size; i++)
		{
			Layer temp = new Layer(xDimention, yDimention);
			genes.add(temp);
		}

		System.out.println("Init DNA for size "+ size +" Complete");
	}

	public void save(String filePath)
	{
		for (int i = 0; i < genes.size(); i++)
		{
			genes.get(i).save(filePath + "/" + i + ".png");
			System.out.println("Area of img #" + i + " is " + genes.get(i).getArea());
		}
	}

	@Override
	public String toString()
	{

		return "";
	}
}