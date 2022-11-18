package net.zeeraa.novacore.spigot.utils;

import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependentMaterial;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PotionBuilder extends ItemBuilder {

    public PotionBuilder(Material material) {
        super(material);
    }

    public PotionBuilder(Material material, int ammount) {
        super(material, ammount);
    }

    public PotionBuilder(VersionIndependentMaterial material) {
        super(material);
    }

    public PotionBuilder(VersionIndependentMaterial material, int ammount) {
        super(material, ammount);
    }

    public PotionBuilder(ItemStack itemStack) {
        super(itemStack);
    }

    public PotionBuilder(ItemStack itemStack, boolean clone) {
        super(itemStack, clone);
    }


    public PotionBuilder setPotionEffect(PotionEffect effect, boolean color) {
        VersionIndependentUtils.get().setPotionEffect(item, meta, effect, color);
        return this;
    }

    public PotionBuilder setPotionColor(Color color) {
        VersionIndependentUtils.get().setPotionColor(meta, color);
        return this;
    }

}
