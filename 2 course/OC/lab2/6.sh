#!/bin/bash
maxSize=0
PID=-1

for pid in $(ps axo pid)
do
	if [[ !(-f "/proc/$pid/status") ]]; then continue; fi
	size=$(cat /proc/$pid/status | grep -i VMSIZE | grep -Eo "[[:digit:]]+")
	if [[ $size -gt $maxSize ]]; then
		maxSize=$size
		PID=$pid
	fi
done
echo PID \: $PID  \|  VIRT \: $maxSize
