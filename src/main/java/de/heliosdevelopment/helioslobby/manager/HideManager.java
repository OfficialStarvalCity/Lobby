package de.heliosdevelopment.helioslobby.manager;

import de.heliosdevelopment.helioslobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;

import de.heliosdevelopment.helioslobby.player.LobbyPlayer;
import de.heliosdevelopment.helioslobby.player.PlayerManager;
import de.heliosdevelopment.helioslobby.player.Visibility;

public class HideManager {

	public static void setVisibility(Player player, ItemStack item) {
		LobbyPlayer lobbyPlayer = PlayerManager.getPlayer(player);
		if (lobbyPlayer == null) {
			player.sendMessage("lol du existierst nicht im System");
			return;
		}
		if(item == null)
			return;
		ItemMeta meta = item.getItemMeta();
		Dye dye = new Dye();
		switch (lobbyPlayer.getVisibility()) {
		case ALL:
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (!players.hasPermission("lobby.advanced"))
					player.hidePlayer(players);
			}
			dye.setColor(DyeColor.PURPLE);
			lobbyPlayer.setVisibility(Visibility.PREMIUM);
			player.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix")+"§7Dir werden nur noch §6Premium §7Spieler angezeigt.");
			break;
		case NONE:
			for (Player players : Bukkit.getOnlinePlayers()) {
				player.showPlayer(players);
			}
			lobbyPlayer.setVisibility(Visibility.ALL);
			dye.setColor(DyeColor.LIME);
			meta.setDisplayName("§7× §eSpieler Verstecken §7×");
			player.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix")+"§7Dir werden nun §aalle §7Spieler angezeigt.");
			break;
		case PREMIUM:
			for (Player players : Bukkit.getOnlinePlayers()) {
				player.hidePlayer(players);
			}
			dye.setColor(DyeColor.GRAY);
			lobbyPlayer.setVisibility(Visibility.NONE);
			meta.setDisplayName("§7× §eSpieler anzeigen §7×");
			player.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix")+"§7Dir werden nun §ckeine §7Spieler mehr angezeigt.");
			break;
		default:
			break;

		}
		ItemStack stack = dye.toItemStack(1);
		stack.setItemMeta(meta);
		player.setItemInHand(stack);
		player.playSound(player.getLocation(), SoundManager.getSound(SoundManager.Sound.NOTE_PIANO), 1, 1);
	}

}
