package net.zeeraa.novacore.spigot.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

public class ColorUtils {
    public static DyeColor getDyeColorByColor(Color color) {
        if (color.equals(Color.WHITE)) {
            return DyeColor.WHITE;
        } else if (color.equals(Color.SILVER)) {
            return DyeColor.SILVER;
        } else if (color.equals(Color.GRAY)) {
            return DyeColor.GRAY;
        } else if (color.equals(Color.BLACK)) {
            return DyeColor.BLACK;
        } else if (color.equals(Color.RED) || color.equals(Color.MAROON)) {
            return DyeColor.RED;
        } else if (color.equals(Color.YELLOW) || color.equals(Color.OLIVE)) {
            return DyeColor.YELLOW;
        } else if (color.equals(Color.LIME)) {
            return DyeColor.LIME;
        } else if (color.equals(Color.GREEN)) {
            return DyeColor.GREEN;
        } else if (color.equals(Color.AQUA)) {
            return DyeColor.LIGHT_BLUE;
        } else if (color.equals(Color.TEAL)) {
            return DyeColor.CYAN;
        } else if (color.equals(Color.BLUE) || color.equals(Color.NAVY)) {
            return DyeColor.BLUE;
        } else if (color.equals(Color.FUCHSIA)) {
            return DyeColor.PINK;
        } else if (color.equals(Color.PURPLE)) {
            return DyeColor.PURPLE;
        } else if (color.equals(Color.ORANGE)) {
            return DyeColor.ORANGE;
        } else {
            return DyeColor.WHITE;
        }
    }
    public static DyeColor getDyeColorByChatColor(ChatColor color) {
        return getDyeColorByColor(getColorByChatColor(color));
    }
    public static Color getColorByDyeColor(DyeColor color) {
        switch (color) {
            case BLACK:
                return Color.BLACK;
            case BLUE:
                return Color.NAVY;
            case GREEN:
                return Color.GREEN;
            case CYAN:
                return Color.TEAL;
            case RED:
                return Color.RED;
            case PURPLE:
                return Color.PURPLE;
            case ORANGE:
                return Color.ORANGE;
            case SILVER:
                return Color.SILVER;
            case GRAY:
                return Color.GRAY;
            case LIME:
                return Color.LIME;
            case LIGHT_BLUE:
                return Color.AQUA;
            case MAGENTA:
            case PINK:
                return Color.FUCHSIA;
            case YELLOW:
                return Color.YELLOW;
            case WHITE:
                return Color.WHITE;
            default:
                return Color.WHITE;
        }
    }
    public static Color getColorByChatColor(ChatColor color) {
        return getColorByDyeColor(getDyeColorByChatColor(color));
    }
}
