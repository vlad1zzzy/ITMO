#!/bin/bash

A=1
MODE="+"
(tail -n 1 -f 5.help) |
while true
do
	read LINE
	case $LINE in
		"+")
			echo "ADD"
			MODE="+";;
		"*")
			echo "MUL"
			MODE="*";;
		[0-9]*)
			echo "$A $MODE $LINE"
			case $MODE in
				"+")
					A=$(($A + $LINE));;
				"*")
					A=$(($A * $LINE));;
			esac
			echo "current res : $A"
			;;
		"QUIT")
			echo "Quit"
			exit 0;;
		*)
			echo "Invalid input"
			exit 1;;
	esac
done
