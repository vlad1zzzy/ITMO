#!/bin/bash

echo "Write ' kill -s 9 $$ ' to stop topNew.sh"

free > helper.log
(grep "Mem" helper.log | awk '{print $1 " " $2 " (total)"}') > info.log
(grep "Swap" helper.log | awk '{print $1 " " $2 " (total)"}') >> info.log
(echo -n "Pagesize: " &&  getconf PAGESIZE) >> info.log
(grep "Mem" helper.log | awk '{print $1 " " $2 " (free)"}') >> info.log
(grep "Swap" helper.log | awk '{print $1 " " $2 " (free)"}') >> info.log


PID=$(cat pid.log)
echo "_| MemFree  | SwapFree | MemPercent " >> info.log
while true; do
	(top -b -n 1 | head -12 | tail -9) > helper.log
	sleep 1
	memFree=$(grep "MiB\sMem" helper.log | awk '{print $6}')
	swapFree=$(grep "MiB\sSwap" helper.log | awk '{print $5}')
	memPercent=$(grep "$PID" helper.log | awk '{print $10}')
	echo " |  $memFree  |   $swapFree   |    $memPercent"
done >> info.log
