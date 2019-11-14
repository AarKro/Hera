#!/bin/bash

# Untrack script in git so local changes to this script won't be staged by git
start=$PWD
script=`dirname "$BASH_SOURCE"`

cd $script
git update-index --skip-worktree env_var.sh
cd $start

# Hera environment variables
export HERA_DB_URL='#DB_URL'
export HERA_DB_USER='#DB_USER'
export HERA_DB_PWD='#DB_PASSWORD'
export HERA_DB_NAME='#DB_NAME'