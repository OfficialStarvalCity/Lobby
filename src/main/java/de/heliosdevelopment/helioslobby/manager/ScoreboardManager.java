package de.heliosdevelopment.helioslobby.manager;

import com.google.common.collect.Lists;
import de.heliosdevelopment.heliosperms.HeliosPerms;
import de.heliosdevelopment.heliosperms.utils.PermissionGroup;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Arrays;
import java.util.List;

public class ScoreboardManager {

	public static void updateScoreboard(Player p) {
        if(p.getScoreboard() == null)
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        Scoreboard board = p.getScoreboard();
        Objective obj;
        if (board.getObjective(p.getName()) != null)
            board.getObjective(p.getName()).unregister();
        obj = board.registerNewObjective(p.getName(), "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(" §eHelios Development ");
        p.setExp(0);
        PermissionGroup group = HeliosPerms.getGroup(p.getUniqueId());
        String prefix = group.getColorCode() + group.getName();
        List<String> Scores = Lists.reverse(Arrays.asList("§9", " §7Rang", "    §8» " + prefix, "§1", " §7Teamspeak", "    §8» §eheliosdevelopment.org", "§2",
                " §7Onlinezeit", "    §8» §a" + 1 + " h", "§3"));
        for (Integer i = 0; i < Scores.size(); i++) {
            obj.getScore(Scores.get(i)).setScore(i);
        }
        p.setScoreboard(board);
	}

}
