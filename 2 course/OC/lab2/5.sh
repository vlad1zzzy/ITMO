#!/bin/bash

prevPPID=0
count=0
summ=0
while read line
do
	curPPID=$(echo $line | cut -d ":" -f 2 | grep -Eo '[[:digit:]]+')
	curART=$(echo $line | cut -d ":" -f 3 | grep -Eo '[[:digit:].]+')
	if [[ $curPPID == $prevPPID ]]; then
		echo $line
		((count++))
		summ=$(echo "scale=3; $summ + $curART" | bc -l)
	else
		echo $summ $count
		echo Average_Sleeping_Children_of_ParentID=$prevPPID is $(echo "scale=3; $summ / $count" | bc -l)
		count=1
		summ=$curART
		prevPPID=$curPPID
		echo $line
	fi
done < "ART.log" > "avgART.log"

echo Average_Sleeping_Children_of_ParentID=$prevPPID is $(echo "scale=3; $summ / $count" | bc -l) >> "avgART.log"
