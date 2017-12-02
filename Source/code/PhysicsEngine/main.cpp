#include "./h/headers.h"

int main(int argc, char* argv[])
{
	// Check arguments, we should always have atleast one
	if (argc < 2)
	{
		std::cout << "Incorrect number of arguments - Fatal Error - " << std::endl; 

		return 0;
	}

	Debug::open();
	Debug::logf("-> Simulation for file: " + std::string(argv[1]));


	Simulation* simulation = new Simulation(std::string(argv[1]));

	simulation->openWindow();


	Debug::logf("-> Finished simulation for file: " + std::string(argv[1]));
	Debug::save();
	Debug::close();

	return 0;
}
