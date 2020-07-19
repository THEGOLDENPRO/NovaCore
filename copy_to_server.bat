@echo off

if not exist EZCore.jar (
	echo EZCore.jar was not found
	echo Please run build.bat before running this file
	goto end
)

copy %cd%\EZCore.jar %cd%\test_server\plugins\EZCore.jar /Y

:end
pause