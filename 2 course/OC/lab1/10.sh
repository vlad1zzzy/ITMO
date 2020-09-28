#!/bin/bash
man bash | grep -h -s -o -w '\w\{4,\}' | tr "[:upper:]" "[:lower:]" | sort | uniq -c | sort -n | tail -3 | awk '{print $2}'
