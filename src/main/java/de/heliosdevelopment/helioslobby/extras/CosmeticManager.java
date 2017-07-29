package de.heliosdevelopment.helioslobby.extras;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.extras.paticle.ParticleCosmetic;
import de.heliosdevelopment.helioslobby.extras.paticle.ParticleManager;
import de.heliosdevelopment.helioslobby.extras.pet.Pet;
import de.heliosdevelopment.helioslobby.extras.pet.PetCosmetic;
import de.heliosdevelopment.helioslobby.extras.pet.PetManager;
import de.heliosdevelopment.helioslobby.utils.Item;
import de.heliosdevelopment.helioslobby.utils.ItemCreator;
import net.minecraft.server.v1_8_R3.EnumParticle;

/**
 * 
 * @author Theis
 *
 */
public class CosmeticManager {

	private static final List<CosmeticItem> cosmetics = new ArrayList<>();
	private static final int[] positions = new int[] { 10, 11, 12, 13, 14, 15, 16, 20, 21, 22, 23, 24, 30, 31, 32 };
	private static int idCounter = 0;

	public static void init() {
		// Heads
		cosmetics.add(new HeadCosmetic("Seelaterne", CosmeticType.HATS, Material.SEA_LANTERN, 10));
		cosmetics.add(new HeadCosmetic("Kürbis", CosmeticType.HATS, Material.PUMPKIN, 10));
		cosmetics.add(new HeadCosmetic("Melone", CosmeticType.HATS, Material.MELON_BLOCK, 10));
		cosmetics.add(new HeadCosmetic("Kaktus", CosmeticType.HATS, Material.CACTUS, 10));
		cosmetics.add(new HeadCosmetic("Noten Block", CosmeticType.HATS, Material.NOTE_BLOCK, 10));
		cosmetics.add(new HeadCosmetic("Diamant", CosmeticType.HATS, Material.DIAMOND_BLOCK, 10));
		cosmetics.add(new HeadCosmetic("Smaragt", CosmeticType.HATS, Material.EMERALD_BLOCK, 10));
		cosmetics.add(new HeadCosmetic("Astronautenhelm", CosmeticType.HATS, Material.BEACON, 20));
		cosmetics.add(new HeadCosmetic("Skelett", CosmeticType.HATS, Material.SKULL_ITEM, 20, 0));
		cosmetics.add(new HeadCosmetic("Wither", CosmeticType.HATS, Material.SKULL_ITEM, 20, 1));
		cosmetics.add(new HeadCosmetic("Zombie", CosmeticType.HATS, Material.SKULL_ITEM, 20, 2));
		cosmetics.add(new HeadCosmetic("Creeper", CosmeticType.HATS, Material.SKULL_ITEM, 20, 4));
		cosmetics.add(new HeadCosmetic("Mazes_", "Mazes_", CosmeticType.HATS, Material.SKULL_ITEM, 40, 3));
		cosmetics.add(new HeadCosmetic("lvkaas", "lvkaas", CosmeticType.HATS, Material.SKULL_ITEM, 40, 3));
		cosmetics.add(new HeadCosmetic("Teeage", "Teeage", CosmeticType.HATS, Material.SKULL_ITEM, 40, 3));

		// Partikel
		cosmetics.add(new ParticleCosmetic("Cloud", CosmeticType.PARTICLE, Material.BLAZE_POWDER, 15,
				EnumParticle.CLOUD, 0.25, 0.1, 0.25, 0.1, 10));
		cosmetics.add(new ParticleCosmetic("Critical", CosmeticType.PARTICLE, Material.BLAZE_POWDER, 15,
				EnumParticle.CRIT, 0.5, 0.5, 0.5, 0.25, 3));
		cosmetics.add(new ParticleCosmetic("Vulkan", CosmeticType.PARTICLE, Material.BLAZE_POWDER, 15,
				EnumParticle.LAVA, 0, 0, 0, 1, 2));
		cosmetics.add(new ParticleCosmetic("Herzen", CosmeticType.PARTICLE, Material.BLAZE_POWDER, 15,
				EnumParticle.HEART, -0.5, 0.25, -0.5, 1, 1));
		cosmetics.add(new ParticleCosmetic("Blubber", CosmeticType.PARTICLE, Material.BLAZE_POWDER, 15,
				EnumParticle.WATER_DROP, 0.5, 0.0, 0.5, 1, 25));
		cosmetics.add(new ParticleCosmetic("Schneeball", CosmeticType.PARTICLE, Material.BLAZE_POWDER, 15,
				EnumParticle.SNOWBALL, 0.5, 0.0, 0.5, 10, 10));
		cosmetics.add(new ParticleCosmetic("Farbige Noten", CosmeticType.PARTICLE, Material.BLAZE_POWDER, 15,
				EnumParticle.NOTE, 0.5, 1, 0.5, 0.1, 2));
		cosmetics.add(new ParticleCosmetic("Glücklicher Villager", CosmeticType.PARTICLE, Material.BLAZE_POWDER, 15,
				EnumParticle.VILLAGER_HAPPY, 0, 0, 0, 1, 5));
		cosmetics.add(new ParticleCosmetic("Wütender Villager", CosmeticType.PARTICLE, Material.BLAZE_POWDER, 15,
				EnumParticle.VILLAGER_ANGRY, 0, 0, 0, 1, 3));
		cosmetics.add(new ParticleCosmetic("Flammen", CosmeticType.PARTICLE, Material.BLAZE_POWDER, 15,
				EnumParticle.FLAME, 0.1, 0.1, 0.1, 0.01, 10));
		cosmetics.add(new ParticleCosmetic("Hexenmagie", CosmeticType.PARTICLE, Material.BLAZE_POWDER, 15,
				EnumParticle.SPELL_WITCH, 0.0, 0.0, 0.0, 2, 20));
		cosmetics.add(new ParticleCosmetic("Buntes Flimmern", CosmeticType.PARTICLE, Material.BLAZE_POWDER, 15,
				EnumParticle.REDSTONE, 0.5, 0.4, 0.5, 2.0, 20));
		cosmetics.add(new ParticleCosmetic("Lavadrip", CosmeticType.PARTICLE, Material.BLAZE_POWDER, 15,
				EnumParticle.DRIP_LAVA, 0.0, 0.0, 0.0, 5.0, 20));
		cosmetics.add(new ParticleCosmetic("Portal", CosmeticType.PARTICLE, Material.BLAZE_POWDER, 15,
				EnumParticle.PORTAL, 0.0, 0.0, 0.0, 1.0, 15));
		cosmetics.add(new ParticleCosmetic("Wirbel", CosmeticType.PARTICLE, Material.BLAZE_POWDER, 15,
				EnumParticle.SPELL, 0.0, 0.0, 0.0, 0.0, 10));

		// Pets
		cosmetics.add(new PetCosmetic("Kuh", CosmeticType.PETS, Material.MONSTER_EGG, 30, 92, EntityType.COW));
		cosmetics.add(new PetCosmetic("Schwein", CosmeticType.PETS, Material.MONSTER_EGG, 30, 90, EntityType.PIG));
		cosmetics.add(new PetCosmetic("Schaaf", CosmeticType.PETS, Material.MONSTER_EGG, 30, 91, EntityType.SHEEP));
		cosmetics.add(new PetCosmetic("Wolf", CosmeticType.PETS, Material.MONSTER_EGG, 30, 95, EntityType.WOLF));
		cosmetics.add(new PetCosmetic("Katze", CosmeticType.PETS, Material.MONSTER_EGG, 30, 98, EntityType.OCELOT));
		cosmetics.add(new PetCosmetic("Huhn", CosmeticType.PETS, Material.MONSTER_EGG, 30, 93, EntityType.CHICKEN));
		cosmetics.add(
				new PetCosmetic("Pilz Kuh", CosmeticType.PETS, Material.MONSTER_EGG, 30, 96, EntityType.MUSHROOM_COW));
		cosmetics.add(new PetCosmetic("Hase", CosmeticType.PETS, Material.MONSTER_EGG, 30, 101, EntityType.RABBIT));
	}

	public static void openInventory(Player p, CosmeticType type, int site) {
		// Inventory inv = getInventory();

		ArrayList<CosmeticItem> temp = new ArrayList<>();
		// int min = (15 * site) - 15;
		// int max = 15 * site;
		for (CosmeticItem si : cosmetics) {
			if (si.getType() == type) {
				temp.add(si);
			}
		}
		// int i = 0;
		// if (temp.size() == 0) {
		// Item item = new Item(Material.GLOWSTONE_DUST);
		// item.setName("§cNoch im Labor");
		// inv.setItem(23, item.getItem());
		// } else {
		// for (int pos = min; pos <= max; pos++) {
		// if (temp.size() > pos) {
		// inv.setItem(positions[i],
		// temp.get(pos).getItem(CoreAPI.getInstance().getLevel(p)));
		// i++;
		// }
		// }
		// }

		Inventory inv = Bukkit.createInventory(null, 45, "§7Cosmetics");

		Item pane = new Item(Material.STAINED_GLASS_PANE, 1, 7);
		pane.setName(" ");
		// int[] pos = new int[] { 1, 10, 19, 28, 37, 48, 49, 51, 52 };
		for (int i = 0; i < inv.getSize(); i++)
			inv.setItem(i, pane.getItem());
		final int level = 100;
		final boolean hasPermission = p.hasPermission("lobby.unlockall");
		for (int i = 0; i < temp.size(); i++) {
			if (type == CosmeticType.PETS)
				inv.setItem((positions[i + 7]) - 9, temp.get(i).getItem(level, hasPermission));
			else
				inv.setItem(positions[i], temp.get(i).getItem(level, hasPermission));
		}
		new ItemCreator(Material.SKULL_ITEM, 1).durability(3).setSkullOwner("MHF_ArrowLeft").setName("§7Zurück")
				.setSlot(inv, inv.getSize() - 1);
		String name = "";
		switch (type) {
		case HATS:
			name = "§eHut entfernen";
			break;
		case PARTICLE:
			name = "§ePartikeleffekt entfernen";
			break;
		case PETS:
			name = "§eHaustier ins Tierheim geben";
			break;
		default:
			break;
		}
		Dye dye = new Dye();
		dye.setColor(DyeColor.RED);
		Item clear = new Item(dye);
		clear.setName(name);
		inv.setItem(inv.getSize() - 5, clear.getItem());

		p.openInventory(inv);
	}

	public static void openMainInventory(Player player) {
		// Inventory inv = Bukkit.createInventory(null, 54, "§7Cosmetics");
		// Item skull = new Item(Material.SKULL_ITEM, 1, 3);
		// skull.setSkullOwner("Teeage");
		// skull.setName("§eHüte");
		// inv.setItem(0, skull.getItem());
		// Item pets = new Item(Material.MONSTER_EGG, 1, 101);
		// pets.setName("§eHaustiere");
		// inv.setItem(9, pets.getItem());
		// Item armor = new Item(Material.IRON_CHESTPLATE);
		// armor.setName("§eRüstung");
		// inv.setItem(18, armor.getItem());
		// Item gadgets = new Item(Material.TNT);
		// gadgets.setName("§eGadgets");
		// inv.setItem(27, gadgets.getItem());
		// Item particle = new Item(Material.BLAZE_POWDER);
		// particle.setName("§ePartikel");
		// inv.setItem(36, particle.getItem());
		//
		// Item pane = new Item(Material.STAINED_GLASS_PANE, 1, 15);
		// pane.setName(" ");
		// int[] pos = new int[] { 1, 10, 19, 28, 37, 48, 49, 51, 52 };
		// for (int i = 0; i < 9; i++)
		// inv.setItem(pos[i], pane.getItem());
		//
		// Item switchSite = new Item(Material.SKULL_ITEM, 1, 3);
		// switchSite.setName("§eVorherige Seite");
		// switchSite.setSkullOwner("MHF_ArrowLeft");
		// inv.setItem(47, switchSite.getItem());
		// switchSite.setName("§eNächste Seite");
		// switchSite.setSkullOwner("MHF_ArrowRight");
		// inv.setItem(53, switchSite.getItem());
		//
		// Item remove = new Item(Material.BARRIER);
		// remove.setName("§cExtras zurücksetzen");
		// inv.setItem(50, remove.getItem());
		//
		// return inv;

		Inventory inv = Bukkit.createInventory(null, 36, "§7Cosmetics");

		Item pane = new Item(Material.STAINED_GLASS_PANE, 1, 7);
		pane.setName(" ");
		// int[] pos = new int[] { 1, 10, 19, 28, 37, 48, 49, 51, 52 };
		for (int i = 0; i < inv.getSize(); i++)
			inv.setItem(i, pane.getItem());

		Item skull = new Item(Material.SKULL_ITEM, 1, 3);
		skull.setSkullOwner(player.getName());
		skull.setName("§eHüte");
		inv.setItem(13, skull.getItem());
		Item pets = new Item(Material.MONSTER_EGG, 1, 101);
		pets.setName("§eHaustiere");
		inv.setItem(24, pets.getItem());
		// Item armor = new Item(Material.IRON_CHESTPLATE);
		// armor.setName("§eRüstung");
		// inv.setItem(18, armor.getItem());
		// Item gadgets = new Item(Material.TNT);
		// gadgets.setName("§eGadgets");
		// inv.setItem(27, gadgets.getItem());
		Item particle = new Item(Material.BLAZE_POWDER);
		particle.setName("§ePartikel");
		inv.setItem(20, particle.getItem());
		// new ItemCreator(Material.SKULL_ITEM,
		// 1).durability(3).setSkullOwner("MHF_ArrowLeft").setName("§7Zurück")
		// .setSlot(inv, inv.getSize() - 1);
		player.openInventory(inv);

	}

	public static CosmeticItem getCosmeticItem(int id) {
		for (CosmeticItem ci : cosmetics) {
			if (ci.getId() == id)
				return ci;
		}
		return null;
	}

	public static CosmeticItem getCosmeticItem(Material mat, String name) {
		if (name == null) {
			for (CosmeticItem ci : cosmetics) {
				if (ci.getMaterial() == mat)
					return ci;
			}
		}
		if (name.contains("'s Kopf"))
			name = name.replace("'s Kopf", "");
		for (CosmeticItem ci : cosmetics) {
			if (ci.getMaterial() == mat && ci.getName().equalsIgnoreCase(name))
				return ci;
		}
		return null;
	}

	public static int getNextId() {
		return idCounter++;
	}

	public static List<CosmeticItem> getItems(CosmeticType type) {
		ArrayList<CosmeticItem> temp = new ArrayList<>();
		for (CosmeticItem si : cosmetics) {
			if (si.getType() == type) {
				temp.add(si);
			}
		}
		return temp;
	}

	public static void openColorInventory(Player player) {
		Inventory inv = Bukkit.createInventory(null, 18, "§eFärben");
		Dye dye = new Dye();
		Item item;
		for (DyeColor c : DyeColor.values()) {
			dye.setColor(c);
			item = new Item(dye);
			item.setName("§e" + c.name());
			item.setLore("§7Klicke um dein Schaf zu färben.");
			inv.addItem(item.getItem());
		}
		new ItemCreator(Material.SKULL_ITEM, 1).durability(3).setSkullOwner("MHF_ArrowLeft").setName("§7Zurück")
				.setSlot(inv, inv.getSize() - 1);
		player.openInventory(inv);
	}

	public static void updateCosmetics(Player player) {
		if (player != null) {
			Pet pet = PetManager.getPet(player);
			ParticleCosmetic effect = ParticleManager.getParticle().get(player);
			ItemStack head = player.getInventory().getHelmet();
			HeadCosmetic headCosmetic = null;
			if (head != null && head.getItemMeta() != null && head.getItemMeta().getDisplayName() != null
					&& head.getType() != null) {
				headCosmetic = (HeadCosmetic) CosmeticManager.getCosmeticItem(
						player.getInventory().getHelmet().getType(),
						ChatColor.stripColor(player.getInventory().getHelmet().getItemMeta().getDisplayName()));
			}
			if (Lobby.getInstance().getMysql().getCosmetics(player.getUniqueId().toString()).isEmpty()) {
				Lobby.getInstance().getMysql().insertCosmetics(player.getUniqueId().toString(),
						pet != null ? PetManager.getPet(pet.getAnimal().getType()).getId() : -1,
						pet != null ? encode(pet.getName()) : "-31", pet != null ? (pet.isAdult() ? 1 : 0) : -1,
						effect != null ? effect.getId() : -1, headCosmetic != null ? headCosmetic.getId() : -1);
			} else {
				Lobby.getInstance().getMysql().updateCosmetics(player.getUniqueId().toString(),
						pet != null ? PetManager.getPet(pet.getAnimal().getType()).getId() : -1,
						pet != null ? encode(pet.getName()) : "-31", pet != null ? (pet.isAdult() ? 1 : 0) : -1,
						effect != null ? effect.getId() : -1, headCosmetic != null ? headCosmetic.getId() : -1);
			}
			PetManager.despawnPet(player);
			ParticleManager.removePlayer(player);
			player.getInventory().setHelmet(null);
		}
	}

	public static void equitItems(Player player) {
		if (player != null) {
			List<Object> cosmetics = Lobby.getInstance().getMysql().getCosmetics(player.getUniqueId().toString());
			if (cosmetics.size() != 5) {
				System.out.println("CosmeticManager/equitItemsNot/enough Items");
				for (Object o : cosmetics)
					System.out.println(o.toString());
				return;
			}

			if (Integer.valueOf(cosmetics.get(0).toString()) != -1
					&& !cosmetics.get(1).toString().equalsIgnoreCase("-31")
					&& Integer.valueOf(cosmetics.get(2).toString()) != -1) {
				PetManager.addPet(player, new Pet(player,
						((PetCosmetic) CosmeticManager.getCosmeticItem(Integer.valueOf(cosmetics.get(0).toString())))
								.getEntityType(),
						cosmetics.get(1).toString().replace("_", " "),
						Integer.valueOf(decode(cosmetics.get(2).toString()))));
			}
			if (Integer.valueOf(cosmetics.get(3).toString()) != -1) {
				ParticleManager.addPlayer(player, (ParticleCosmetic) CosmeticManager
						.getCosmeticItem(Integer.valueOf(cosmetics.get(3).toString())));
			}
			if (Integer.valueOf(cosmetics.get(4).toString()) != -1) {
				HeadCosmetic headCosmetic = (HeadCosmetic) CosmeticManager
						.getCosmeticItem(Integer.valueOf(cosmetics.get(4).toString()));
				if (headCosmetic != null)
					headCosmetic.equipItem(player);
			}

		}
	}

	private static String encode(String name) {
		if (name.contains("'"))
			name = name.replace("'", ":-");
		return name;
	}

	private static String decode(String name) {
		if (name.contains(":-"))
			name = name.replace(":-", "'");
		return name;
	}

}
