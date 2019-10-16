#!/bin/bash

cd ~/st-notifier/st-notifier-lightwall
git pull
mvn clean install
cd target
java -Dspring.profiles.active=default,gpio -jar st-notifier-lightwall-1.0-SNAPSHOT.jar

