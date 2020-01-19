#!/bin/bash

# set environment variables
source env_var.sh

mysql_config_editor set --login-path=local --host=localhost --user=$HERA_DB_USER --password

echo "mysql password encrypted and saved"