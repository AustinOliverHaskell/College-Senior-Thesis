#pragma once

#include <string>

using namespace std;

class Simulation
{
	public:
		Simulation(string n);
		~Simulation();

		void start(bool display = true);

	private:
		string name;
};