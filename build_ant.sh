#!/bin/bash
set -e

#uncomment following lines and set the right value if java/ant are not in PATH
#JAVA_HOME=$JAVA_HOME_1_6
#export JAVA_HOME
#PATH=$JAVA_HOME/bin:$PATH
#export PATH
#ANT_HOME=$ANT_HOME
#PATH=$ANT_HOME/bin:$PATH
#export PATH

ant dist -f build.xml