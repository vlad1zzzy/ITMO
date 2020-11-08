#!/bin/bash

read pid

ppid=$(cat /proc/$pid/status | awk '{if (sub(/PPid/, " /PPid/")) print $2}')

cat /proc/$ppid/status | awk '{if (sub(/Name/, " /Name/")) print $2}'
