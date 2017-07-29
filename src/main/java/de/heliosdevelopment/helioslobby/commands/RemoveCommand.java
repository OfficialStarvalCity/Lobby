package de.heliosdevelopment.helioslobby.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.heliosdevelopment.helioslobby.Lobby;

public class RemoveCommand extends LobbyCommand {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs instanceof Player) {
			Player p = (Player) cs;
			if (p.hasPermission("lobby.remove")) {
				if (args.length == 0) {
					p.sendMessage(prefix + "§cDu musst einen Namen angeben!");
				} else if (args.length == 1) {
					Lobby.getInstance().getMysql().removeSpawn(args[0]);
					p.sendMessage(prefix + "§aDu hast den Spawn " + args[0] + " entfernt.");
				}

			} else
				p.sendMessage(prefix +chatManager.getMessage("nopermissions"));
		}
		return true;
	}
}
