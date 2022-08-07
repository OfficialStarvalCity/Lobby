package de.heliosdevelopment.helioslobby.commands;

import de.heliosdevelopment.helioslobby.Lobby;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.heliosdevelopment.helioslobby.item.ItemManager;

public class ClearCommand extends LobbyCommand {

	private final ItemManager itemManager = Lobby.getInstance().getItemManager();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender instanceof Player)) {
			if (args.length == 0) {
				Player p = (Player) sender;
				itemManager.clearInventory(p);
				itemManager.getItems(p);
				p.sendMessage(prefix + "§7Du hast dein Inventar geleert.");
			}
		} else {
			sender.sendMessage(prefix + "§cDu musst ein Spieler sein!");
		}
		return true;
	}
}
