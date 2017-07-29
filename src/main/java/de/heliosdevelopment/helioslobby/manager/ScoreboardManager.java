package de.heliosdevelopment.helioslobby.manager;

import org.bukkit.entity.Player;

public class ScoreboardManager {

	public static void updateScoreboard(Player p) {

		/*Scoreboard board = p.getScoreboard();
		Objective obj;
		if (board.getObjective(p.getName()) != null)
			board.getObjective(p.getName()).unregister();
		obj = board.registerNewObjective(p.getName(), "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(" §eHelios Development ");
		p.setExp(0);
		// Group group = VentrasPerms.getGroup(p.getUniqueId());
		// String prefix = group.getColorCode() + group.getName();
		List<String> Scores = Lists.reverse(Arrays.asList("§9", " §b➜ §7Rang", "    §8» User", "§1"));
		// " §b✦ §7Onlinezeit", " §8» §a" + 1 + " h", "§e", " §b✪ §7Level", " §8»
		// §e"+VentrasCore.getInstance().getLevel(p), "§3"));
		for (Integer i = 0; i < Scores.size(); i++) {
			obj.getScore(Scores.get(i)).setScore(i);
		}
		p.setScoreboard(board);*/
	}

}
