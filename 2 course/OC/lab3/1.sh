#!/bin/bash
dt=$(date +"%m:%d:%Y_%H:%M:%S")
mkdir ~/test && echo "catalog test was created successfully" > ~/report && touch ~/test/$dt
ping -c 1 "www.net_nikogo.ru" 2> error.tmp || echo $dt "error" >> ~/report
