package de.heliosdevelopment.helioslobby.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;

public class Item {

	private ItemStack i;
	private final ItemMeta m;
	private List<String> l = new ArrayList<>();
	private byte dyecolor = 20;
	private byte potion = 0;
	private byte dataid = 0;
	private LeatherArmorMeta lc;
	private String skull;

	/**
	 * Create a new Item with a definded Material and an amount
	 *
	 * @param m
	 *            The Material of the item
	 * @param amount
	 *            The amount of the item
	 */
	public Item(Material m, int amount) {
		i = new ItemStack(m, amount);
		this.m = i.getItemMeta();
		if (isLeather())
			lc = (LeatherArmorMeta) i.getItemMeta();
	}

	/**
	 * Create a new Item with a defined Material Amount is 1
	 *
	 * @param m
	 *            The Material of the item
	 */
	public Item(Material m) {
		i = new ItemStack(m);
		this.m = i.getItemMeta();
		if (isLeather())
			lc = (LeatherArmorMeta) i.getItemMeta();
	}

	/**
	 * Create a new Dye to ItemStack
	 *
	 * @param d
	 *            The Dye of the item
	 */
	public Item(Dye d) {
		i = d.toItemStack();
		i.setAmount(1);
		this.m = i.getItemMeta();
		if (isLeather())
			lc = (LeatherArmorMeta) i.getItemMeta();
	}

	/**
	 * Create a new Item with a definded Material and an amount
	 *
	 * @param m
	 *            The Material of the item
	 * @param amount
	 *            The amount of the item
	 */
	public Item(Material m, int amount, int data) {
		i = new ItemStack(m, amount);
		this.m = i.getItemMeta();
		this.dataid = (byte) data;
		if (isLeather())
			lc = (LeatherArmorMeta) i.getItemMeta();
	}

	/**
	 * Give the item a displayname
	 *
	 * @param name
	 *            The displayname of the item
	 */
	public void setName(String name) {
		m.setDisplayName(name);
	}

	public String getName() {
		return m.getDisplayName();
	}

	/**
	 * Return the material of the item
	 *
	 * @return The material of the item
	 */
	public Material getMaterial() {
		return i.getType();
	}

	/**
	 * Return the amount of the item
	 *
	 * @return The amount of the item
	 */
	public int getAmount() {
		return i.getAmount();
	}

	/**
	 * Remove the lore
	 */
	public void removeLore() {
		l.clear();
	}

	/**
	 * Add Strings to the lore of the item
	 *
	 * @param lore
	 *            Add to the lore of the item
	 */
	public void addLore(String... lore) {
		Collections.addAll(l, lore);
	}

	/**
	 * Add Strings to the lore of the item
	 *
	 * @param lore
	 *            Add to the lore of the item
	 */
	public void addLore(List<String> lore) {
		for (String s : lore)
			l.add(s);
	}

	/**
	 * Set the lore of the item Remove the old lore
	 *
	 * @param lore
	 *            Set the lore of the item
	 */
	public void setLore(String... lore) {
		clearLore();
		Collections.addAll(l, lore);
	}

	/**
	 * Set the lore of the item Remove the old lore
	 *
	 * @param lore
	 *            Set the lore of the item
	 */
	public void setLore(List<String> lore) {
		l = lore;
	}

	/**
	 * Clear the lore
	 */
	public void clearLore() {
		l.clear();
	}

	/**
	 * Create a Player Skull Material must be Material.SKULL_ITEM
	 *
	 * @param name
	 *            The name of the Player
	 */
	public void setSkullOwner(String name) {
		skull = name;
	}

	/**
	 * Set the material of the item
	 *
	 * @param m
	 *            Material of the item
	 */
	public void setMaterial(Material m) {
		i.setType(m);
	}

	/**
	 * Set the amount of the item
	 *
	 * @param m
	 *            Amount of the item
	 */
	public void setAmount(int amount) {
		i.setAmount(amount);
	}

	/**
	 * Set the color of the item (Supported: wool, Stained Clay, Stained Glass and
	 * Carpet)
	 *
	 * @param colorid
	 *            Color-id <a href="http://bit.ly/1lGeJgS">Click to see a list</a>
	 */
	public void setColor(int colorid) {
		dyecolor = (byte) colorid;
	}

	/**
	 * Set the data of the item
	 *
	 * @param colorid
	 *            Color-id
	 */
	public void setData(int data) {
		dataid = (byte) data;
	}

	/**
	 * Returns the data ID
	 *
	 * @return byte
	 */
	public byte getData() {
		return dataid;
	}

	/**
	 * Add an enchantment with a specific level
	 *
	 * @param ench
	 *            Enchantment of the item
	 * @param level
	 *            The level of the item as an int
	 */
	public void addEnchantment(Enchantment ench, int level) {
		m.addEnchant(ench, level, true);
	}

	private boolean isLeather() {
        return getMaterial() == Material.LEATHER_BOOTS || getMaterial() == Material.LEATHER_CHESTPLATE
                || getMaterial() == Material.LEATHER_HELMET || getMaterial() == Material.LEATHER_LEGGINGS;

    }

	/**
	 * Set the color of the leather armor
	 *
	 * @param c
	 *            Color of the leather armor
	 */
	public void setLeatherColor(Color c) {
		if (isLeather()) {
			lc.setColor(c);
		}
	}

	/**
	 * Set the the potion id of the potion <a href="http://bit.ly/1rqDJuE">IDs</a>
	 *
	 * @param id
	 *            The id of the potion
	 */
	public void setPotion(int id) {
		potion = (byte) id;
	}

	/**
	 * The only way to get your Item to use it
	 *
	 * @return The Item as an itemstack
	 */
	public ItemStack getItem() {
		if (dyecolor < 20) {
			i = new ItemStack(i.getType(), i.getAmount(), dyecolor);
		}
		if (potion > 0) {
			i = new ItemStack(i.getType(), i.getAmount(), potion);
		}
		if (dataid != 0) {
			i = new ItemStack(i.getType(), i.getAmount(), dataid);
		}
		if (skull != null && i.getType() == Material.SKULL_ITEM) {
			SkullMeta meta = (SkullMeta) i.getItemMeta();
			meta.setOwner(skull);
			meta.setDisplayName(m.getDisplayName());
			meta.setLore(l);
			i.setItemMeta(meta);
			return i;
		}
		m.setLore(l);
		if (isLeather()) {
			lc.setDisplayName(m.getDisplayName());
			lc.setLore(l);
			i.setItemMeta(lc);
		} else {
			i.setItemMeta(m);
		}
		return i;
	}

	public void hideFlags() {
		m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		m.addItemFlags(ItemFlag.HIDE_DESTROYS);
		m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		m.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		m.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		m.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		i.setItemMeta(m);
	}
}
