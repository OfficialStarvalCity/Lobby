package de.heliosdevelopment.helioslobby.extras;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.heliosdevelopment.helioslobby.utils.Item;

public abstract class CosmeticItem {

	private final int id;
	private final String name;
	private final CosmeticType type;
	private final int price;
	private final Material material;
	private final int data;
	private final Color color;

	public CosmeticItem(String name, CosmeticType type, Material mat, int price) {
		this.id = CosmeticManager.getNextId();
		this.name = name;
		this.type = type;
		this.price = price;
		this.material = mat;
		this.data = 0;
		this.color = null;
	}

	public CosmeticItem(String name, CosmeticType type, Material mat, int price, int data) {
		this.id = CosmeticManager.getNextId();
		this.name = name;
		this.type = type;
		this.price = price;
		this.material = mat;
		this.data = data;
		this.color = null;
	}

	public CosmeticItem(String name, CosmeticType type, Material mat, int price, Color color) {
		this.id = CosmeticManager.getNextId();
		this.name = name;
		this.type = type;
		this.price = price;
		this.material = mat;
		this.data = 0;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public CosmeticType getType() {
		return type;
	}

	public int getPrice() {
		return price;
	}

	public Material getMaterial() {
		return material;
	}

	public int getData() {
		return data;
	}

	public int getId() {
		return id;
	}

	public ItemStack getItem(boolean hasItem, boolean hasPermission) {
		Item i = new Item(material, 1, data);
		i.setName("§6" + name);
		if (type == CosmeticType.HATS && material.equals(Material.SKULL_ITEM)) {
			i.setSkullOwner(name);
			i.setName("§6" + name + "'s Kopf");
		}
		if (type == CosmeticType.ARMOR && color != null)
			i.setLeatherColor(color);
		if (hasItem || hasPermission) {
			i.addLore("", "§aFreigeschaltet");
			i.addEnchantment(Enchantment.KNOCKBACK, 1);
		} else
			i.addLore("", "§cKosten §7" + price);
		i.hideFlags();
		return i.getItem();

	}

	public abstract void equipItem(Player player);

}
