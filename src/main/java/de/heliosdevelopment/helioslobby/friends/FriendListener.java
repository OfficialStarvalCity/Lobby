package de.heliosdevelopment.helioslobby.friends;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FriendListener implements Listener {

    private final FriendManager friendManager;

    public FriendListener(FriendManager friendManager) {
        this.friendManager = friendManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        for(String s : friendManager.addPlayer(event.getPlayer()).getFriends())
            System.out.println(s);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        friendManager.removePlayer(event.getPlayer());
    }

}
