package de.heliosdevelopment.helioslobby.item;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import de.heliosdevelopment.helioslobby.utils.Item;

public abstract class LobbyItem {

	private final String name;
	private final Material material;
	private final String permission;
	private final List<String> lore;
	private final List<String> alias;
	private final Dye dye;

	public LobbyItem(String name, Material material, String permission, List<String> lore, List<String> alias) {
		this(name, material, permission, lore, alias, null);
	}

	public LobbyItem(String name, Material material, String permission, List<String> lore, List<String> alias,
			Dye dye) {
		this.name = name;
		this.material = material;
		this.lore = lore;
		this.permission = permission;
		this.alias = alias;
		this.dye = dye;
	}

	public String getName() {
		return name;
	}

	public Material getMaterial() {
		return material;
	}

	public List<String> getLore() {
		return lore;
	}

	public String getPermission() {
		return permission;
	}

	public ItemStack getItem() {
		Item item;
		if (dye != null)
			item = new Item(dye);
		else
			item = new Item(material);
		item.setName(name);
		if (lore != null)
			item.setLore(lore);
		return item.getItem();
	}

	public List<String> getAlias() {
		return alias;
	}

	public abstract void onInteract(Player player);

}
