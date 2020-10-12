#!bin/bash

ps -u vlad1zzzy o pid,command > "test.log"
cmds=$(wc -l < "test.log")
((cmds--))
cat "test.log" | tail -n+2 |awk -v ans="${cmds}" 'BEGIN {print ans > "process.log"}{printf("%s : %s\n", $1, $2) > "process.log"}'
