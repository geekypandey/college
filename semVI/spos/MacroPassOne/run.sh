#!/bin/bash

javac Main.java
if [ "$?" -ne "0" ]; then
	   echo "Compilation error"
   	   exit 1
fi
java Main
echo "\n"
ls *.class 
rm *.class
