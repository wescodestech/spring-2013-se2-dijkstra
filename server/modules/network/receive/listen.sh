#!/bin/sh

# Created by Matthew A. Crist on March 4, 2013.

# This file will be responsible for opening a port that will be able to 
# listen for incoming connections and save that information to the 
# global incoming folder.

FILENAME=`date +%s`".txt"

nc -l 20000 > $FILENAME

CONTENTS=$(cat $FILENAME)

echo $CONTENTS
