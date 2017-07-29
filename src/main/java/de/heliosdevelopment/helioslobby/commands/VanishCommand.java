package de.heliosdevelopment.helioslobby.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.heliosdevelopment.helioslobby.Lobby;

public class VanishCommand extends LobbyCommand implements  Listener {

	private final List<Player> vanish = new ArrayList<>();

	public VanishCommand() {
		Bukkit.getPluginManager().registerEvents(this, Lobby.getInstance());
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs instanceof Player) {
			Player p = (Player) cs;
			if (p.hasPermission("lobby.vanish")) {
				if (vanish.contains(p)) {
					for (Player players : Bukkit.getOnlinePlayers()) {
						players.showPlayer(p);
						vanish.remove(p);
					}
					p.sendMessage(prefix + "§7Du bist nun Sichtbar.");
				} else {
					for (Player players : Bukkit.getOnlinePlayers()) {
						players.hidePlayer(p);
						vanish.add(p);
					}
					p.sendMessage(prefix + "§7Du bist nun Unsichtbar.");
				}

			} else 
				p.sendMessage(prefix +chatManager.getMessage("nopermissions"));
			
		}
		return true;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		for (Player player : vanish) {
			e.getPlayer().hidePlayer(player);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if (vanish.contains(e.getPlayer())) {
			for (Player players : Bukkit.getOnlinePlayers()) {
				players.showPlayer(player);
				vanish.remove(player);
			}
		}

		for (Player players : vanish) {
			player.showPlayer(players);
		}
	}
}
