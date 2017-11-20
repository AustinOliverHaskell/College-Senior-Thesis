package austin.structures;

import java.util.*;

public class TestingSuite
{
	public static void runAllTests()
	{
		testVec();
		testLayer();
		testObjFileBuilder();
		testDna();
		testStructure();
		testEvaluator();
	}

	public static void testVec()
	{
		Vec test = new Vec();

		// Test default constructor
		assert (test.x == 0);
		assert (test.y == 0);
		assert (test.z == 0);

		// Test toString
		assert (test.toString().equals("0 0 0"));

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
		// ---------- Test 01 - copy ----------
		Layer layer = new Layer(100, 100, 0);
		Layer layer2 = layer.clone();

		assert( layer.equals(layer)  );
		assert( layer.equals(layer2) );
		// ------------------------------------


		// ---------- Test 02 - mutate ----------
		layer.mutate(0.2f);

		assert( !layer.equals(layer2) );
		assert(  layer.equals(layer)  );
		// --------------------------------------
	}

	public static void testObjFileBuilder()
	{
		// ---------- Test 01 - Cube ----------
		ObjFileBuilder test = new ObjFileBuilder();

		ArrayList<Vec> top = new ArrayList<Vec>();
		ArrayList<Vec> btm = new ArrayList<Vec>();

		top.add(new Vec(0, 0, 0));
		top.add(new Vec(10, 0, 0));
		top.add(new Vec(10, 10, 0));
		top.add(new Vec(0, 10, 0));

		btm.add(new Vec(0, 0, 10));
		btm.add(new Vec(10, 0, 10));
		btm.add(new Vec(10, 10, 10));
		btm.add(new Vec(0, 10, 10));

		Layer layer  = new Layer(20, 20, 0, top);
		Layer layer2 = new Layer(20, 20, 10, btm);

		test.add(layer);
		test.add(layer2);

		test.save("../compiled/obj/", "cube_test");
		// --------------------------------------


		// ---------- Test 02 - Random ----------
		ObjFileBuilder test2 = new ObjFileBuilder();

		Layer layer3 = new Layer(20, 20, 0);
		Layer layer4 = layer3.clone();

		layer4.setZIndex(10);

		test2.add(layer3);
		test2.add(layer4);

		test2.save("../compiled/obj/", "random_test");
		// -------------------------------------------------

		// ---------- Test 03 - Random - 3 Layers ----------
		ObjFileBuilder test3 = new ObjFileBuilder();

		Layer layer5 = new Layer(20, 20, 0);
		Layer layer6 = layer5.clone();
		Layer layer7 = layer5.clone();

		layer6.setZIndex(10);
		layer7.setZIndex(20);

		test3.add(layer5);
		test3.add(layer6);
		test3.add(layer7);

		test3.save("../compiled/obj/", "random_multi_layer_test");
		// --------------------------------------
	}

	public static void testDna()
	{

	}

	public static void testStructure()
	{

	}

	public static void testEvaluator()
	{
		Structure structure = new Structure();

		Evaluator evaluator = new Evaluator(structure);

		evaluator.evaluate();
	}
}