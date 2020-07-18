@echo off
call mvn package

if exist %cp%\EZCore.jar del %cp%\EZCore.jar

copy %cd%\EZCore\target\ezcore-1.0.0-SNAPSHOT.jar %cd%\EZCore.jar

pause