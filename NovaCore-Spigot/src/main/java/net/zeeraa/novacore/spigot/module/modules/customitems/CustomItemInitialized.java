package net.zeeraa.novacore.spigot.module.modules.customitems;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public abstract class CustomItemInitialized extends CustomItem {
	private final String id;

    public CustomItemInitialized(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getFullId() {
        return this.getClass().getName() + "." + id;
    }

    @Override
    public ItemStack getItem(@Nullable Player player) {
        ItemStack stack = createItemStack(player);

        stack = NBTEditor.set(stack, 1, "novacore", "iscustomitem");
        stack = NBTEditor.set(stack, this.getClass().getName() + "." + id, "novacore", "customitemid");
        return stack;
    }
}