call mvn package

if exist %cd%\NovaCore.jar del %cd%\NovaCore.jar

copy %cd%\NovaCore-Spigot\target\NovaCore-Spigot-1.0.0-SNAPSHOT.jar %cd%\NovaCore-Spigot.jar

timeout /T 5