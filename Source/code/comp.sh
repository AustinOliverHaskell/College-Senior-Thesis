#!/bin/bash
clear
echo -e "\e[1m - -  Compilation, Execution, and Documentation script  - - \e[0m"
echo -e "\e[1m - -            Austin Haskell - 2017/2018              - - \e[0m"

echo -e "\e[94mCompile Program? [y/n]\e[0m"
read answer
echo ""

if [ $answer = "y" ]
	then

	echo -e "\e[0;96mCompiling Java...\e[0m"
	echo "javac -g -d ../compiled/ -Xlint -deprecation -classpath './Jama-1.0.3.jar' *.java"
	javac -g -d ../compiled/ -Xlint -deprecation -classpath "./Jama-1.0.3.jar" *.java

	if [ $? -eq 0 ]
	then
		echo ""
		echo -e "\e[42mJAVA SUCCESS\e[0m"
	else
		echo ""
		echo -e "\e[41mJAVA FAILED\e[0m"
		exit 1
	fi

	echo ""
	echo -e "\e[0;96mCompiling C++... \e[0m"
	cd PhysicsEngine
	make

	if [ $? -eq 0 ]
	then
		echo -e "\e[42mC++ SUCCESS\e[0m"
	else
		echo -e "\e[41mC++ FAILED\e[0m"
		exit 1
	fi

	cd ..
	echo ""
	echo "---> Compilation complete"
	echo ""

fi

echo -e "\e[94mRun the program? [y/n]\e[0m"
read answer

echo ""

if [ $answer = "y" ]
	then

	echo "Starting up project..."

	# echo "-> Clearing images folder of old data"
	# rm ../compiled/img/*.png

	# echo "-> Removing old log file"
	# rm ../compiled/*.txt

	echo ""
	echo -e "\e[94m  Run C program only? [y/n]\e[0m"
	read answer

	if [ $answer = "y" ]
		then
		./PhysicsEngine/o/Driver FILENAME
	else
		clear
		echo "-> Starting Program..."
		echo " vvvvvvvvvvvvvvvvvvvv "
		echo ""
		pwd
		java -ea -cp Jama-1.0.3.jar:../compiled/ austin.structures.Driver 10 1
		if [ $? -eq 0 ]
		then
			echo ""
			echo " ^^^^^^^^^^^^^^^^^^^^ "
			echo -e "\e[42m---> Program run complete...\e[0m"
		else
			echo "\u001B[0m"
			echo " ^^^^^^^^^^^^^^^^^^^^ "
			echo -e "\e[41m---> Program run complete...\e[0m"
		fi
		
	fi

	

fi

echo ""
echo -e "\e[94mGenerate Javadoc? [y/n]\e[0m"
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