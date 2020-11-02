#!/bin/bash
nice -n 10 sh 4.gen.sh & pid1=$!
#sudo cpulimit -l 10 /home/vlad1zzzy/lab3/4.gen.sh &
sh 4.gen.sh & pid2=$!
sh 4.gen.sh & pid3=$!

#renice +10 $pid1

echo -e "1 : $pid1\n2 : $pid2\n3 : $pid3"

kill -s 9 $pid3
