#!/bin/bash

ps axo pid,command | awk '{if (sub(/sbin/, "")) print $1}'
