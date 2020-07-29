package xyz.zeeraa.ezcore.module.lootdrop;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import xyz.zeeraa.ezcore.EZCore;
import xyz.zeeraa.ezcore.log.EZLogger;
import xyz.zeeraa.ezcore.loottable.LootTable;

public class LootDrop {
	private Location location;
	private Inventory inventory;
	private UUID uuid;
	private String lootTable;
	private boolean removed;

	public LootDrop(Location location, String lootTable) {
		this.removed = false;
		this.uuid = UUID.randomUUID();
		this.location = location;
		this.inventory = Bukkit.createInventory(new LootDropInventoryHolder(uuid), 27, "Loot drop");

		this.lootTable = lootTable;
		
		fill();

		EZCore.getInstance().getVersionIndependentUtils().setBlockAsPlayerSkull(this.location.getBlock());
		
		NBTEditor.setSkullTexture(this.location.getBlock(), "http://textures.minecraft.net/texture/2bdd62f25f4a49cc42e054a3f212c3e0092138299172d7d8f3d438214ca972ac");
	}

	public void fill() {
		LootTable lt = EZCore.getInstance().getLootTableManager().getLootTable(lootTable);

		if (lt == null) {
			EZLogger.warn("LootDrop", "No loot table named " + lootTable + " was found");
			return;
		}

		ArrayList<ItemStack> loot = lt.generateLoot();

		inventory.clear();

		while (loot.size() > inventory.getSize()) {
			loot.remove(0);
		}

		while (loot.size() > 0) {
			Random random = new Random();

			int slot = random.nextInt(inventory.getSize());

			if (inventory.getItem(slot) == null) {
				ItemStack item = loot.remove(0);
				inventory.setItem(slot, item);
			}
		}
	}

	public boolean isRemoved() {
		return removed;
	}

	public void scheduleRemove() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(EZCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				remove(true);
			}
		}, 80L);
	}

	public void remove() {
		this.remove(false);
	}

	public void remove(boolean dropItems) {
		removed = true;

		this.location.getBlock().setType(Material.AIR);

		for (Player p : this.location.getWorld().getPlayers()) {
			if (p.getOpenInventory() != null) {
				if (p.getOpenInventory().getTopInventory() != null) {
					if (p.getOpenInventory().getTopInventory().getHolder() instanceof LootDropInventoryHolder) {
						if (((LootDropInventoryHolder) p.getOpenInventory().getTopInventory().getHolder()).getUuid() == this.uuid) {
							p.closeInventory();
						}
					}
				}
			}
		}

		for (ItemStack i : this.inventory.getContents()) {
			if (i == null) {
				continue;
			}

			if (i.getType() == Material.AIR) {
				continue;
			}

			this.location.getWorld().dropItem(this.location, i);
		}

		this.inventory.clear();
	}

	public UUID getUuid() {
		return uuid;
	}

	public Location getLocation() {
		return location;
	}

	public World getWorld() {
		return this.location.getWorld();
	}

	public Inventory getInventory() {
		return inventory;
	}
}