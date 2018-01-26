#pragma once

#include "headers.h"
#include "glHeaders.h"

class Simulation
{
	public:
		// --- Constructors/Destructors ---
		Simulation();
		Simulation(std::string filePath);
		~Simulation();

		// --- Bullet Physics ---


		// --- OpenGL ---
		void openWindow();
		void render();

		
		// --- Other ---

	private:
		std::string objFilePath;
		int fitnessValue;
		GLFWwindow* window;
};