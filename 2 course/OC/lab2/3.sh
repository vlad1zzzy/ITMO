#!/bin/bash

#ps axo pid,stime,cmd > 3.help
#cat 3.help | tail -3 | head -1 | awk '{print $0}'

ps -A o pid,ppid,stime,cmd > 3.help
tac 3.help > 3.helpr
ppid=-1
while read line
do
	curpid=$(echo $line | awk '{print $1}')
	curppid=$(echo $line | awk '{print $2}')
	curcmd=$(echo $line | awk '{print $4}')
	echo $curcmd
	if [[ ppid -ne -1 && ppid -ne $curppid ]]; then
		pid=$curpid
		break
	fi
	if [[ $curcmd == "sh" ]]; then
		ppid=$curppid
	fi
done < 3.helpr

echo $pid
