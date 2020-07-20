#!/bin/bash

#######################################################################################
# THIS SCRIPT IS AUTOMATICALLY TRIGGERED BY AWS CODE DEPLOY                           #
# AFTER THE HERA JAR IS BUILT AND UPLOADED TO EC2 AND RUNS ON                         #
# THE EC2 INSTANCE ITSELF                                                             #
#                                                                                     #
# THIS SCRIPT WILL NOT WORK LOCALLY AND WAS NEVER INTENDED TO DO SO                   #
#######################################################################################

cd /home/ec2-user

# Terminate running jar processes as in terminating Hera
pkill -f 'java -jar'

# Close all running screen sessions
pkill screen

echo "$(date): starting hera"

screen -dmS hera bash -c 'java -jar bots/hera-prod-bundle-jar-with-dependencies.jar'
