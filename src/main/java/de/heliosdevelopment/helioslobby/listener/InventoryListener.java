package de.heliosdevelopment.helioslobby.listener;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.heliosdevelopment.helioslobby.navigator.NavigatorItem;
import de.heliosdevelopment.helioslobby.navigator.NavigatorManager;

public class InventoryListener implements Listener {

	@EventHandler
	public void onNavigatorInvInteract(InventoryClickEvent e) {
		if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null
				|| e.getCurrentItem().getItemMeta().getDisplayName() == null || e.getInventory().getName() == null)
			return;
		Player p = (Player) e.getWhoClicked();
		e.setCancelled(true);
		if (e.getInventory().getName().equalsIgnoreCase("§eNavigator")) {
			e.setCancelled(true);
			String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
			for (NavigatorItem item : NavigatorManager.getItems()) {
				if (item.getName().equalsIgnoreCase(name)) {
					p.teleport(item.getLocation());
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
				}
			}
		}
	}

}
