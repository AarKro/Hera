#!/bin/bash

commands=$(mktemp)
trap "rm -f $commands" 0 2 3 15

dir=`dirname "$BASH_SOURCE"`

echo "DROP DATABASE IF EXISTS $HERA_DB_NAME;" >> $commands
echo "CREATE DATABASE $HERA_DB_NAME;" >> $commands
echo "USE $HERA_DB_NAME;" >> $commands
cat $dir/tables/* $dir/constraints.sql >> $commands

mysql -u $HERA_DB_USER -p$HERA_DB_PWD < $commands