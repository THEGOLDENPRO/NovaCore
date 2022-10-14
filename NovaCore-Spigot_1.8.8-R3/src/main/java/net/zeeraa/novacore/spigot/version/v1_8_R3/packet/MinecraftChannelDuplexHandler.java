package net.zeeraa.novacore.spigot.version.v1_8_R3.packet;

import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import net.minecraft.server.v1_8_R3.PacketPlayInSpectate;
import net.zeeraa.novacore.spigot.abstraction.enums.ChatVisibility;
import net.zeeraa.novacore.spigot.abstraction.enums.Hand;
import net.zeeraa.novacore.spigot.abstraction.enums.MainHand;
import net.zeeraa.novacore.spigot.abstraction.packet.event.PlayerSettingsEvent;
import net.zeeraa.novacore.spigot.abstraction.packet.event.PlayerSwingEvent;
import net.zeeraa.novacore.spigot.abstraction.packet.event.SpectatorTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.lang.reflect.Field;
import java.util.UUID;

public class MinecraftChannelDuplexHandler extends net.zeeraa.novacore.spigot.abstraction.packet.MinecraftChannelDuplexHandler {

    public MinecraftChannelDuplexHandler(Player player) {
        super(player);
    }

    public boolean readPacket(Player player, Object packet) throws NoSuchFieldException, IllegalAccessException {
        Event event = null;
        if (packet.getClass().equals(PacketPlayInSettings.class)) {
            PacketPlayInSettings settings = (PacketPlayInSettings) packet;
            Field field = PacketPlayInSettings.class.getDeclaredField("b");
            field.setAccessible(true);
            int value = (int) field.get(settings);
            event = new PlayerSettingsEvent(player, settings.a(), value, ChatVisibility.valueOf(settings.c().name()), settings.d(), settings.e(), MainHand.RIGHT, false, true);

        } else if (packet.getClass().equals(PacketPlayInArmAnimation.class)) {
            PacketPlayInArmAnimation arm = (PacketPlayInArmAnimation) packet;
            event = new PlayerSwingEvent(player, arm.timestamp, Hand.MAIN_HAND);

        } else if (packet.getClass().equals(PacketPlayInSpectate.class)) {
            PacketPlayInSpectate spectate = (PacketPlayInSpectate) packet;
            Field field = PacketPlayInSpectate.class.getDeclaredField("a");
            field.setAccessible(true);
            UUID id = (UUID) field.get(spectate);
            event = new SpectatorTeleportEvent(player, Bukkit.getPlayer(id));


        }


        if (event == null)
            return false;

        Bukkit.getPluginManager().callEvent(event);

        return !((Cancellable) event).isCancelled();
    }
}
