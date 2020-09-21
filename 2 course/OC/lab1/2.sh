#!/bin/bash
read str
while [[ "$str" != "q" ]]
do
	line="$line$str"
	read str
done
echo "$line"
