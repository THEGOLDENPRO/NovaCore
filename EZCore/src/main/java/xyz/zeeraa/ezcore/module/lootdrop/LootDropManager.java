package xyz.zeeraa.ezcore.module.lootdrop;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import xyz.zeeraa.ezcore.EZCore;
import xyz.zeeraa.ezcore.log.EZLogger;
import xyz.zeeraa.ezcore.module.EZModule;
import xyz.zeeraa.ezcore.utils.LocationUtils;

public class LootDropManager extends EZModule implements Listener {
	private static LootDropManager instance;

	private ArrayList<LootDrop> chests;
	private ArrayList<LootDropEffect> dropEffects;

	private int taskId;

	public static LootDropManager getInstance() {
		return instance;
	}

	@Override
	public String getName() {
		return "LootDropManager";
	}

	public LootDropManager() {
		LootDropManager.instance = this;
		chests = new ArrayList<LootDrop>();
		dropEffects = new ArrayList<LootDropEffect>();
		taskId = -1;
	}

	@Override
	public void onEnable() {
		taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(EZCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				for (int i = dropEffects.size(); i > 0; i--) {
					if (dropEffects.get(i - 1).isCompleted()) {
						dropEffects.remove(i - 1);
					}
				}
			}
		}, 20L, 20L);
	}

	@Override
	public void onDisable() {
		destroy();
	}

	public void destroy() {
		if (taskId != -1) {
			Bukkit.getScheduler().cancelTask(taskId);
		}

		for (LootDropEffect effect : dropEffects) {
			effect.undoBlocks();
		}

		for (int i = chests.size(); i > 0; i--) {
			removeChest(chests.get(i - 1));
		}
	}

	public void removeFromWorld(World world) {
		for (LootDropEffect effect : dropEffects) {
			if (effect.getWorld().equals(world)) {
				effect.undoBlocks();
			}
		}

		for (int i = chests.size(); i > 0; i--) {
			if (chests.get(i).getWorld().equals(world)) {
				removeChest(chests.get(i - 1));
			}
		}
	}

	public void spawnDrop(Location location, String lootTable) {
		if (canSpawnAt(location)) {
			spawnDrop(location, lootTable, true);
		}
	}

	public boolean canSpawnAt(Location location) {

		for (LootDropEffect effect : dropEffects) {
			if (effect.getLocation().getBlockX() == location.getBlockX()) {
				if (effect.getLocation().getBlockZ() == location.getBlockZ()) {
					return false;
				}
			}
		}

		if (location.getBlock().getType() == Material.SKULL) {
			return false;
		}

		if (LocationUtils.isOutsideOfBorder(location)) {
			return false;
		}

		return true;
	}

	public void spawnDrop(Location location, String lootTable, boolean announce) {
		dropEffects.add(new LootDropEffect(location, lootTable));
		if (announce) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
				p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "A loot drop is spawning at X: " + ChatColor.AQUA + "" + ChatColor.BOLD + location.getBlockX() + ChatColor.GOLD + "" + ChatColor.BOLD + " Z: " + ChatColor.AQUA + "" + ChatColor.BOLD + location.getBlockZ());
			}
		}
	}

	public void spawnChest(Location location, String lootTable) {
		chests.add(new LootDrop(location, lootTable));
	}

	public LootDrop getChestAtLocation(Location location) {
		for (LootDrop chest : chests) {
			if (chest.getLocation().getWorld() == location.getWorld()) {
				if (chest.getLocation().getBlockX() == location.getBlockX()) {
					if (chest.getLocation().getBlockY() == location.getBlockY()) {
						if (chest.getLocation().getBlockZ() == location.getBlockZ()) {
							return chest;
						}
					}
				}
			}
		}

		return null;
	}

	public void removeChest(LootDrop chest) {
		chests.remove(chest);
		chest.remove();
	}

	public LootDrop getChestByUUID(UUID uuid) {
		for (LootDrop chest : chests) {
			if (chest.getUuid() == uuid) {
				return chest;
			}
		}

		return null;
	}

	private boolean isInventoryEmpty(Inventory inventory) {
		for (ItemStack i : inventory.getContents()) {
			if (i == null) {
				continue;
			}

			if (i.getType() != Material.AIR) {
				return false;
			}
		}

		return true;
	}

	public boolean isDropActiveAt(World world, int x, int z) {
		for (LootDropEffect e : dropEffects) {
			if (e.getWorld().getName().equalsIgnoreCase(world.getName())) {
				if (e.getLocation().getBlockX() == x) {
					if (e.getLocation().getBlockZ() == z) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getInventory().getHolder() instanceof LootDropInventoryHolder) {
			if (isInventoryEmpty(e.getInventory())) {
				UUID uuid = ((LootDropInventoryHolder) e.getInventory().getHolder()).getUuid();

				LootDrop chest = this.getChestByUUID(uuid);

				if (chest != null) {
					if (chest.isRemoved()) {
						return;
					}

					removeChest(chest);
					chest.getWorld().playSound(chest.getLocation(), Sound.WITHER_HURT, 1F, 1F);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.SKULL) {
				LootDrop chest = this.getChestAtLocation(e.getClickedBlock().getLocation());

				if (chest != null) {
					e.getPlayer().openInventory(chest.getInventory());
					e.setCancelled(true);
				}
			} else if (e.getClickedBlock().getType() == Material.BEACON) {
				for (LootDropEffect effect : dropEffects) {
					for (Location location : effect.getRemovedBlocks().keySet()) {
						if (location.equals(e.getClickedBlock().getLocation())) {
							EZLogger.trace("Preventing player from interacting with loot drop beacon");
							e.setCancelled(true);
							return;
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent e) {
		for (LootDropEffect effect : dropEffects) {
			for (Location location : effect.getRemovedBlocks().keySet()) {
				if (location.equals(e.getBlock().getLocation())) {
					EZLogger.trace("Preventing player from breaking loot drop");
					e.setCancelled(true);
					return;
				}
			}
		}

		if (e.getBlock().getType() == Material.SKULL) {
			LootDrop chest = this.getChestAtLocation(e.getBlock().getLocation());

			if (chest != null) {
				e.setCancelled(true);
				return;
			}
		}
	}
}