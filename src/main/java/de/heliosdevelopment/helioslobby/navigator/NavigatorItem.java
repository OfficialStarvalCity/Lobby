package de.heliosdevelopment.helioslobby.navigator;

import org.bukkit.Location;
import org.bukkit.Material;

public class NavigatorItem {
	private final String name;
	private final Integer slot;
	private final Location location;
	private final Material material;

	public NavigatorItem(String name, Integer slot, Location location, Material material) {
		this.name = name;
		this.slot = slot;
		this.location = location;
		this.material = material;
	}

	public String getName() {
		return name;
	}

	public Integer getSlot() {
		return slot;
	}

	public Location getLocation() {
		return location;
	}

	public Material getMaterial() {
		return material;
	}
}
