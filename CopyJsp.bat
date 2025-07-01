@echo off
setlocal enabledelayedexpansion
set "web_apps=C:\apache-tomcat-10.1.28\webapps"
set "war_name=mon-projet-springfilm"
set "views=%web_apps%\%war_name%\WEB-INF"

set "work_dir=C:\ITU\S4\Spring\springManyToMany"
set "work_dir_views=%work_dir%\src\main\webapp\WEB-INF\views\"


xcopy /s /y "%work_dir_views%\*.*" "%views%"
xcopy /s /y  "%work_dir%\src\main\webapp\index.jsp" "%web_apps%\%war_name%\"
echo 3

