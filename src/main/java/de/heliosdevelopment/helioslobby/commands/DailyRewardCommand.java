package de.heliosdevelopment.helioslobby.commands;

import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.manager.DailyReward;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DailyRewardCommand extends LobbyCommand {

    private final DailyReward reward;

    public DailyRewardCommand(DailyReward dailyReward) {
        this.reward = dailyReward;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            if (p.hasPermission("lobby.setreward")) {
                if (cmd.getName().equalsIgnoreCase("spawnWitch")) {
                    reward.spawnEntity(p.getLocation());
                    p.sendMessage(prefix + "§eDu hast eine Lobby Hexe erstellt!");
                } else if (cmd.getName().equalsIgnoreCase("despawnWitch")) {
                    reward.despawnEntity(p.getLocation());
                    p.sendMessage(prefix + "§eDu hast alle Lobby Hexen entfernt!");
                }
            } else {
                p.sendMessage(prefix + chatManager.getMessage("nopermissions"));
            }
        }
        return true;
    }

}
