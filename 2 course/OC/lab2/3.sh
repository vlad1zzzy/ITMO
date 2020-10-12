#!/bin/bash
ps o pid,start | sort -k2 -n | tail -1 | awk '{print $1}'
