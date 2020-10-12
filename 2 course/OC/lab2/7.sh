#!/bin/bash

for pid in $(ps axo pid)
do
	if [[ !(-f "/proc/$pid/io") ]]; then continue; fi
	readed=$(sudo cat /proc/$pid/io | grep -i rchar | grep -Eo "[[:digit:]]+")
	echo "$pid $readed"
done >> "READED.log"

sleep 1

while read line
do
	pid=$(echo "$line" | cut -d' ' -f1)
	readed=$(echo "$line" | cut -d' ' -f2)
	if [[ !(-f "/proc/$pid/io") ]]; then continue; fi
	readedAfterMinute=$(sudo cat /proc/$pid/io | grep -i rchar | grep -Eo "[[:digit:]]+")
	diff=$(echo "scale=1; $readedAfterMinute - $readed" | bc )
	cmd=$(ps -p $pid -o cmd --no-headers)
	echo "$pid $diff  $cmd" >> "READEDinMINUTE.log"
done < "READED.log"

sort -nru -k2 "READEDinMINUTE.log" | head -3 | awk '{printf("%s : %s : %s\n", $1, $2, $3)}'
