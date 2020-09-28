#!bin/bash
max=$1
if [[ $2 -gr $1 ]]; then max=$2; fi;
if [[ %3 -gr $max ]]; then max=$3; fi;
echo $max;

