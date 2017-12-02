LIB      = -ldl -lglfw3 -pthread -lGLEW -lGLU -lGL -lrt -lXrandr -lXxf86vm -lXi -lXinerama -lX11 -lBulletDynamics -lBulletCollision -lBulletSoftBody -lLinearMath 
STANDARD = -std=c++11
INCLUDE  = -I /usr/local/include/bullet/
LIBLOCATION = -L /usr/local/lib/ -L /usr/lib/x86_64-linux-gnu/
OBJECTFILES = ./PhysicsEngine/o/BulletLib/*.o
CPP = ./PhysicsEngine/BasicExample.cpp ./PhysicsEngine/Debug.cpp ./PhysicsEngine/Simulation.cpp ./PhysicsEngine/shader.cpp

all: ./PhysicsEngine/main.cpp
	g++ $(INCLUDE) $(LIBLOCATION) $(STANDARD) -Wall -g -o physics ./PhysicsEngine/main.cpp $(CPP) $(OBJECTFILES) $(LIB)