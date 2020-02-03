#!/bin/bash
clear;
javac Main.java
if [ "$?" -ne "0" ]; then
	   echo "Compilation error"
   	   exit 1
fi
echo "\n\n"
java Main
echo "\n\n"
ls *.class 
rm *.class
