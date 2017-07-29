package de.heliosdevelopment.helioslobby.player;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.MySQL;

public class PlayerManager {

	private final static Set<LobbyPlayer> players = new HashSet<>();
	private final static MySQL mysql = Lobby.getInstance().getMysql();

	public static void loadPlayer(Player player) {
		LobbyPlayer lobbyPlayer = mysql.getLobbyPlayer(player.getUniqueId().toString());
		if (lobbyPlayer == null)
			lobbyPlayer = new LobbyPlayer(player.getUniqueId(), Visibility.ALL);
		players.add(lobbyPlayer);
	}

	public static void unloadPlayer(Player player) {
		LobbyPlayer lobbyPlayer = getPlayer(player);
		if (lobbyPlayer != null) {
			mysql.addPlayer(lobbyPlayer.getUuid().toString(), lobbyPlayer.getVisibility());
		}
	}

	public static LobbyPlayer getPlayer(Player player) {
		for (LobbyPlayer lobbyPlayer : players)
			if (lobbyPlayer.getUuid().equals(player.getUniqueId()))
				return lobbyPlayer;
		return null;
	}

}
