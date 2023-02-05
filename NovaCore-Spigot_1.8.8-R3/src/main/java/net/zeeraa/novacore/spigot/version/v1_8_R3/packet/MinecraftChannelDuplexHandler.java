package net.zeeraa.novacore.spigot.version.v1_8_R3.packet;

import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import net.minecraft.server.v1_8_R3.PacketPlayInSpectate;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import net.zeeraa.novacore.spigot.abstraction.enums.ChatVisibility;
import net.zeeraa.novacore.spigot.abstraction.enums.Hand;
import net.zeeraa.novacore.spigot.abstraction.enums.MainHand;
import net.zeeraa.novacore.spigot.abstraction.packet.event.PlayerAttemptBreakBlockEvent;
import net.zeeraa.novacore.spigot.abstraction.packet.event.PlayerListenSoundEvent;
import net.zeeraa.novacore.spigot.abstraction.packet.event.PlayerSettingsEvent;
import net.zeeraa.novacore.spigot.abstraction.packet.event.PlayerSwingEvent;
import net.zeeraa.novacore.spigot.abstraction.packet.event.ReadPacketSentEvent;
import net.zeeraa.novacore.spigot.abstraction.packet.event.SpectatorTeleportEvent;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MinecraftChannelDuplexHandler extends net.zeeraa.novacore.spigot.abstraction.packet.MinecraftChannelDuplexHandler {
	public MinecraftChannelDuplexHandler(Player player) {
		super(player);
	}

	public boolean readPacket(Player player, Object packet) throws NoSuchFieldException, IllegalAccessException {
		List<Event> events = new ArrayList<>();
		events.add(new ReadPacketSentEvent(player, packet));
		if (packet.getClass().equals(PacketPlayInSettings.class)) {
			PacketPlayInSettings settings = (PacketPlayInSettings) packet;
			Field field = PacketPlayInSettings.class.getDeclaredField("b");
			field.setAccessible(true);
			int value = (int) field.get(settings);
			events.add(new PlayerSettingsEvent(player, settings.a(), value, ChatVisibility.valueOf(settings.c().name()), settings.d(), settings.e(), MainHand.RIGHT, false, true));

		} else if (packet.getClass().equals(PacketPlayInArmAnimation.class)) {
			PacketPlayInArmAnimation arm = (PacketPlayInArmAnimation) packet;

			events.add(new PlayerSwingEvent(player, arm.timestamp, Hand.MAIN_HAND));
			Block block = VersionIndependentUtils.get().getReacheableBlockExact(player);
			if (block != null) {
				if (canBreak(player, block)) {
					events.add(new PlayerAttemptBreakBlockEvent(player, arm.timestamp, block));
				}
			}

		} else if (packet.getClass().equals(PacketPlayInSpectate.class)) {
			PacketPlayInSpectate spectate = (PacketPlayInSpectate) packet;
			Field field = PacketPlayInSpectate.class.getDeclaredField("a");
			field.setAccessible(true);
			UUID id = (UUID) field.get(spectate);

			events.add(new SpectatorTeleportEvent(player, Bukkit.getPlayer(id)));

		} else if (packet.getClass().equals(PacketPlayInBlockDig.class)) {
			List<Player> playersDigging = VersionIndependentUtils.get().getPacketManager().getPlayersDigging();
			PacketPlayInBlockDig action = (PacketPlayInBlockDig) packet;

			switch (action.c()) {
			case START_DESTROY_BLOCK:
				if (playersDigging.stream().noneMatch(pl -> pl.getUniqueId().equals(player.getUniqueId()))) {
					playersDigging.add(player);
					if (canBreak(player, VersionIndependentUtils.get().getReacheableBlockExact(player))) {
						events.add(new PlayerAttemptBreakBlockEvent(player, System.currentTimeMillis(), VersionIndependentUtils.get().getReacheableBlockExact(player)));
					}
				}
				break;
			case STOP_DESTROY_BLOCK:
			case ABORT_DESTROY_BLOCK:
				playersDigging.remove(player);
				break;
			default:
				break;
			}
		}

		if (events.isEmpty())
			return true;

		boolean value = true;
		for (Event event : events) {
			Bukkit.getPluginManager().callEvent(event);

			if (((Cancellable) event).isCancelled()) {
				value = false;
				break;
			}
		}
		return value;
	}

	@Override
	public boolean writePacket(Player player, Object packet) throws NoSuchFieldException, IllegalAccessException {
		List<Event> events = new ArrayList<>();
		if (packet.getClass().equals(PacketPlayOutNamedSoundEffect.class)) {
			PacketPlayOutNamedSoundEffect effect = (PacketPlayOutNamedSoundEffect) packet;
			Field a = effect.getClass().getDeclaredField("a");
			a.setAccessible(true);
			Field b = effect.getClass().getDeclaredField("b");
			b.setAccessible(true);
			Field c = effect.getClass().getDeclaredField("c");
			c.setAccessible(true);
			Field d = effect.getClass().getDeclaredField("d");
			d.setAccessible(true);
			Field e = effect.getClass().getDeclaredField("e");
			e.setAccessible(true);
			Field f = effect.getClass().getDeclaredField("f");
			f.setAccessible(true);

			String name = (String) a.get(effect);
			double x = (float) b.get(effect) / 8.0F;
			double y = (float) c.get(effect) / 8.0F;
			double z = (float) d.get(effect) / 8.0F;
			float volume = (float) e.get(effect);
			float pitch = (float) f.get(effect) / 63.0F;

			events.add(new PlayerListenSoundEvent(player, name, null, x, y, z, volume, pitch));
		}

		if (events.isEmpty())
			return true;

		boolean value = true;
		for (Event event : events) {
			Bukkit.getPluginManager().callEvent(event);

			if (((Cancellable) event).isCancelled()) {
				value = false;
				break;
			}
		}
		return value;
	}
}