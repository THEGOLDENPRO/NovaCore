package xyz.zeeraa.ezcore.version.v1_8_R3;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;

public class VersionIndependentUtils implements xyz.zeeraa.ezcore.abstraction.VersionIndependentUtils {
	@SuppressWarnings("deprecation")
	@Override
	public void setBlockAsPlayerSkull(Block block) {
		block.setType(Material.SKULL);
		Skull skull = (Skull) block.getState();
		skull.setSkullType(SkullType.PLAYER);

		block.getState().update(true);

		block.setData((byte) 1);
	}
}