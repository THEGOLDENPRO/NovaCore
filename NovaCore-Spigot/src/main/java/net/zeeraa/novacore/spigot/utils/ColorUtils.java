package net.zeeraa.novacore.spigot.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

public class ColorUtils {
    public static DyeColor getDyeColorByColor(Color color) {
        int rgb = color.asRGB();
        if (rgb == Color.WHITE.asRGB()) {
            return DyeColor.WHITE;
        } else if (rgb == Color.SILVER.asRGB()) {
            return DyeColor.SILVER;
        } else if (rgb == Color.GRAY.asRGB()) {
            return DyeColor.GRAY;
        } else if (rgb == Color.BLACK.asRGB()) {
            return DyeColor.BLACK;
        } else if (rgb == Color.RED.asRGB() || rgb ==  Color.MAROON.asRGB()) {
            return DyeColor.RED;
        } else if (rgb == Color.YELLOW.asRGB() || rgb ==  Color.OLIVE.asRGB()) {
            return DyeColor.YELLOW;
        } else if (rgb == Color.LIME.asRGB()) {
            return DyeColor.LIME;
        } else if (rgb == Color.GREEN.asRGB()) {
            return DyeColor.GREEN;
        } else if (rgb == Color.AQUA.asRGB()) {
            return DyeColor.LIGHT_BLUE;
        } else if (rgb == Color.TEAL.asRGB()) {
            return DyeColor.CYAN;
        } else if (rgb == Color.BLUE.asRGB() || rgb ==  Color.NAVY.asRGB()) {
            return DyeColor.BLUE;
        } else if (rgb == Color.FUCHSIA.asRGB()) {
            return DyeColor.PINK;
        } else if (rgb == Color.PURPLE.asRGB()) {
            return DyeColor.PURPLE;
        } else if (rgb == Color.ORANGE.asRGB()) {
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
