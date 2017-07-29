package de.heliosdevelopment.helioslobby.extras.pet;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.extras.CosmeticItem;
import de.heliosdevelopment.helioslobby.extras.CosmeticManager;
import de.heliosdevelopment.helioslobby.extras.CosmeticType;

public class PetManager {

	private static final Map<Player, Pet> pets = new HashMap<>();

	public static void addPet(Player player, Pet pet) {
		if (pets.containsKey(player)) {
			player.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix") + "§eReicht dir eins nicht???");
			return;
		}
		pets.put(player, pet);
	}

	public static synchronized void despawnPet(Player p) {

		Pet pet = pets.get(p);

		if (pet != null) {

			pets.remove(p);
			pet.despawnPet();

		}

	}

	public static void teleportToPlayer(Creature creature, Location loc) {
		creature.teleport(loc);
	}

	public static void despawnAll() {
		for (Pet pet : pets.values()) {
			pet.despawnPet();
		}

	}

	public static Map<Player, Pet> getPets() {
		return pets;

	}

	public static boolean isPet(int id) {
		for (Pet pet : pets.values()) {
			if (pet.getAnimal().getEntityId() == id)
				return true;
		}
		return false;
	}

	public static Pet getPet(int id) {
		for (Pet pet : pets.values()) {
			if (pet.getAnimal().getEntityId() == id)
				return pet;
		}
		return null;
	}

	public static Pet getPet(Player owner) {
		for (Pet pet : pets.values()) {
			if (pet.getOwner() == owner)
				return pet;
		}
		return null;
	}

	public static PetCosmetic getPet(EntityType type) {
		for (CosmeticItem pet : CosmeticManager.getItems(CosmeticType.PETS)) {
			if (((PetCosmetic) pet).getEntityType() == type)
				return (PetCosmetic) pet;
		}
		return null;
	}
}
