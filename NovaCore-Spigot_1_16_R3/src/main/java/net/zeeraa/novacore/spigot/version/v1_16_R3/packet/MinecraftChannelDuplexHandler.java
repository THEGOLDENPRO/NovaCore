package net.zeeraa.novacore.spigot.version.v1_16_R3.packet;

import net.minecraft.server.v1_16_R3.EnumChatVisibility;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_16_R3.PacketPlayInSettings;
import net.minecraft.server.v1_16_R3.PacketPlayInSpectate;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_16_R3.SoundEffect;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import net.zeeraa.novacore.spigot.abstraction.enums.*;
import net.zeeraa.novacore.spigot.abstraction.enums.SoundCategory;
import net.zeeraa.novacore.spigot.abstraction.packet.event.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MinecraftChannelDuplexHandler extends net.zeeraa.novacore.spigot.abstraction.packet.MinecraftChannelDuplexHandler {

	public MinecraftChannelDuplexHandler(Player player) {
		super(player);
	}

	public boolean readPacket(Player player, Object packet) throws NoSuchFieldException, IllegalAccessException {
		List<Player> playersDigging = VersionIndependentUtils.get().getPacketManager().getPlayersDigging();
		List<Event> events = new ArrayList<>();
		events.add(new ReadPacketSentEvent(player, packet));
		if (packet.getClass().equals(PacketPlayInSettings.class)) {
			PacketPlayInSettings settings = (PacketPlayInSettings) packet;
			Field field = PacketPlayInSettings.class.getDeclaredField("c");
			field.setAccessible(true);
			EnumChatVisibility value = (EnumChatVisibility) field.get(settings);
			events.add(new PlayerSettingsEvent(player, settings.locale, settings.viewDistance, ChatVisibility.valueOf(value.name()), settings.e(), settings.f(), MainHand.valueOf(settings.getMainHand().name()), false, true));

		} else if (packet.getClass().equals(PacketPlayInArmAnimation.class)) {
			PacketPlayInArmAnimation arm = (PacketPlayInArmAnimation) packet;

			events.add(new PlayerSwingEvent(player, System.currentTimeMillis(), Hand.valueOf(arm.b().name())));
				if (canBreak(player, VersionIndependentUtils.get().getReacheableBlockExact(player))) {
					events.add(new PlayerAttemptBreakBlockEvent(player, System.currentTimeMillis(), VersionIndependentUtils.get().getReacheableBlockExact(player)));
				}


		} else if (packet.getClass().equals(PacketPlayInSpectate.class)) {
			PacketPlayInSpectate spectate = (PacketPlayInSpectate) packet;
			Field field = PacketPlayInSpectate.class.getDeclaredField("a");
			field.setAccessible(true);
			UUID id = (UUID) field.get(spectate);
			events.add(new SpectatorTeleportEvent(player, Bukkit.getPlayer(id)));

		} else if (packet.getClass().equals(PacketPlayInBlockDig.class)) {
			PacketPlayInBlockDig action = (PacketPlayInBlockDig) packet;

			switch (action.d()) {
			case START_DESTROY_BLOCK:
				if (playersDigging.stream().noneMatch(pl -> pl.getUniqueId().equals(player.getUniqueId()))) {
					playersDigging.add(player);
					Block block = VersionIndependentUtils.get().getReacheableBlockExact(player);
					if (block != null) {
						if (canBreak(player, block)) {
							events.add(new PlayerAttemptBreakBlockEvent(player, System.currentTimeMillis(), block));
						}
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
		events.add(new WritePacketSentEvent(player, packet));
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
			Field g = effect.getClass().getDeclaredField("g");
			g.setAccessible(true);

			SoundCategory sc = (SoundCategory) b.get(effect);

			SoundEffect effect1 = (SoundEffect) a.get(effect);
			Field key = effect1.getClass().getDeclaredField("b");
			b.setAccessible(true);
			MinecraftKey mcKey = (MinecraftKey) key.get(effect1);
			
			Sound foundSound = Arrays.stream(Sound.values()).filter(sound -> sound.getKey().toString().equalsIgnoreCase(mcKey.toString())).findFirst().get();
			net.zeeraa.novacore.spigot.abstraction.enums.SoundCategory category = Arrays.stream(SoundCategory.values()).filter(soundCategory -> soundCategory.getName().equalsIgnoreCase(sc.getName())).findFirst().get();


			double x = (float)c.get(effect) / 8.0F;
			double y = (float)d.get(effect) / 8.0F;
			double z = (float)e.get(effect) / 8.0F;
			float volume = (float) f.get(effect);
			float pitch = (float) g.get(effect);


			events.add(new PlayerListenSoundEvent(player, foundSound, category, x, y, z, volume, pitch));
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