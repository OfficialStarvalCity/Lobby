package de.heliosdevelopment.helioslobby.manager;

import com.google.common.collect.Lists;
import de.heliosdevelopment.helioslobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardManager {

    private static final String title = ChatColor.translateAlternateColorCodes('&',
            Lobby.getInstance().getConfig().getString("scoreboard.title"));
    private static final List<String> scoreboard = new ArrayList<>();

    static {
        for (String string : Lobby.getInstance().getConfig().getStringList("scoreboard.layout"))
            scoreboard.add(ChatColor.translateAlternateColorCodes('&', string));
    }


    //availible placeholders onlinerang, rank
    public static void updateScoreboard(Player player) {
        if(!SettingManager.getSetting("scoreboard").isEnabled())
            return;
        if (player.getScoreboard() == null)
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        Scoreboard board = player.getScoreboard();
        Objective obj;
        if (board.getObjective(player.getName()) == null) {
            obj = board.registerNewObjective(player.getName(), "dummy");
        } else {
            obj = board.getObjective(player.getName());
            obj.unregister();
            obj = board.registerNewObjective(player.getName(), "dummy");
        }
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(title);

        List<String> scores = Lists.reverse(scoreboard);
        for (Integer i = 0; i < scores.size(); i++) {
            String string = scores.get(i);
            if (string.contains("%rank%")) {
                //de.heliosdevelopment.heliosperms.utils.PermissionGroup group = de.heliosdevelopment.heliosperms.HeliosPerms.getGroup(player.getUniqueId());
                //string = string.replace("%rank%",group.getColorCode() + group.getName());
                string = "§4None";
            }
            if (string.contains("%onlinetime%")) {
                int onlineTime = Lobby.getInstance().getMysql().getOnlineTime(player.getUniqueId());
                if (onlineTime <= 60)
                    onlineTime = 1;
                else
                    onlineTime = onlineTime / 60;
                string = string.replace("%onlinetime%", String.valueOf(onlineTime));
            }
            obj.getScore(string).setScore(i);
        }
        player.setScoreboard(board);
    }

}
