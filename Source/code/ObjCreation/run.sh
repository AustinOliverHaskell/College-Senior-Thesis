echo "Compiling ... "
javac -g -Xlint -cp "Jama-1.0.3.jar" *.java 
echo "Running   ... "
java -ea -cp "Jama-1.0.3.jar:." Driver
echo "Done, Press any key to continue ... "
read c
clear