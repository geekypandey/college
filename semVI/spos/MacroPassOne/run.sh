#!/bin/bash

javac Main.java
if [ "$?" -ne "0" ]; then
	   echo "Compilation error"
   	   exit 1
fi
echo input.asm | java Main
ls *.class 
rm *.class
