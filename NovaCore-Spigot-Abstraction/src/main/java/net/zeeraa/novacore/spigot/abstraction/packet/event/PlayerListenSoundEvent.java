package net.zeeraa.novacore.spigot.abstraction.packet.event;

import net.zeeraa.novacore.spigot.abstraction.enums.SoundCategory;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerListenSoundEvent extends AsyncPacketEvent {

    private Sound sound;
    private String soundString;
    private SoundCategory soundCategory;
    private Location location;
    private float volume;
    private float pitch;

    public PlayerListenSoundEvent(Player player, Sound sound, SoundCategory soundCategory, double x, double y, double z, float volume, float pitch) {
        super(player);
        this.sound = sound;
        this.soundCategory = soundCategory;
        this.location = new Location(player.getWorld(), x, y, z);
        this.volume = volume;
        this.pitch = pitch;

    }
    public PlayerListenSoundEvent(Player player, String soundString, SoundCategory soundCategory, double x, double y, double z, float volume, float pitch) {
        super(player);
        this.soundCategory = soundCategory;
        this.location = new Location(player.getWorld(), x, y, z);
        this.volume = volume;
        this.pitch = pitch;
        this.soundString = soundString;
    }

    public Sound getSound() {
        return sound;
    }

    public String getSoundString() {
        return soundString;
    }

    public SoundCategory getSoundCategory() {
        return soundCategory;
    }

    public Location getLocation() {
        return location;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

}
