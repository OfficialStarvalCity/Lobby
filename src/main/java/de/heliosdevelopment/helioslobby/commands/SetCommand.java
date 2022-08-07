package de.heliosdevelopment.helioslobby.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.heliosdevelopment.helioslobby.Lobby;

public class SetCommand extends LobbyCommand {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            if (p.hasPermission("lobby.set")) {
                if (args.length == 0) {
                    p.sendMessage(prefix + "§cDu musst einen Namen angeben!");
                } else if (args.length == 1) {
                    Lobby.getInstance().getMysql().addSpawn(args[0], p.getLocation());
                    p.sendMessage(prefix + "§aDu hast den Spawn für " + args[0] + "gesetzt.");
                }

            } else
                p.sendMessage(prefix + chatManager.getMessage("nopermissions"));
        }
        return true;
    }
}
