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
	cd PhysicsEngine
	#echo "C++ COMPILATION IS COMMENTED OUT"
	make -f makefile.make
	mv Driver ./o
	cd ..
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

	pwd

	echo ""
	echo "Run C program only? [y/n]"
	read answer

	if [ $answer = "y" ]
		then
		cd PhysicsEngine/o/
		./Driver FILENAME
		cd ../..
	else
		echo "-> Starting Program..."
		echo " vvvvvvvvvvvvvvvvvvvv "
		echo ""
		java -ea -cp ../compiled/ austin.structures.Driver 1000 1
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
	
	# A bored computer scientist is a bad thing...

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