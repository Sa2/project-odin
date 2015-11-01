sbt clean compile

dir=models/dao/;
echo checking and making models directory
[ ! -e ../../application/project-odin/app/$dir ] && mkdir -p ../../application/project-odin/app/$dir
[ ! -e ../../application/odin-user-service/app/$dir ] && mkdir -p ../../application/odin-user-service//app/$dir


cp target/scala-2.11/src_managed/main/models/dao/Tables.scala ../../application/project-odin/app/models/dao/
cp target/scala-2.11/src_managed/main/models/dao/Tables.scala ../../application/odin-user-service/app/models/dao/

echo Generated!
