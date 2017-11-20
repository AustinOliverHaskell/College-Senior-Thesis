#include "./h/headers.h"

int main(int argc, char* argv[])
{
	if (argc < 2)
	{
		std::cout << "Incorrect number of arguments - Fatal Error - " << std::endl; 

		return 0;
	}

	Debug::open();

/*	DummyGUIHelper noGfx;

	CommonExampleOptions options(&noGfx);
	CommonExampleInterface* example = BasicExampleCreateFunc(options);
	
	example->initPhysics();
	example->stepSimulation(1.f/60.f);
	example->exitPhysics();

	delete example;*/

	Debug::logs(" - C Program - ");
	Debug::logs(argv[1]);

	Debug::save();
	Debug::close();



	return 0;
}
