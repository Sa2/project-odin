sbt clean compile

dir=models/dao/;
components_dir=../../app/components/;

echo checking and making models directory
[ ! -e ../../app/project-odin/app/${dir} ] && mkdir -p ../../app/project-odin/app/${dir}
[ ! -e ${components_dir}odin-user-service/app/${dir} ] && mkdir -p ${components_dir}odin-user-service//app/${dir}
[ ! -e ${components_dir}odin-auth-service/app/${dir} ] && mkdir -p ${components_dir}odin-auth-service//app/${dir}


cp target/scala-2.11/src_managed/main/models/dao/Tables.scala ../../app/project-odin/app/models/dao/
cp target/scala-2.11/src_managed/main/models/dao/Tables.scala ${components_dir}odin-user-service/app/models/dao/
cp target/scala-2.11/src_managed/main/models/dao/Tables.scala ${components_dir}odin-auth-service/app/models/dao/

echo Generated!
