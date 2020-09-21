#!/bin/bash
sudo awk '{if (sub(/WW/, "Warning") || (sub(/II/, "Information"))) {print > "full.log"}}' /var/log/anaconda/X.log
