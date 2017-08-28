package de.heliosdevelopment.helioslobby.friends;

import org.bukkit.entity.Player;

import java.util.List;

public class FriendPlayer {

    private final Player player;
    private final List<String> friends;

    public FriendPlayer(Player player, List<String> friends) {
        this.player = player;
        this.friends = friends;
    }

    public Player getPlayer() { return player; }

    public List<String> getFriends() {
        return friends;
    }
}
