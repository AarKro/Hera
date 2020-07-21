#!/bin/bash

start=$PWD
script=`dirname "$BASH_SOURCE"`
cd $script

# set environment variables
source env_var.sh

cd ../sql

commands="deployment.sql"

echo "DROP DATABASE IF EXISTS $HERA_DB_NAME;" > $commands
echo "CREATE DATABASE $HERA_DB_NAME;" >> $commands
echo "USE $HERA_DB_NAME;" >> $commands
cat tables/* >> $commands
cat data.sql >> $commands
cat constraints.sql >> $commands
cat views.sql >> $commands

cd $start