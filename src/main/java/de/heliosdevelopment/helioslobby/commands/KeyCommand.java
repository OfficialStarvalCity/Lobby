package de.heliosdevelopment.helioslobby.commands;

import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.player.LobbyPlayer;
import de.heliosdevelopment.helioslobby.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KeyCommand extends LobbyCommand {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            if (p.hasPermission("lobby.givekeys")) {
                if (args.length == 0) {
                    p.sendMessage(prefix + "§c/keys (player) (give/take) (amount)");
                } else if (args.length == 3) {
                    if (args[1].equalsIgnoreCase("give")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target == null) {
                            p.sendMessage(prefix + "§cDer Spieler ist nicht online!");
                            return true;
                        }
                        int keys = Integer.valueOf(args[2]);
                        LobbyPlayer lobbyPlayer = PlayerManager.getPlayer(target);
                        if (lobbyPlayer != null) {
                            lobbyPlayer.setKeys(lobbyPlayer.getKeys() + keys);
                            p.sendMessage(prefix + "§aDu hast dem Spieler §e" + args[0] + " §e" + keys + " §aSchlüssel gegeben.");
                        }
                    } else if (args[1].equalsIgnoreCase("take")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target == null) {
                            p.sendMessage(prefix + "§cDer Spieler ist nicht online!");
                            return true;
                        }
                        int keys = Integer.valueOf(args[2]);
                        LobbyPlayer lobbyPlayer = PlayerManager.getPlayer(target);
                        if (lobbyPlayer != null) {
                            keys = lobbyPlayer.getKeys() - keys;
                            if (keys < 0)
                                keys = 0;
                            lobbyPlayer.setKeys(keys);
                            p.sendMessage(prefix + "§aDu hast dem Spieler §e" + args[0] + " §e" + keys + " §aSchlüssel weggenommen.");
                        }
                    }
                }

            } else
                p.sendMessage(prefix + chatManager.getMessage("nopermissions"));
        }
        return true;
    }
}
