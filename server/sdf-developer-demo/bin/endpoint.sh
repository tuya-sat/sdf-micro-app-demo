#!/bin/bash

CURRENT_DIR=`dirname $0`
API_HOME=`cd "$CURRENT_DIR/.." >/dev/null; pwd`
Jar=`ls $API_HOME/lib/sdf-developer-demo-1.0.0-*.jar`
RETVAL="0"
LOG="api_stdout.log"

base_param=""

if [ "$PORT" != "" ]
then
  base_param=${base_param}" -Dserver.port"=$PORT
fi

if [ "$REDIS_HOST" != "" ]
then
  base_param=${base_param}" -Dredis.highway.datasource.host"=$REDIS_HOST
fi

if [ "$REDIS_PASSWORD" != "" ]
then
  base_param=${base_param}" -Dredis.highway.datasource.password"=$REDIS_PASSWORD
fi

if [ "$REDIS_PORT" != "" ]
then
  base_param=${base_param}" -Dredis.highway.datasource.port"=$REDIS_PORT
fi

echo $base_param
echo $Jar

# run java application
cd $API_HOME
java $base_param \
-jar $Jar >> $API_HOME/logs/$LOG 2>&1