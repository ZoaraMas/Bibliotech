@echo off
@REM mvn clean package
setlocal enabledelayedexpansion

:: Variables
set "work_dir=C:\ITU\S4\Spring\Bibliotech"
set "web_apps=C:\apache-tomcat-10.1.28\webapps"
set "war_name=Bibliotech"
set "java_home=C:\Program Files\Java\jdk-17\bin\javac.exe"

set "assets=%work_dir%\assets"
set "images=%assets%\images"

if exist "%web_apps%\%war_name%.war" (
    echo "war found"
    del /f /q "%web_apps%\%war_name%.war"
) else echo "war not found " + "%web_apps%\%war_name%.war"
:: Copy the .war file into [web_apps]
copy /y "%work_dir%\target\%war_name%.war" "%web_apps%"
echo "after copy"
del "%work_dir%\target\%war_name%.war"
echo Deployement is successful