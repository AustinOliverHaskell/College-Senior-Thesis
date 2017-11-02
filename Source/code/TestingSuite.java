package austin.structures;

public class TestingSuite
{
	public static void runAllTests()
	{
		testVec();
		testLayer();
		testObjFileBuilder();
	}

	public static void testVec()
	{
		Vec test = new Vec();

		// Test default constructor
		assert (test.x == 0);
		assert (test.y == 0);
		assert (test.z == 0);

		// Test toString
		assert (test.toString().equals("\"0,0,0\""));

		// Test setting variables
		test.x = 12;
		test.y = 13;
		test.z = 14;

		assert (test.x == 12);
		assert (test.y == 13);
		assert (test.z == 14);
	}

	public static void testLayer()
	{

	}

	public static void testObjFileBuilder()
	{
		ObjFileBuilder test = new ObjFileBuilder();
		Layer layer  = new Layer(200, 200, 0);
		Layer layer2 = new Layer(200, 200, 20);

		test.add(layer);
		test.add(layer2);

		test.save("../compiled/obj/", "test.obj");
	}
}