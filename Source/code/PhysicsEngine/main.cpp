#include <iostream>
#include <unistd.h>
#include <string>
#include "./h/Simulation.h"

using namespace std;

int main(int argc, char* argv[])
{
	// TODO: Get arguments


	Simulation s("Test");


	s.start();

	return 0;
}
