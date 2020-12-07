#!/bin/bash

PID=$$
> report.log
echo "Write ' kill -s 9 $PID ' to stop mem.bash"
echo "$PID" > pid.log

array=()
step=0

sh top.sh &

while true; do
	((step++))
	array+=(1 2 3 4 5 6 7 8 9 10)
	if [[ $step -eq 10000  ]]; then
		step=0
		echo ${#array[*]} >> report.log
	fi
done
