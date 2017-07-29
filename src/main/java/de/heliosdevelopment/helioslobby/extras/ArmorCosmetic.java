package de.heliosdevelopment.helioslobby.extras;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.heliosdevelopment.helioslobby.utils.Item;

public class ArmorCosmetic extends CosmeticItem {

	private final ArmorPosition position;
	private final Color color;

	public ArmorCosmetic(String name, CosmeticType type, Material mat, int requeredLevel, ArmorPosition position,
			Color color) {
		super(name, type, mat, requeredLevel, color);
		this.position = position;
		this.color = color;
	}

	public ArmorCosmetic(String name, CosmeticType type, Material mat, int requeredLevel, ArmorPosition position) {
		super(name, type, mat, requeredLevel);
		this.position = position;
		color = null;
	}

	public ArmorPosition getPosition() {
		return position;
	}

	public Color getColor() {
		return color;
	}

	public enum ArmorPosition {
		HEAD, CHEST, LEGS, FEET
	}

	@Override
	public void equipItem(Player player) {
		Item armor = new Item(getMaterial(), 1);
		if (color != null)
			armor.setLeatherColor(color);
		switch (position) {
		case HEAD:
			player.getInventory().setHelmet(armor.getItem());
			break;
		case CHEST:
			player.getInventory().setChestplate(armor.getItem());
			break;
		case LEGS:
			player.getInventory().setLeggings(armor.getItem());
			break;
		case FEET:
			player.getInventory().setBoots(armor.getItem());
			break;
		}
	}

}
