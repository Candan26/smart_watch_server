#!/bin/bash

BASE_PATH = /home/localFolder/smart_watch_server
BIN_BASE_PATH = $BASE_PATH/bin
CONF_PATH = $BASE_PATH/conf
JAR_NAME =smart_watch_server.jar
NOHUP_OUT_PATH =./logs/nohup.out

echo "INFO: Base path:['$BASE_PATH'] bin path:['$BIN_PATH'] conf path:['$CONF_PATH']"

if ps -ef | grep "$JAR_NAME" | grep -v "grep" >/dev/null
then
  echo "Process $JAR_NAME is already running!"
else
  nohup java -d64 -Xmx1024m -Xms1024m -Dfile.encoding=UTF-8 -jar $BIN_PATH/$JAR_NAME $CONF_PATH/log4j.xml $CONF_PATH/ 2>&1 >> $NOHUP_OUT_PATH&
fi