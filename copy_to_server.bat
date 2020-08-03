@echo off

if not exist NovaCore.jar (
	echo NovaCore.jar was not found
	echo Please run build.bat before running this file
	goto end
)

copy %cd%\NovaCore.jar %cd%\test_server\plugins\NovaCore.jar /Y

:end
pause