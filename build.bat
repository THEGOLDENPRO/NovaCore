@echo off
call mvn package

if exist %cd%\NovaCore.jar del %cd%\NovaCore.jar

copy %cd%\NovaCore\target\NovaCore-1.0.0-SNAPSHOT.jar %cd%\NovaCore.jar

pause