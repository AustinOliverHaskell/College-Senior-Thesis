#!/bin/sh
clear
echo " - Compilation, Execution, and Documentation script - "

echo "Compile Program? [y/n]"
read answer
echo ""

if [ $answer = "y" ]
	then

	echo "Compiling Java..."
	javac -g -d ../compiled/ -Xlint -deprecation *.java
	echo "javac -g -d ../compiled/ -Xlint -deprecation *.java"
	echo ""
	echo "Compiling C++..."
	make -f ./PhysicsEngine/makefile.make
	mv physics ./PhysicsEngine/o
	echo ""
	echo "---> Compilation complete"
	echo ""

fi

echo "Run the program? [y/n]"
read answer

echo ""

if [ $answer = "y" ]
	then

	echo "Starting up project..."

	echo "-> Clearing images folder of old data"
	rm ../compiled/img/*.png

	echo "-> Removing old log file"
	rm ../compiled/*.txt

	echo ""
	echo "Run C program only? [y/n]"
	read answer

	if [ $answer = "y" ]
		then
		./PhysicsEngine/o/physics FILENAME
	else
		echo "-> Starting Program..."
		echo " vvvvvvvvvvvvvvvvvvvv "
		echo ""
		java -ea -cp ../compiled/ austin.structures.Driver 20 1
		echo ""
		echo " ^^^^^^^^^^^^^^^^^^^^ "
		echo "---> Program run complete..."
	fi

	

fi

echo ""
echo "Generate Javadoc? [y/n]"
read answer
echo ""

if [ $answer = "y" ]
	then

	echo "    ______ _______     "
	echo "   |      |      ||    "
	echo "   | java | doc  ||    "
	echo "   | ---- | ---- ||    "
	echo "   | ---- | ---- ||    "
	echo "   |______|______||    "
	echo ""

	echo "Generating Javadoc..."
	javadoc -d ../docs/ *.java
	echo "---> Javadoc Generation Complete"

fi