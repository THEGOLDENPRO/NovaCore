call mvn package

if exist %cd%\NovaCore-Spigot.jar del %cd%\NovaCore-Spigot.jar
if exist %cd%\NovaCore-Bungeecord.jar del %cd%\NovaCore-Bungeecord.jar

copy %cd%\NovaCore-Spigot\target\NovaCore-Spigot-1.0.0-SNAPSHOT.jar %cd%\NovaCore-Spigot.jar
copy %cd%\NovaCore-Bungeecord\target\NovaCore-Bungeecord-1.0.0-SNAPSHOT.jar %cd%\NovaCore-Bungeecord.jar

timeout /T 5