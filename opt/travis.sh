#!/bin/bash

echo "DBFlute Task Start"
expect -c "
set timeout 10
spawn sh ../db-manager/dbflute_dfclient/manage.sh
expect \"What is your favorite task? (number):\"
send \"0\n\"
expect \"*df-replace-schema* Are you ready? (y or n):\"
send \"y\n\"
expect \"BUILD SUCCESSFUL\"
send \"echo complete\n\"
"
