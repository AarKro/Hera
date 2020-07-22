#!/bin/bash

#######################################################################################
# THIS SCRIPT IS AUTOMATICALLY TRIGGERED BY AWS CODE DEPLOY                           #
# AFTER THE HERA JAR IS BUILT AND UPLOADED TO EC2 AND RUNS ON                         #
# THE EC2 INSTANCE ITSELF                                                             #
#                                                                                     #
# THIS SCRIPT WILL NOT WORK LOCALLY AND WAS NEVER INTENDED TO DO SO                   #
#######################################################################################

cd /home/ec2-user

# Terminate Hera
pkill -f 'java -jar bots/hera-prod-bundle-jar-with-dependencies.jar'

echo "$(date): starting hera" >> logs/hera-start.log

java -jar bots/hera-prod-bundle-jar-with-dependencies.jar
