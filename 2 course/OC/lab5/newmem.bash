#!/bin/bash

PID=$$
N=$1
echo "Write ' kill -s 9 $PID ' to stop mem.bash"
#echo "$PID" > pid.log

array=()

#sh topNew.sh &

while [[ ${#array[*]} -le $N ]]; do
	array+=(1 2 3 4 5 6 7 8 9 10)
done

count=$(cat ended.log)
(( count++ ))
echo $count > ended.log

echo "Process $PID ended"
