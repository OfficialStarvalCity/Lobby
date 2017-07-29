package de.heliosdevelopment.helioslobby.extras;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.heliosdevelopment.helioslobby.utils.Item;

public class HeadCosmetic extends CosmeticItem {

	private String headName;

	public HeadCosmetic(String name, CosmeticType type, Material mat, int requeredLevel, int data) {
		super(name, type, mat, requeredLevel, data);
	}

	public HeadCosmetic(String owner, String name, CosmeticType type, Material mat, int requeredLevel, int data) {
		super(name, type, mat, requeredLevel, data);
		headName = owner;
	}

	public HeadCosmetic(String name, CosmeticType type, Material mat, int requeredLevel) {
		super(name, type, mat, requeredLevel);
	}

	@Override
	public void equipItem(Player player) {
		Item skull = new Item(getMaterial(), 1, getData());
		if (getMaterial() == Material.SKULL_ITEM || getData() == 3) {
			if (headName != null) {
				skull.setSkullOwner(getName());
				skull.setName("§7" + getName() + "'s Kopf");
			} else {
				skull.setSkullOwner(headName);
				skull.setName("§7" + headName + "'s Kopf");
			}
		}
		player.getInventory().setHelmet(skull.getItem());
		player.updateInventory();

	}

}
