sbt clean compile

dir=models/dao/;
echo checking and making models directory
[ ! -e ../../project-odin/app/$dir ] && mkdir -p ../../project-odin/app/$dir


cp target/scala-2.11/src_managed/main/models/dao/Tables.scala ../../project-odin/app/models/dao/

echo Generated!
