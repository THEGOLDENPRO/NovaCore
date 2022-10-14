package net.zeeraa.novacore.spigot.abstraction.packet.event;

import net.zeeraa.novacore.spigot.abstraction.enums.ChatVisibility;
import net.zeeraa.novacore.spigot.abstraction.enums.MainHand;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerSettingsEvent extends PacketEvent {


    private final String language;

    private final int chunkDistance;
    private final ChatVisibility chatVisibility;
    private final boolean chatColored;
    private final int skinCustomization;
    private final MainHand mainHand;
    private final boolean textFiltering;
    private final boolean serverListing;

    public PlayerSettingsEvent(Player player, String language, int chunkDistance, ChatVisibility chatVisibility, boolean chatColored, int skinCustomization,
                               MainHand mainHand, boolean textFiltering, boolean serverListing) {
        super(player);
        this.language = language;
        this.chunkDistance = chunkDistance;
        this.chatVisibility = chatVisibility;
        this.chatColored = chatColored;
        this.skinCustomization = skinCustomization;
        this.mainHand = mainHand;
        this.textFiltering = textFiltering;
        this.serverListing = serverListing;
    }


    private boolean isKthBitSet(int n, int k) {
        return (n & (1 << (k - 1))) > 0;
    }

    public String getLanguage() {
        return language;
    }

    public int getChunkDistance() {
        return chunkDistance;
    }

    public ChatVisibility getChatVisibility() {
        return chatVisibility;
    }

    public boolean isChatColored() {
        return chatColored;
    }

    public boolean hasCapeEnabled() {
        return isKthBitSet(skinCustomization, 1);
    }

    public boolean hasJacketEnabled() {
        return isKthBitSet(skinCustomization, 2);
    }

    public boolean hasLeftSleeveEnabled() {
        return isKthBitSet(skinCustomization,3);
    }

    public boolean hasRightSleeveEnabled() {
        return isKthBitSet(skinCustomization, 4);
    }

    public boolean hasLeftPantsLegEnabled() {
        return isKthBitSet(skinCustomization, 5);
    }

    public boolean hasRightPantsLegEnabled() {
        return isKthBitSet(skinCustomization, 6);
    }

    public boolean hasHatEnabled() {
        return isKthBitSet(skinCustomization, 7);
    }

    public MainHand getMainHand() {
        return mainHand;
    }

    public boolean textFilteringEnabled() {
        return textFiltering;
    }

    public boolean serverListingEnabled() {
        return serverListing;
    }
}
