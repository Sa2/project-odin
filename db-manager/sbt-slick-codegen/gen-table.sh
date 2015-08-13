sbt clean compile

modeldir=models
daodir=dao

echo "models directory checking..."
if [ -e  ../../project-odin/app/$modeldir ]; then
  echo "$modeldir found"
else
  mkdir ../../project-odin/app/$modeldir
  echo "$modeldir maked"
fi
echo "dao directory checking..."
if [ -e  ../../project-odin/app/$modeldir/$daodir ]; then
  echo "$daodir found"
else
  mkdir ../../project-odin/app/$modeldir/$daodir
  echo "$daodir maked"
fi

cp target/scala-2.11/src_managed/main/models/dao/Tables.scala ../../project-odin/app/models/dao/

echo Generated!
