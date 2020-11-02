#!/bin/bash

echo $$ > 6.help
A=1
sum() {
	MODE="+"
}
mul() {
	MODE="*"
}
end() {
	MODE="END"
}
trap 'sum' USR1
trap 'mul' USR2
trap 'end' TERM
while true
do
	case $MODE in
		"+")
			A=$(($A + 2));;
		"*")
			A=$(($A * 2));;
		"END")
			echo "Stopped by SIGTERM"
			exit;;
	esac
	echo "current res : $A"
	sleep 1		
done
