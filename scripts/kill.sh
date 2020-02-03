#!/bin/bash

JAR_NAME=smart_watch_server.jar
out=`ps -ef | grep java | grep $JAR_NAME | grep -v grep | awk '{print $2}'`
processes=`ps -ef | grep java | grep $JAR_NAME | grep -v grep | awk '{print $2'} | wc -l`

if [[ $processes -gt 0 ]]
then
   echo Killing $JAR_NAME  process: $out
   kill $out
else
   echo No $JAR_NAME  processes found...
fi