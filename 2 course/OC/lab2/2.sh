#!/bin/bash

ps axo pid,command | awk '{print $1 " " $2}' | awk '{if (sub(/sbin/, "sbin")) print $0}'

