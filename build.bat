call mvn clean package

if exist %cd%\NovaCore-Spigot.jar del %cd%\NovaCore-Spigot.jar
if exist %cd%\NovaCore-Bungeecord.jar del %cd%\NovaCore-Bungeecord.jar
if exist %cd%\NovaCore-GameEngine.jar del %cd%\NovaCore-GameEngine.jar
if exist %cd%\NovaUtils.jar del %cd%\NovaUtils.jar

copy %cd%\NovaCore-Spigot\target\NovaCore-Spigot-1.1.0-SNAPSHOT.jar %cd%\NovaCore-Spigot.jar
copy %cd%\NovaCore-Bungeecord\target\NovaCore-Bungeecord-1.0.0-SNAPSHOT.jar %cd%\NovaCore-Bungeecord.jar
copy %cd%\NovaCore-GameEngine\target\novacore-gameengine-1.0.0-SNAPSHOT.jar %cd%\NovaCore-GameEngine.jar
copy %cd%\NovaUtils\target\NovaUtils-1.0.0-SNAPSHOT.jar %cd%\NovaUtils.jar
copy %cd%\NovaCore-GameMapDesignToolkit\target\GameMapDesignToolkit-1.0.0-SNAPSHOT.jar %cd%\GameMapDesignToolkit.jar

timeout /T 5