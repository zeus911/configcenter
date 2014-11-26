set java_home=%JAVA_HOME_1_6%
set path=%JAVA_HOME_1_6%\bin;%path%

rd /S /Q WebContent\WEB-INF\lib
md WebContent\WEB-INF\lib

mvn -U clean dependency:copy-dependencies package -Dmaven.test.skip=true

rd /S /Q output
md output
cp target/configcenter-1.0.0.0.war output/configcenter.war