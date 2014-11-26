@echo off
set CURRENT_DIR=%~dp0

echo %CURRENT_DIR%
if "%JAVA_HOME%" == "" goto noJavaHome
set _JAVA_CMD=%JAVA_HOME%\bin\java
goto run

:noJavaHome
set _JAVA_CMD=C:\Program Files\Java\jdk1.5.0_05\bin\java.exe

:run
"%_JAVA_CMD%" -Djava.ext.dirs="%CURRENT_DIR%WebContent\WEB-INF\lib" -cp "%CURRENT_DIR%WebContent\WEB-INF\classes" com.baidu.rigel.platform.dao.tools.SqlMappingExporter

:end
set _JAVA_CMD=
set MYCLASSPATH=

echo "file export success: %CURRENT_DIR%sql_exporter.txt" 
pause

@echo on