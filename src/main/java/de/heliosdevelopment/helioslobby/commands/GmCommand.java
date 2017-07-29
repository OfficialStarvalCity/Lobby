package de.heliosdevelopment.helioslobby.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GmCommand extends LobbyCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission("lobby.gamemode")) {
				if (arg3.length == 1) {
					if (!arg3[0].equalsIgnoreCase("0") && !arg3[0].equalsIgnoreCase("1")
							&& !arg3[0].equalsIgnoreCase("2") && !arg3[0].equalsIgnoreCase("3")) {
						p.sendMessage(prefix + "§cUngültiger Gamemode.");
						return true;
					}
					if (Integer.valueOf(arg3[0]) == 1) {
						p.sendMessage(prefix + "§7Dein Gamemode wurde geändert.");
						p.setGameMode(GameMode.CREATIVE);
					} else if (Integer.valueOf(arg3[0]) == 0) {
						p.sendMessage(prefix + "§7Dein Gamemode wurde geändert.");
						p.setGameMode(GameMode.SURVIVAL);
					} else if (Integer.valueOf(arg3[0]) == 2) {
						p.sendMessage(prefix + "§7Dein Gamemode wurde geändert.");
						p.setGameMode(GameMode.ADVENTURE);
					} else if (Integer.valueOf(arg3[0]) == 3) {
						p.sendMessage(prefix + "§7Dein Gamemode wurde geändert.");
						p.setGameMode(GameMode.SPECTATOR);
					}
				} else {
					p.sendMessage(prefix + "§cSyntax: /gm [0|1|2|3]");
				}
			} else {
				p.sendMessage(prefix +chatManager.getMessage("nopermissions"));
			}
		} 

		return true;
	}
}