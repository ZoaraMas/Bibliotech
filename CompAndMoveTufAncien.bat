@echo off
setlocal enabledelayedexpansion

:: Variables
set "work_dir=C:\Users\Titix\Documents\ITU\S4\SERVLET2\transaction"
set "build=%work_dir%\build"
set "web=%work_dir%\webapp"
set "web_xml=%web%\WEB-INF\web.xml"
set "lib=%work_dir%\lib"
set "web_apps=C:\tomcat-10.1.28-windows-x64\apache-tomcat-10.1.28\webapps"
set "war_name=veilleExamen"
set "src=%work_dir%\src"
set "java_home=C:\Program Files\Java\jdk-17\bin\javac.exe"

set "assets=%work_dir%\assets"
set "images=%assets%\images"

set "deployed_project=%web_apps%\%war_name%"

:: Delete the [build] folder
if exist "%build%" (
    rd /s /q "%build%"
)
echo "a";
:: Step 1
:: Create folder structure
mkdir "%build%\WEB-INF\lib\"
echo 1

:: Step 2
mkdir "%build%\WEB-INF\classes\"
echo 2

:: Copy the content of [web] in the [build]
xcopy /s /y "%web%\*.*" "%build%"
echo 3

echo "copy the html to build"
:: Copy the html to the build
xcopy /i "%work_dir%\*.html" "%build%"
pause

:: Step 4
:: Copy [web_xml] to [build] + "\WEB-INF"
copy "%web_xml%" "%build%\WEB-INF"
echo "b";

:: Copy the jsp files to build/
@REM xcopy /s /i "%web%\*.jsp" "%build%\JSP"

:: Copy the jar files in [lib] into [build] + "\WEB-INF\lib"
xcopy /s /i "%lib%\*.jar" "%build%\WEB-INF\lib"

:: Copy the assets to build
@REM mkdir "%build%\WEB-INF\assets\images"
@REM xcopy /s /y "%images%\*.*" "%build%\assets\images\"


echo "f";

:: Step 2
:: Create a list of all .java files in the repository src and its subRepositories
 dir /s /B "%src%\*.java" > sources.txt
:: Create a list of all .jar files in the repository lib and its subRepositories
dir /s /B "%lib%\*.jar" > libs.txt

echo "g";

:: Build the classPath
set "classpath="
for /F "delims=" %%i in (libs.txt) do set "classpath=!classpath!%%i;"

:: Execute the javac command
::javac -d "%build%\WEB-INF\classes" @sources.txt

@REM "%java_home%" -parameters -d "%build%\WEB-INF\classes" -cp "%lib%\*.jar" @sources.txt
"%java_home%" -parameters -d "%build%\WEB-INF\classes" -cp "%classpath%" @sources.txt
@REM javac -d "%build%\WEB-INF\classes" -cp "%lib%\*.jar" @sources.txt
echo "d";

:: Delete all files sources.txt and libs.txt after compilation
del sources.txt
del libs.txt


:: Step 5
:: Create a file .war named [war_name].war from the folder [build] and its content
cd "%build%"
jar cf "%work_dir%\%war_name%.war" *
echo "jar created";
pause
:: Step 6
:: Delete the file xwar in [web_apps] if it exists
if exist "%web_apps%\%war_name%.war" (
    echo "war found"
    del /f /q "%web_apps%\%war_name%.war"
)

:: Copy the .war file into [web_apps]
copy /y "%work_dir%\%war_name%.war" "%web_apps%"

@REM del "%work_dir%\%war_name%.war"
echo "f";



del /F /Q "%build%"
echo Deployement is successful
echo "g";

pause