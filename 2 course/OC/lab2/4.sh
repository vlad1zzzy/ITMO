#!/bin/bash


for pid in $(ps axo pid)
do
	if [[ pid -eq "PID" ]]; then continue; fi

	PPidDir="/proc/"$pid"/status"
	ARTDir="/proc/"$pid"/sched"
	if [[ !(-f $PPidDir || -f $ARTDir) ]]; then continue; fi

	eval $(awk '{if (sub (/PPid/, "")) {print "ppid="$2} }' $PPidDir)
	eval $(awk '{if (sub (/se.sum_exec_runtime/, "")) {print "sum="$2} }' $ARTDir)
	eval $(awk '{if (sub (/nr_switches/, "")) {print "switch="$2} }' $ARTDir)
	
	art=$(awk -v s=$sum -v sw=$switch 'BEGIN { print s / sw}')

	echo -e "ProcessId=$pid : Parent_ProcessID=$ppid : Average_Running_Time=$art"
done | sort -nt "=" -k3 > "ART.log"
