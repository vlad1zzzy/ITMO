#!/bin/bash

echo "2" >> task.help3
while true;
do
	read LINE
	if [[ "$LINE" =~ [0-9]+ || "$LINE" == "QUIT" ]]; then
		echo "$LINE" >> task.help3
		if [[ "$LINE" == "QUIT" ]]; then
			exit
	fi
	fi
	
	case $LINE in
		"+")
			kill -USR1 $(cat task.help2);;
		"*")
			kill -USR2 $(cat task.help2);;
		"TERM")
			kill -TERM $(cat task.help2)
			exit;;
	esac
done

