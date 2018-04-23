#include <iostream>
#include <cstdlib>
#include <stdlib.h>
#include <cmath>
#include <getopt.h>
#include <string>
#include <time.h>

#include "./GL/src/h/World.h"
#include "./GL/src/h/Model.h"
#include "./GL/src/h/defs.h"
#include "./GL/src/h/Structure.h"
#include "./GL//src/h/Constants.h"

using namespace std;

int main (int argc, char * argv[])
{
    string shaderPath = string(getenv("SHADER_PATH"));
    string objPath    = string(getenv("OBJ_PATH"));
    // Defaults
    string mode = "CONCISE";
    string path = "Object.obj";

    string windowName = "Simulation";

    // This isnt really nessasary but it helps with 
    //  the visualization of each group
    float r, g, b;

    r = 0.0f;
    g = 0.0f; 
    b = 0.0f;

    //cout << argv[1] << " " << argv[2] << endl;

    int c;

    while ((c = getopt(argc, argv, "hf:n:r:g:b:vc")) != -1)
    {
        switch (c)
        {
            case 'h':
                cerr << argv[0] << "options:" << endl;
                cerr << "  -f Filename to simulate. " << endl;
                cerr << "  -v Enable verbose mode. " << endl;
                exit(0);

            case 'f':
                path = optarg;
                //cout << optarg << endl;
                break;

            case 'v':
                mode = "VERBOSE";
                break;

            case 'c':
                mode = "CONCISE";
                break;

            case 'n':
                windowName = optarg;
                break;

            case 'r':
                r = stof(optarg);
                break;

            case 'g':
                g = stof(optarg);
                break;

            case 'b':
                b = stof(optarg);
                break;
        }
    }

    srand (static_cast <unsigned> (time(0)));

    //std::cout << "Thesis" << std::endl;

    //cout << "Path: " << path << endl;

    // Create the world
    World * world = new World(mode, windowName);

    GLuint shader = 0;
    GLuint solidShader = 0;

    if (mode == "VERBOSE")
    {
        // Load in the shaders that were going to use
        string vertex   = shaderPath + "lightShader.vertexshader";
        string fragment = shaderPath + "lightShader.fragmentshader";

        shader = loadShaders(vertex.c_str(), fragment.c_str());

        vertex   = shaderPath + "SimpleVertexShader.vertexshader";
        fragment = shaderPath + "SimpleFragmentShader.fragmentshader";
        solidShader = loadShaders(vertex.c_str(), fragment.c_str());

        // Create the memory for our singular light
        GLuint LightID = glGetUniformLocation(shader, "LightPosition_worldspace");

        vec3 lightPosition = vec3(5.0f, 5.0f, 5.0f);

        // Let there be light
        world->setLight(LightID);
        world->setLightPos(lightPosition);
    }

    string planePath  = objPath + string("plane.obj");
    string structPath = objPath + path; 
    string spherePath = objPath + string("sphere.obj");

    // Models in our scene, the plane needs to have its normals calculated
    //Model * object = new Model("./GL/src/obj/Object.obj", solidShader, world);
    Model * sphere = new Model(spherePath.c_str(), solidShader, world);
    Model * plane  = new Model(planePath.c_str(),  solidShader, world, true);
    //Model * light  = new Model("./GL/src/obj/cube.obj", solidShader, world);
    Structure * s  = new Structure(structPath, shader, world);  

    s->setColor(r, g, b);

    if (mode == "VERBOSE")
    {
        // Initilize our buffers
        //object->initBuffers();
        plane ->initBuffers();
        //light ->initBuffers();
        sphere->initBuffers();

        // Set the plane to a different color
        //object->randomizeColor();
        sphere->setColor(PARTICLE_R, PARTICLE_G, PARTICLE_B);
        plane ->setColor(0.5f, 0.5f, 0.5f);
        //light ->setColor(1.0f, 1.0f, 1.0f);
    }

    // Physical Attributes
    //object->setMass(50);
    sphere->setMass(WEIGHT);

    //object->setFriction(0.1f);
    sphere->setFriction(0.1f);
    plane ->setFriction(2.0f);

    //object->setRollingFriction(0.01f);
    sphere->setRollingFriction(0.05f);

    //object->setRestitution(0.5);
    sphere->setRestitution(0.5);
    plane ->setRestitution(0.5);

    plane ->setCollisionShape(new btStaticPlaneShape(btVector3(0, 1, 0), 0));
    sphere->setCollisionShape(new btSphereShape(1));

    //object->setPosition(vec3(0.0f, 10.0f, 0.0f));
    sphere->setPosition(vec3(0.0f, 12.0f, 0.5f));
    plane ->setPosition(vec3(0.0f, 0.0f, 0.0f));
    //light ->setPosition(lightPosition);

    plane ->setScale(vec3(60.0f, 0.0f, 60.0f));
    sphere->setScale(vec3(PARTICLE_SCALING, PARTICLE_SCALING, PARTICLE_SCALING));
    //object->setScale(vec3(2.0f, 2.0f, 2.0f));

    sphere->setType("Raindrop");


    // This one needs a special mesh, the meshes are slower but model
    //  the object much better
    //object->calcTriangleCollisionMesh();

    // Add them to the scene
    // Structures add themselfs to the world from their constructor
    world->addModel(plane);

    // Set Backgrond to black
    world->setBackgroundColor(0.0f, 0.0f, 0.0f);
    world->calcGlue();

    if (mode == "VERBOSE")
    {
        cout << " --------------- Now Rendering ---------------" << endl;
    }

    time_t seconds;
    seconds = time(NULL);

    // Render
    if (mode == "VERBOSE")
    {
        while(time(NULL) - seconds <= SIM_TIME)
        {
            for (int i = 0; i < PARTICLE_COUNT; i++)
            {
                float x = -40.0f;
                float y = 2 + rand() % RANGE;
                float z = rand() % RANGE - (rand() % RANGE);

                Model * temp = new Model(sphere, world);
                temp->setPosition(vec3(x, y, z));
                temp->configureRigidBody();
                temp->getRigidBody()->applyCentralImpulse(btVector3(WIND_SPEED_X, WIND_SPEED_Y, WIND_SPEED_Z));
                world->addModel(temp);
            }

            world->render();
        }
    }
    else
    {
        for (int i = 0; i < 60 * SIM_TIME; i++)
        {
            for (int i = 0; i < PARTICLE_COUNT; i++)
            {
                float x = -40.0f;
                float y = 2 + rand() % RANGE;
                float z = rand() % RANGE - (rand() % RANGE);

                Model * temp = new Model(sphere, world);
                temp->setPosition(vec3(x, y, z));
                temp->configureRigidBody();
                temp->getRigidBody()->applyCentralImpulse(btVector3(WIND_SPEED_X, WIND_SPEED_Y, WIND_SPEED_Z));
                world->addModel(temp);
            }

            world->render();

            //cout << "#";
        }
    }

    int fitness = world->getBrokenCount();
    // The world object will handle the destruction of all models in its space
    delete world;
    delete s;

    cout << endl << "Returning Fitness: " << fitness << endl;

    return fitness;
}