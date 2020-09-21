#!/bin/bash
grep -E -h -s -r -o "[[:alnum:]_.]+@[[:alnum:]_]+\.[[:alpha:]]+" /etc | sort -u | awk '{printf("%s, ", $1)}' > emails.lst
