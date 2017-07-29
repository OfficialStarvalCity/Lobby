package de.heliosdevelopment.helioslobby.extras;

import org.bukkit.ChatColor;

public enum CosmeticType {

	PETS, HATS, PARTICLE, GADGETS, ARMOR;

	public static CosmeticType getCosmeticTypebyName(String name) {
		switch (ChatColor.stripColor(name)) {
		case "Hüte":
			return CosmeticType.HATS;
		case "Haustiere":
			return CosmeticType.PETS;
		case "Rüstung":
			return CosmeticType.ARMOR;
		case "Gadgets":
			return CosmeticType.GADGETS;
		case "Partikel":
			return CosmeticType.PARTICLE;
		default:
			return CosmeticType.HATS;
		}

	}

}
