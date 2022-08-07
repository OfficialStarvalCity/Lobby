package de.heliosdevelopment.helioslobby;

import java.io.File;
import java.util.UUID;

import de.heliosdevelopment.helioslobby.player.points.PointsPlayer;
import de.heliosdevelopment.helioslobby.player.points.PointsPlayerManager;
import org.bukkit.configuration.file.YamlConfiguration;


public class HeliosPointsAPI {

    private final static Lobby plugin = Lobby.getInstance();
    private final static File file = new File(plugin.getDataFolder() + "//data.yml");
    private final static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    /**
     * Get Points from a Player
     *
     * @param uuid
     *            of the player
     *
     * @return The current number of points
     */
    public static int getPoints(UUID uuid) {
        if (uuid == null)
            return 0;
        PointsPlayer pointsPlayer = PointsPlayerManager.getPlayer(uuid);
        if (pointsPlayer != null) {
            return pointsPlayer.getPoints();
        } else {
            if (plugin.getDatabaseType() == DatabaseType.MYSQL) {
                return plugin.getPointsMysql().getInt(uuid.toString());
            } else {
                if (cfg.contains(uuid.toString()))
                    return cfg.getInt(uuid.toString());
            }
        }
        return 0;

    }

    /**
     * Add the defined Player the defined amount of Points
     *
     * @param uuid
     *            The player who get the points
     * @param amount
     *            The amount of Points
     */
    public static void addPoints(UUID uuid, int amount) {
        if (uuid == null || amount == 0)
            return;
        PointsPlayer pointsPlayer = PointsPlayerManager.getPlayer(uuid);
        if (pointsPlayer != null) {
            pointsPlayer.setPoints(pointsPlayer.getPoints() + amount);
        } else {
            if (plugin.getDatabaseType() == DatabaseType.MYSQL) {
                plugin.getPointsMysql().setPoints(uuid.toString(), getPoints(uuid) + amount);
            } else {
                if (cfg.contains(uuid.toString()))
                    cfg.set(uuid.toString(), cfg.getInt(uuid.toString()) + amount);
                else
                    cfg.set(uuid.toString(), amount);
            }
        }
    }

    /**
     * Remove points from the define Player
     *
     * @param uuid
     *            The player who lost the points
     * @param amount
     *            The amount of Points
     */
    public static boolean removePoints(UUID uuid, Integer amount) {
        if (uuid == null || amount == 0)
            return false;
        if (getPoints(uuid) < amount)
            return false;
        PointsPlayer pointsPlayer = PointsPlayerManager.getPlayer(uuid);
        if (pointsPlayer != null) {
            pointsPlayer.setPoints(pointsPlayer.getPoints() - amount);
        } else {
            if (plugin.getDatabaseType() == DatabaseType.MYSQL) {
                plugin.getPointsMysql().setPoints(uuid.toString(), getPoints(uuid) - amount);
            } else {
                if (cfg.contains(uuid.toString()))
                    cfg.set(uuid.toString(), cfg.getInt(uuid.toString()) - amount);
                else
                    cfg.set(uuid.toString(), amount);
            }
        }
        return true;
    }

    /**
     * Set the points from a Player to the define amount
     *
     * @param uuid
     *            The Player
     * @param amount
     *            The amount of Points
     */
    public static void setPoints(UUID uuid, Integer amount) {
        if (uuid == null || amount == 0)
            return;
        PointsPlayer pointsPlayer = PointsPlayerManager.getPlayer(uuid);
        if (pointsPlayer != null) {
            pointsPlayer.setPoints(amount);
        } else {
            if (plugin.getDatabaseType() == DatabaseType.MYSQL) {
                plugin.getPointsMysql().setPoints(uuid.toString(), amount);
            } else {
                cfg.set(uuid.toString(), amount);
            }
        }
    }

    public static File getFile() {
        return file;
    }

    public static YamlConfiguration getConfiguration() {
        return cfg;
    }

}
