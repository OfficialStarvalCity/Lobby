package de.heliosdevelopment.helioslobby.player.points;

import de.heliosdevelopment.helioslobby.DatabaseType;
import de.heliosdevelopment.helioslobby.HeliosPointsAPI;
import de.heliosdevelopment.helioslobby.Lobby;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PointsPlayerManager {

    private final static Set<PointsPlayer> players = new HashSet<>();
    private final static Lobby plugin = Lobby.getInstance();

    public static void loadPlayer(Player player) {
        int points = 0;
        if (plugin.getDatabaseType() == DatabaseType.MYSQL) {
            points = plugin.getPointsMysql().getInt(player.getUniqueId().toString());
        } else {
            points = HeliosPointsAPI.getConfiguration().getInt(player.getUniqueId().toString());
        }
        players.add(new PointsPlayer(player.getUniqueId(), points));
    }

    public static void unloadPlayer(Player player) {
        PointsPlayer pointsPlayer = getPlayer(player.getUniqueId());
        if (pointsPlayer != null) {
            if (plugin.getDatabaseType() == DatabaseType.MYSQL) {
                plugin.getPointsMysql().setPoints(pointsPlayer.getUuid().toString(), pointsPlayer.getPoints());
            } else {
                HeliosPointsAPI.getConfiguration().set(pointsPlayer.getUuid().toString(), pointsPlayer.getPoints());
                try {
                    HeliosPointsAPI.getConfiguration().save(HeliosPointsAPI.getFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static PointsPlayer getPlayer(UUID uuid) {
        for (PointsPlayer pointsPlayer : players)
            if (pointsPlayer.getUuid().equals(uuid))
                return pointsPlayer;
        return null;

    }

}
