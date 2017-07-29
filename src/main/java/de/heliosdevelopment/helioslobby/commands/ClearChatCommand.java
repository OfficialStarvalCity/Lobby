package de.heliosdevelopment.helioslobby.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChatCommand extends LobbyCommand {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs instanceof Player) {
			Player p = (Player) cs;
			if (p.hasPermission("lobby.clearchat")) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					for (int i = 0; i < 200; i++)
						player.sendMessage(" ");
					player.sendMessage(
							prefix + "§7Der Chat wurde von §a" + p.getName() + " §7gecleared.");
				}
			} else
				p.sendMessage(prefix +chatManager.getMessage("nopermissions"));
		}
		return true;
	}
}
