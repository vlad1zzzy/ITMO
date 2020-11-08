#!/bin/bash

echo $$ > task.help2
A=1
MODE="+"
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
	B=$(cat task.help3 | tail -1)
	if [[ "$B" == "QUIT" ]]; then
		echo "Quit"
		exit
	fi
	case $MODE in
		"+")
			A=$(($A + $B));;
		"*")
			A=$(($A * $B));;
		"END")
			echo "Stopped by SIGTERM"
			exit;;
	esac
	echo "current res : $A"
	sleep 1		
done
