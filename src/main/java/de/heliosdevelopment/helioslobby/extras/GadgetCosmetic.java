package de.heliosdevelopment.helioslobby.extras;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.heliosdevelopment.helioslobby.utils.Item;

public class GadgetCosmetic extends CosmeticItem {

	public GadgetCosmetic(String name, CosmeticType type, Material mat, int requeredLevel, int data) {
		super(name, type, mat, requeredLevel, data);
	}

	@Override
	public void equipItem(Player player) {
		player.getInventory().setItem(4, new Item(getMaterial(), 1, getData()).getItem());
	}

}
