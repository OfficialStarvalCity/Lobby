package de.heliosdevelopment.helioslobby.player;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class LobbyPlayer {

    private final UUID uuid;
    private final String name;

    private Visibility visibility;
    private int keys;
    private Set<Integer> cosmetics;
    private long lastDailyReward;
    private long lastPremiumReward;

    public LobbyPlayer(UUID uuid, String name, Visibility visibility, Set<Integer> cosmetics, long lastDailyReward, long lastPremiumReward, int keys) {
        this.uuid = uuid;
        this.name = name;
        this.visibility = visibility;
        this.cosmetics = cosmetics;
        this.lastDailyReward = lastDailyReward;
        this.lastPremiumReward = lastPremiumReward;
        this.keys = keys;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() { return name; }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public int getKeys() {
        return keys;
    }

    public void setKeys(int keys) {
        this.keys = keys;
    }

    public Set<Integer> getCosmetics() { return cosmetics; }

    public long getLastDailyReward() { return lastDailyReward; }

    public void setLastDailyReward(long lastDailyReward) {
        this.lastDailyReward = lastDailyReward;
    }

    public long getLastPremiumReward() {
        return lastPremiumReward;
    }

    public void setLastPremiumReward(long lastPremiumReward) {
        this.lastPremiumReward = lastPremiumReward;
    }
}
