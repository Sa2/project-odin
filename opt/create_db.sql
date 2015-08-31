# travisユーザー作成
create user travis@localhost;
GRANT ALL PRIVILEGES ON *.* TO travis@localhost;

# odinユーザー作成
CREATE USER odin@localhost;
GRANT ALL PRIVILEGES ON *.* TO odin@localhost IDENTIFIED BY 'odin' WITH GRANT OPTION;
FLUSH PRIVILEGES;
SET PASSWORD FOR odin@localhost = PASSWORD('odin');

CREATE DATABASE gungnir_db CHARACTER SET utf8;
