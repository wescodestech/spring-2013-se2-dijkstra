#!/bin/bash

# Created by Matthew A. Crist on March 4, 2013.

# This file will be responsible for opening a port that will be able to 
# listen for incoming connections and save that information to the 
# global incoming folder.

KILL_SERVICE=true

for(( ; ; ))
do
	FILENAME=`date +%s`".rmi"
	nc -l 20000 > $FILENAME
	
	echo "Connection accepted.  Data written to $FILENAME"	

	ACTIONSTRING=$(grep ^{.*} $FILENAME)

	OLDIFS=$IFS
	IFS=";"

	read -a actions <<< "$(printf "%s" "$ACTIONSTRING")"


	for((i=0; i<${#actions[@]}; i++))
	do
		IFS="="
		read -a action <<< "$(printf "%s" "${actions[i]//[\{\} ]/}")"

		echo ${action[0]}
		echo ${action[1]}
	done

	IFS=$OLDIFS

	if($KILL_SERVICE)
	then
		break
	fi
done
