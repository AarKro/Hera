#!/bin/bash

start=$PWD
script=`dirname "$BASH_SOURCE"`
cd $script

# set environment variables
source env_var.sh

cd ../sql

commands=$(mktemp)
trap "rm -f $commands" 0 2 3 15

echo "DROP DATABASE IF EXISTS $HERA_DB_NAME;" > $commands
echo "CREATE DATABASE $HERA_DB_NAME;" >> $commands
echo "USE $HERA_DB_NAME;" >> $commands
cat tables/* >> $commands
cat data.sql >> $commands
sed -i -e "s/\#DISCORD_LOGIN_TOKEN/$HERA_LOGIN_TOKEN/g" $commands
sed -i -e "s/\#YOUTUBE_API_TOKEN/$HERA_YOUTUBE_TOKEN/g" $commands
sed -i -e "s/\#YOUTUBE_API_APP_NAME/$HERA_YOUTUBE_APP_NAME/g" $commands
cat constraints.sql >> $commands

mysql -u $HERA_DB_USER -p$HERA_DB_PWD < $commands

cd $start