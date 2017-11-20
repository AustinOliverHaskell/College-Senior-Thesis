#pragma once

#include <fstream>
#include <string>
#include <vector>

class Debug
{
	public:
		Debug(){};

		static void logf(std::string message);
		static void logs(std::string message);
		static void loga(std::string message);

		static void save(std::string path = "./", std::string name = "CLog.txt");
		static void open(std::string path = "CLog.txt");
		static void close();

		static void clear();
		static void disable();
		static void enable();

	protected:
		static std::ofstream file;
		static bool debugingEnabled;
		static bool fileOpened;
		static std::vector<std::string> *errorList; 
};

