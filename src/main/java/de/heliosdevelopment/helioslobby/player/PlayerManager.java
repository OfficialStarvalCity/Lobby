package de.heliosdevelopment.helioslobby.player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.MySQL;

public class PlayerManager {

    private final static Set<LobbyPlayer> players = new HashSet<>();
    private final static MySQL mysql = Lobby.getInstance().getMysql();

    public static void loadPlayer(Player player) {
        LobbyPlayer lobbyPlayer = mysql.getLobbyPlayer(player.getUniqueId().toString());
        if (lobbyPlayer == null)
            lobbyPlayer = new LobbyPlayer(player.getUniqueId(), player.getName(), Visibility.ALL, new HashSet<>(), 0, 0, 0);
        players.add(lobbyPlayer);
    }

    public static void unloadPlayer(Player player) {
        LobbyPlayer lobbyPlayer = getPlayer(player);
        if (lobbyPlayer != null) {
            mysql.addPlayer(lobbyPlayer.getUuid().toString(), lobbyPlayer.getName(), lobbyPlayer.getVisibility(), lobbyPlayer.getCosmetics(), lobbyPlayer.getLastDailyReward(), lobbyPlayer.getLastPremiumReward(), lobbyPlayer.getKeys());
        }
    }

    public static LobbyPlayer getPlayer(Player player) {
        for (LobbyPlayer lobbyPlayer : players)
            if (lobbyPlayer.getUuid().equals(player.getUniqueId()))
                return lobbyPlayer;
        return null;
    }

    public static UUID getUuid(String name) {
        for (LobbyPlayer ip : players)
            if (ip.getName().equalsIgnoreCase(name))
                return ip.getUuid();
        return mysql.getUuid(name);
    }

    public static String getName(UUID uuid) {
        for (LobbyPlayer ip : players)
            if (ip.getUuid().equals(uuid))
                return ip.getName();
        return mysql.getPlayerName(uuid);
    }

}
