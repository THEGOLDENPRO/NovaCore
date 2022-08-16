# NovaCore
A plugin with a lot of useful features to make plugins with less code and better compatibility between versions

Downloads can be found here https://jenkins.novauniverse.net/job/NovaCore/

<br>

## 📔 Documentation

- ### [Nova Game Engine](https://github.com/NovaUniverse/NovaCore/tree/master/NovaCore-GameEngine)
  - [Starting and Using the game lobby module.](#-using-the-game-lobby-module)
    - [Maps and starting.](#--maps-and-starting)
  - [Loot Tables](#-loot-table-config)
    - [Adding items.](#adding-items)
    - [Random item amount.](#-random-item-amount)
    - [Custom item names.](#-custom-item-names)
    - [Enchants](#-enchants)
    - [Enchanted books.](#-enchanted-books)
    - [Tracker compasses.](#-tracker-compasses)
  - [Map Data]()
  - [Sending Players Back.]()
  
<br>

## 🡺 Using the game lobby module

### Setting it up

The following code shows how to load the game lobby module, load maps and setting up a random selector

```java
// Enable the module
ModuleManager.require(GameLobby.class);

// Paths for the config and worlds to use
File dataFileDirectory = new File(getDataFolder().getAbsolutePath() + File.separator + "GameLobbyData");

File worldFileDirectory = new File(getDataFolder().getAbsolutePath() + File.separator + "GameLobbyWorlds");

// Create the folders
dataFileDirectory.mkdirs();
worldFileDirectory.mkdirs();

// Load map files		
GameLobby.getInstance().getMapReader().loadAll(dataFileDirectory, worldFileDirectory);

/* Use random selector for lobby maps. This allows us to have multiple maps that gets randomly selected */
GameLobby.getInstance().setMapSelector(new RandomLobbyMapSelector());
```

### Making the maps

1. Put the world file inside SERVER\_FOLDER/plugins/YOUR\_PLUGIN/GameLobbyWorlds/, Final path should look like this SERVER\_FOLDER/plugins/YOUR\_PLUGIN/GameLobbyWorlds/MainGameLobbyWorld
2. Create a json file at SERVER\_FOLDER/plugins/YOUR\_PLUGIN/GameLobbyData/MainGameLobby.json
3. Inside the json file put the following content

```json
{
	"map_name": "main_lobby",
	"display_name": "Main game lobby",
	"world_file": "MainGameLobbyWorld",
	"description": "This is the main waiting lobby",
	"spawn_location": {
		"x": 0.5,
		"y": 40,
		"z": 0.5,
		"yaw": 90,
		"pitch": 0
	}
}
```

Replace the spawn location with the location you want players to spawn at

<br>

## 🡺 🡺 Maps and starting

### Reading the maps

By default the server loads maps from the plugin data folder of the game you are setting up, in this example im going to use survival games. inside plugins/NovaSurvivalGames create the following folders

* LootTables
* Maps
* Worlds

LootTables contains your loot table json files, Maps contains the map json files and Worlds contains the words used for the game.

You can override the directory to load maps from outside of the plugin data by creating plugins/NovaSurvivalGames/map\_overrides.json and putting the following in there

```json
{
	"relative": true,
	"maps_folder": "../../../../map_data/survivalgames/Maps",
	"worlds_folder": "../../../../map_data/survivalgames/Worlds",
	"loot_tables_folder": "../../../../map_data/survivalgames/LootTables"
}
```

On our tournament we have our maps in **/root/map\_data** and our servers in **/root/servers/survivalgames so** ../../../../map\_data is the correct value here, i know its a bit complicated but basically we go from **NovaSurvivalGames -> plugins -> survivalgames -> servers -> root -> map\_data** by doing this.

To create loot tables see **MapData.md** and **LootTables.md**

### Selecting the game maps

All of our map games uses the gui map selector by default. If you want random maps you can use te following code after the minigame plugin has been loaded.

```java
// Unregister listeners for existing selector
MapSelector oldSelector = GameManager.getInstance().getMapSelector();
if(oldSelector != null) {
	if(oldSelector instanceof Listener) {
		HandlerList.unregisterAll((Listener) oldSelector);
	}
}

// Load new map selector
GameManager.getInstance().setMapSelector(new RandomMapSelector());
```

### Starting the game

There are 2 ways to start the game. Number one is to use the **/game start** command that starts the game after a 60 second countdown. Below is some code that can be useful when implementing the countdown in your plugins

```java
// Check if the countdown is running
GameManager.getInstance().getCountdown().isCountdownRunning()

// Get the time left in seconds
GameManager.getInstance().getCountdown().getTimeLeft()

// Cancel the countodwn
GameManager.getInstance().getCountdown().cancelCountdown()
```

If you dont want to wait for the countdown you can either use the **/game forcestart** command or call the following function

```java
// Instantly start tge game
GameLobby.getInstance().startGame();

/* Optionally if you are using the built in countdown you might also want to check if the countdown is running and if so stop it with the following code */
if (GameManager.getInstance().hasCountdown()) {
	if(GameManager.getInstance().getCountdown().hasCountdownStarted()) {
		GameManager.getInstance().getCountdown().cancelCountdown();
	}
}
```

stopping the countdown is not required but will prevent any potentiol weird effects if the countdown is running while the game is force strted with the **GameLobby.getInstance().startGame();** function. If the startGame function is called twice nothing will happen on the second time except that oped players might get a log message in chat saying that there was an attempt so start the game twice. but its best to avoid calling it twice anyways.

### Ending the game early

If you need to end the game early due to some issue use the **/game stop** command. This instantly ends the game and sets everyone to spectator (unless the game is configured to do something else on end)

<br>

## 🡺 Loot table config

The loot table json structure looks like this

```json
{
	"name": "hg_common_chests",
	"min_items": 5,
	"max_items": 9,
	"loader": "novacore.loot_table_loader_v1",

	"items": [
	]
}
```

name is the name used in other plugins / config files to recerence this loot table, every loot table has to have a uniqe name.

min_items is the minimal amount of random items to pick and max_items is the maximum amount. Note that this means diffrent item stack and not the total sum of items.

by putting both min and max to 5 it would make it so that this loot table always generarates 5 items stacks of random sizes depending on the items configured in the loot table.

Items contains the json objecs of the items to generate. Below are some examples

### Adding items

Below is an example of a simple item

```json
{
	"chance": 5,
	"material": "BOW",
	"min_amount": 1
}
```

#### Explaining the chance system

The way you configure rarity is by the number in the chance property. The way this works is that it adds the entry the a list N amount of times where N is the chance. When an item is picked we select a random entry in the list and create an item stack from it. This means that lower numbers will cause the item to be more rare while higher numbers causes the item to be much more common

#### Material

The possible materials can be found at <https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html> (You might want to look up correct version for your server)

### • Random item amount

You can randomise the amount of items to generate in the stack by using the following

```json
"min_amount": 1,
"max_amount": 4
```

This will mate it so that the amount of items in the stack will be between 1 and 4.

### • Custom item names

You can set the name with the following

```json
"display_name": "§6§lKnockback stick"
```

### • Enchants

Add enchantments with the following

```json
"enchantments" : {
	"KNOCKBACK": 3
}
```

Valid enchantment names can be found here <https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/enchantments/Enchantment.html> (You might want to look up correct version for your server)

Enchantments are added with ignoreLevelRestriction set to true so go crazy with the levels if you want.

### • Enchanted books

To add enchants to an enchanted book use

```json
"stored_enchantments" : {
	"KNOCKBACK": 3
}
```

Valid enchantment names can be found here <https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/enchantments/Enchantment.html> (You might want to look up correct version for your server)

Enchantments are added with ignoreLevelRestriction set to true so go crazy with the levels if you want.

### • Tracker compasses

Some of our games (Survival games and skywars being 2 examples) has a system where if you have a comapss in your inventory it will point to the closest enemy. To add a tracker compass to the loot table use the following

```json
{
	"chance": 1,
	"material": "COMPASS",
	"min_amount": 1,
	"display_name": "§6§lTracker compass"
}
```
