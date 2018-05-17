#!/bin/sh

procNo=`ps ax | grep java | grep app.name=JDSpider  | grep -v grep | awk '{print $1}'`
if [ $procNo ]; then
  ps ax | grep java | grep app.name=JDSpider | grep -v grep | awk '{print $1}' | xargs kill -SIGTERM
fi
echo 'JDspider is stopping ...'
while :
do
    procNo=`ps ax | grep java | grep app.name=JDSpider  | grep -v grep | awk '{print $1}'`
    if [ ! $procNo ]; then
        echo 'JDSpider is stopped'
        nohup java \
          -Djava.net.preferIPv4Stack=true \
          -Dapp.name=JDSpider \
          -Xms1024m -Xmx1024m \
          -jar spring-boot-quartz-0.0.1-SNAPSHOT.jar > nohup.out 2>&1 &
        exit
    else
        sleep 1
    fi
done
