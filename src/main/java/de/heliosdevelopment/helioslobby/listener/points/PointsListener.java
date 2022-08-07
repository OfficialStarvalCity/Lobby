package de.heliosdevelopment.helioslobby.listener.points;

import de.heliosdevelopment.helioslobby.player.points.PointsPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PointsListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PointsPlayerManager.loadPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PointsPlayerManager.unloadPlayer(event.getPlayer());
    }

}
