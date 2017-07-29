package de.heliosdevelopment.helioslobby.player;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class LobbyPlayer {

    private final UUID uuid;

    private final HashMap<Setting, Boolean> settings = new HashMap<>();
    private Visibility visibility;
    private int keys;
    private Set<Integer> cosmetics;

    public LobbyPlayer(UUID uuid, Visibility visibility) {
        this.uuid = uuid;
        this.visibility = visibility;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public HashMap<Setting, Boolean> getSettings() {
        return settings;
    }

    public int getKeys() {
        return keys;
    }

    public void setKeys(int keys) {
        this.keys = keys;
    }
}
