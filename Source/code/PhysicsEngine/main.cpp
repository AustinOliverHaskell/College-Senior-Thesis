#include "BasicExample.h"

#include "CommonExampleInterface.h"
#include "CommonGUIHelperInterface.h"
#include "BulletCollision/CollisionDispatch/btCollisionObject.h"
#include "BulletCollision/CollisionShapes/btCollisionShape.h"
#include "BulletDynamics/Dynamics/btDiscreteDynamicsWorld.h"

#include "LinearMath/btTransform.h"
#include "LinearMath/btHashMap.h"

int main(int argc, char* argv[])
{
	
	DummyGUIHelper noGfx;

	CommonExampleOptions options(&noGfx);
	CommonExampleInterface*    example = BasicExampleCreateFunc(options);
	
	example->initPhysics();
	example->stepSimulation(1.f/60.f);
	example->exitPhysics();

	delete example;

	return 0;
}
