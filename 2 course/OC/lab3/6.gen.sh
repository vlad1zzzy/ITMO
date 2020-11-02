#!/bin/bash

while true
do
	read LINE
	case $LINE in
		"+")
			kill -USR1 $(cat 6.help);;
		"*")
			kill -USR2 $(cat 6.help);;
		"TERM")
			kill -TERM $(cat 6.help)
			exit;;
	esac
done		
