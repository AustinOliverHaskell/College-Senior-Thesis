LIB      = -ldl -lGL -lGLU -lBulletDynamics -lBulletCollision -lBulletSoftBody -lLinearMath 
STANDARD = -std=c++11
INCLUDE  = -I /usr/local/include/bullet/
LIBLOCATION = -L /usr/local/lib/
OBJECTFILES = ./PhysicsEngine/o/BulletLib/*.o
CPP = ./PhysicsEngine/BasicExample.cpp ./PhysicsEngine/Debug.cpp

all: ./PhysicsEngine/main.cpp
	g++ $(INCLUDE) $(LIBLOCATION) $(STANDARD) -Wall -g -o physics ./PhysicsEngine/main.cpp $(CPP) $(OBJECTFILES) $(LIB)