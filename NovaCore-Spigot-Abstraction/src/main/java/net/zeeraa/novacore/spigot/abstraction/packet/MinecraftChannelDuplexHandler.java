package net.zeeraa.novacore.spigot.abstraction.packet;

import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.List;

public abstract class MinecraftChannelDuplexHandler extends ChannelDuplexHandler {
	private final Player player;

	public MinecraftChannelDuplexHandler(Player player) {
		this.player = player;
	}

	@Override
	public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
		if (!readPacket(player, packet)) {
			return;
		}
		super.channelRead(channelHandlerContext, packet);
	}

	@Override
	public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
		super.write(channelHandlerContext, packet, channelPromise);
	}

	public abstract boolean readPacket(Player player, Object packet) throws NoSuchFieldException, IllegalAccessException;

	protected boolean canBreak(Player player, Block block) {

		if (block == null) {
			return false;
		}

		if (block.getType() == Material.AIR) {

			return false;
		}
		if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {

			return false;
		}

		List<Player> playersDigging = VersionIndependentUtils.get().getPacketManager().getPlayersDigging();

		if (playersDigging.stream().noneMatch(pl -> pl.getUniqueId().equals(player.getUniqueId()))) {
			return false;
		}

		if (player.getGameMode() == GameMode.SURVIVAL) {
			return true;
		}

		return VersionIndependentUtils.get().canBreakBlock(player.getItemInHand(), block.getType());
	}
}
