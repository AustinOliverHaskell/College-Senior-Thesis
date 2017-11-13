#include "./h/headers.h"

int main(int argc, char* argv[])
{
	Debug::open();

	DummyGUIHelper noGfx;

	CommonExampleOptions options(&noGfx);
	CommonExampleInterface* example = BasicExampleCreateFunc(options);
	
	example->initPhysics();
	example->stepSimulation(1.f/60.f);
	example->exitPhysics();

	delete example;

	Debug::save();
	Debug::close();

	return 0;
}
