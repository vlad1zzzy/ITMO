#!bin/bash

lines=$(ps -u vlad1zzzy | wc -l)
cmds=$(($lines-1))

ps -u vlad1zzzy o pid,command | tail -n+2 | awk -v ans="${cmds}" 'BEGIN {print ans > "process.log"}{printf("%s : %s\n", $1, $2) > "process.log"}'

