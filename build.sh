#!/bin/bash
set -e
source ./make_targz.sh

JAVA_HOME=$JAVA_HOME_1_6
export JAVA_HOME
PATH=$JAVA_HOME/bin:$PATH
export PATH

MAVEN_HOME=$MAVEN_2_2_1
PATH=$MAVEN_HOME:$PATH
export PATH

mvn -U clean package -P online -Dmaven.test.skip=true

mkdir output
mv target/configcenter-1.0.0.0.war output/rigel-configcenter.war

tar_execute "rigel-configcenter" "war"