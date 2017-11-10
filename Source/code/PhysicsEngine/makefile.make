LIB      = -ldl -lGL -lGLU -lBulletDynamics -lBulletCollision -lBulletSoftBody -lLinearMath 
STANDARD = -std=c++11
INCLUDE  = -I /usr/local/include/bullet/
LIBLOCATION = -L /usr/local/lib/
OBJECTFILES = ./PhysicsEngine/o/BulletLib/*.o

all: ./PhysicsEngine/main.cpp
	g++ $(INCLUDE) $(LIBLOCATION) $(STANDARD) -Wall -o physics ./PhysicsEngine/main.cpp $(OBJECTFILES) $(LIB)