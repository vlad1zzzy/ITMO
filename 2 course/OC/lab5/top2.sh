#!/bin/bash

echo "Write ' kill -s 9 $$ ' to stop top2.sh"

free > helper2.log
(grep "Mem" helper2.log | awk '{print $1 " " $2 " (total)"}') > info2.log
(grep "Swap" helper2.log | awk '{print $1 " " $2 " (total)"}') >> info2.log
(echo -n "Pagesize: " &&  getconf PAGESIZE) >> info2.log
(grep "Mem" helper2.log | awk '{print $1 " " $4 " (free)"}') >> info2.log
(grep "Swap" helper2.log | awk '{print $1 " " $4 " (free)"}') >> info2.log


PID=$(cat pid2.log)
echo "_| MemFree  | SwapFree | MemPercent " >> info2.log
while true; do
	(top -b -n 1 | head -12 | tail -9) > helper2.log
	sleep 1
	memFree=$(grep "MiB\sMem" helper2.log | awk '{print $6}')
	swapFree=$(grep "MiB\sSwap" helper2.log | awk '{print $5}')
	memPercent=$(grep "$PID" helper2.log | awk '{print $10}')
	echo " |  $memFree  |   $swapFree   |    $memPercent"
done >> info2.log
