#!/bin/bash

ps axo pid,command | awk '{print $1 " " $2}' | awk '{if (sub(/\s\/sbin\//, " /sbin/")) print $0	}'

