@echo off

if not exist "%JAVA_HOME%\bin\java.exe" echo Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better! & EXIT /B 1
set "JAVA=%JAVA_HOME%\bin\java.exe"

setlocal enabledelayedexecloudsion

set BASE_DIR=%~dp0
rem added double quotation marks to avoid the issue caused by the folder names containing spaces.
rem removed the last 5 chars(which means \bin\) to get the base DIR.
set BASE_DIR="%BASE_DIR:~0,-5%"

set CUSTOM_SEARCH_LOCATIONS=file:%BASE_DIR%/conf/

set SERVER=e-cloud-server

set "ECLOUD_JVM_OPTS=-Xms512m -Xmx512m -Xmn256m"

rem set ecloud server options
set "ECLOUD_OPTS=%ECLOUD_OPTS% -jar %BASE_DIR%\target\%SERVER%.jar"

rem set ecloud server spring config location
set "ECLOUD_CONFIG_OPTS=--spring.config.additional-location=%CUSTOM_SEARCH_LOCATIONS%"

rem set ecloud server log4j file location
set "ECLOUD_LOG4J_OPTS=--logging.config=%BASE_DIR%/conf/e-cloud-server-logback.xml"


set COMMAND="%JAVA%" %ECLOUD_JVM_OPTS% %ECLOUD_OPTS% %ECLOUD_CONFIG_OPTS% %ECLOUD_LOG4J_OPTS% ecloud.server %*

echo "ecloud server is starting..."
rem start ecloud server command
%COMMAND%
echo "ecloud server is started!"
