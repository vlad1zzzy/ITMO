#!/bin/bash

while true;
do
	read LINE
	echo "$LINE" >> 5.help
	if [[ "$LINE" == "QUIT" ]]; then
		echo "Quit"
		exit 0
	fi
	
	if [[ ! "$LINE" =~ [0-9]+ && "$LINE" != "+" && "$LINE" != "*" ]]; then
		echo "Invalid input"
		exit 1
	fi
done
