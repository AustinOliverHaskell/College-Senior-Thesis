all: ./PhysicsEngine/main.cpp
	g++ -Wall -o physics ./PhysicsEngine/main.cpp

physics: main.cpp 
	g++ -Wall -o physics main.cpp