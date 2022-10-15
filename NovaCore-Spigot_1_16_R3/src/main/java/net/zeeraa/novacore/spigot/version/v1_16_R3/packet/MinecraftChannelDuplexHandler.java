package net.zeeraa.novacore.spigot.version.v1_16_R3.packet;

import net.minecraft.server.v1_16_R3.EnumChatVisibility;
import net.minecraft.server.v1_16_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_16_R3.PacketPlayInSettings;
import net.minecraft.server.v1_16_R3.PacketPlayInSpectate;
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
            Field field = PacketPlayInSettings.class.getDeclaredField("c");
            field.setAccessible(true);
            EnumChatVisibility value = (EnumChatVisibility) field.get(settings);
            event = new PlayerSettingsEvent(player, settings.locale, settings.viewDistance, ChatVisibility.valueOf(value.name()), settings.e(), settings.f(), MainHand.valueOf(settings.getMainHand().name()), false, true);

        } else if (packet.getClass().equals(PacketPlayInArmAnimation.class)) {
            PacketPlayInArmAnimation arm = (PacketPlayInArmAnimation) packet;
            event = new PlayerSwingEvent(player, System.currentTimeMillis(), Hand.valueOf(arm.b().name()));

        } else if (packet.getClass().equals(PacketPlayInSpectate.class)) {
            PacketPlayInSpectate spectate = (PacketPlayInSpectate) packet;
            Field field = PacketPlayInSpectate.class.getDeclaredField("a");
            field.setAccessible(true);
            UUID id = (UUID) field.get(spectate);
            event = new SpectatorTeleportEvent(player, Bukkit.getPlayer(id));
            

        }
        if (event == null)
            return true;

        Bukkit.getPluginManager().callEvent(event);

        return !((Cancellable) event).isCancelled();
    }
}
