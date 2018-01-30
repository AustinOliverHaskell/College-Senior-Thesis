#include "./h/Simulation.h"
#include "./h/glHeader.h"
#include "./h/Graphics.h"

#include <string>

using namespace std;

Simulation::Simulation(string n)
{
	name = n;
}

Simulation::~Simulation()
{

}

void Simulation::start(bool display)
{
	if (display)
	{
		Graphics * g = new Graphics(name);

		g->openWindow();

		g->setupGL();

		delete g;
	}

	// TODO: Physics Stuffs
}