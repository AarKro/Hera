#!/bin/bash

# set environment variables
source env_var.sh

echo "Enter your mysql password"
echo "It will be saved in an encrypted password file and stored on your PC"
echo "The setup_db.sh script uses this password file to log into mysql"
echo "For more information, look up 'mysql_config_editor'"
echo "You need to run this script only once :)"

mysql_config_editor set --login-path=local --host=localhost --user=$HERA_DB_USER --password

echo "mysql password encrypted and saved"