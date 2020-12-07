#!/bin/bash

N=5000000

echo "0" > ended.log

for i in {1..10}; do
	sh newmem.bash $N &
	sleep 1
done
