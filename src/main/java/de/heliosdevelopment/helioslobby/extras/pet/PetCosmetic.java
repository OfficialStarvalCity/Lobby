package de.heliosdevelopment.helioslobby.extras.pet;

import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.heliosdevelopment.helioslobby.extras.CosmeticItem;
import de.heliosdevelopment.helioslobby.extras.CosmeticType;

public class PetCosmetic extends CosmeticItem {

	private final EntityType type;

	public PetCosmetic(String name, CosmeticType type, Material mat, int requeredLevel, int data,
			EntityType entityType) {
		super(name, type, mat, requeredLevel, data);
		this.type = entityType;
	}

	public EntityType getEntityType() {
		return type;
	}

	@Override
	public void equipItem(Player player) {

		/*
		 * SAVE: OWNER, TYPE(Enum), NAME(String), AGE(Adult=0/Baby=1)
		 * 
		 * 
		 */
		if (PetManager.getPet(player) != null)
			return;
		Animals e = (Animals) player.getWorld().spawnEntity(player.getLocation(), type);
		e.setBaby();
		e.setAgeLock(true);
		e.setCustomName("§e" + player.getName() + "'s Haustier");
		e.setCustomNameVisible(true);
		e.setRemoveWhenFarAway(false);
		PetManager.addPet(player, new Pet(player, e, "§e" + player.getName() + "'s Haustier"));
	}
}
