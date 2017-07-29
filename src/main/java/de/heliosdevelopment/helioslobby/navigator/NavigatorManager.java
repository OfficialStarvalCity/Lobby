package de.heliosdevelopment.helioslobby.navigator;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.MySQL;
import de.heliosdevelopment.helioslobby.utils.ItemCreator;

public class NavigatorManager {

	private final static Inventory inventory = Bukkit.createInventory(null, 27, "§eNavigator");
	private final static List<NavigatorItem> items = new ArrayList<>();
	private final static MySQL mysql = Lobby.getInstance().getMysql();

	public static void openNavigator(Player player) {
		player.openInventory(inventory);
	}

	public static void init() {

		items.add(new NavigatorItem("Spawn", 22, mysql.getSpawn("spawn"), Material.BEACON));
		items.add(new NavigatorItem("Testserver", 4, mysql.getSpawn("testserver"), Material.IRON_SWORD));

		for (Integer i = 0; i < inventory.getSize(); i++) {
			new ItemCreator(Material.STAINED_GLASS_PANE, 1).durability(7).hideFlags().setName("§1").setSlot(inventory,
					i);
		}
		for (NavigatorItem item : items) {
			new ItemCreator(item.getMaterial(), 1).setName("§a" + item.getName()).hideFlags().setSlot(inventory,
					item.getSlot());
		}
	}

	public static List<NavigatorItem> getItems() {
		return items;
	}

	public static NavigatorItem getItem(String name) {
		for (NavigatorItem item : items)
			if (item.getName().toLowerCase().equals(name.toLowerCase()))
				return item;
		return null;
	}

}
