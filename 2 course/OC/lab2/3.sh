#!/bin/bash

ps axo pid,start,cmd | tail -5 | head -1
