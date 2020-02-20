#!/bin/bash

javac Banker.java
if [ "$?" -ne "0" ]; then
	   echo "Compilation error"
   	   exit 1
fi
java Banker
echo -e "\n"
ls *.class 
rm *.class
