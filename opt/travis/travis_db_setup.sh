#!/bin/bash

# mysql jdbc driverをダウンロードする
mysql_jdbc_ver=5.1.36

mkdir ./tmp
wget http://cdn.mysql.com/Downloads/Connector-J/mysql-connector-java-$mysql_jdbc_ver.zip -P ./tmp
unzip ./tmp/mysql-connector-java-$mysql_jdbc_ver.zip
mv ./tmp/mysql-connector-java-$mysql_jdbc_ver/mysql-connector-java-$mysql_jdbc_ver-bin.jar ./db-manager/dbflute_dfclient/extlib/
rm -rf ./tmp


# DBFluteのreplace-schemaeを使用してDBの初期データをセットアップする
echo "DBFlute Task Start"
expect -c "
set timeout 10
spawn sh ./db-manager/dbflute_dfclient/manage.sh
spawn sh ./db-manager/dbflute_dfclient/manage.sh
expect \"What is your favorite task? (number):\"
send \"0\n\"
expect \"*df-replace-schema* Are you ready? (y or n):\"
send \"y\n\"
expect \"BUILD SUCCESSFUL\"
send \"echo complete\n\"
"
