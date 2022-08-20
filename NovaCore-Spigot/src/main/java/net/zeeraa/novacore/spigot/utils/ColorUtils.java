package net.zeeraa.novacore.spigot.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;

public class ColorUtils {
    public static Color getColorByChatColor(ChatColor color) {
        switch (color) {
            case BLACK:
                return Color.BLACK;
            case DARK_BLUE:
                return Color.NAVY;
            case DARK_GREEN:
                return Color.GREEN;
            case DARK_AQUA:
                return Color.TEAL;
            case DARK_RED:
                return Color.MAROON;
            case DARK_PURPLE:
                return Color.PURPLE;
            case GOLD:
                return Color.ORANGE;
            case GRAY:
                return Color.SILVER;
            case DARK_GRAY:
                return Color.GRAY;
            case BLUE:
                return Color.BLUE;
            case GREEN:
                return Color.LIME;
            case AQUA:
                return Color.AQUA;
            case RED:
                return Color.RED;
            case LIGHT_PURPLE:
                return Color.FUCHSIA;
            case YELLOW:
                return Color.YELLOW;
            case WHITE:
                return Color.WHITE;
            default:
                return Color.WHITE;
        }
    }
}
