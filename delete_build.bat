REM This file is used to delete the jars from the build
@echo off
if exist %cd%\NovaCore-Spigot.jar del %cd%\NovaCore-Spigot.jar
if exist %cd%\NovaCore-Bungeecord.jar del %cd%\NovaCore-Bungeecord.jar
if exist %cd%\NovaCore-GameEngine.jar del %cd%\NovaCore-GameEngine.jar
if exist %cd%\NovaUtils.jar del %cd%\NovaUtils.jar
if exist %cd%\GameMapDesignToolkit.jar del %cd%\GameMapDesignToolkit.jar