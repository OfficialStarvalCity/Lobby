package de.heliosdevelopment.helioslobby.extras.pet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.entity.Animals;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Pet {

	private final Player owner;
	private final Animals animal;
	private final double speed;
	private String name;

	public Pet(Player player, Animals animal, String name) {
		this.owner = player;
		this.animal = animal;
		this.speed = 1.5;
		this.name = name;
	}

	public Pet(Player player, EntityType type, String name, int age) {
		owner = player;
		speed = 1.5;
		this.name = name;
		Animals e = (Animals) player.getWorld().spawnEntity(player.getLocation(), type);
		if (age == 0)
			e.setBaby();
		else
			e.setAdult();
		e.setAgeLock(true);
		e.setCustomName(name);
		e.setCustomNameVisible(true);
		e.setRemoveWhenFarAway(false);
		this.animal = e;
	}

	public void followPlayer() {
		final Location loc = owner.getLocation();
		if (loc.distanceSquared(animal.getLocation()) < 10)
			return;
		if (loc.distanceSquared(animal.getLocation()) > 35) {
			if (loc.getBlock().getType() != Material.AIR) {
				animal.teleport(loc);
			}
		} else {
			((CraftCreature) animal).getHandle().getNavigation().a(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(),
					speed);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		animal.setCustomName(name);
	}

	public boolean isAdult() {
		return animal.isAdult();
	}

	public void changeAge() {
		if (animal.isAdult())
			animal.setBaby();
		else
			animal.setAdult();
		animal.setAgeLock(true);
	}

	public void rideAnimal(Player p) {
		animal.setPassenger(p);
	}

	public void despawnPet() {
		animal.remove();
	}

	public double getSpeed() {
		return speed;
	}

	public Animals getAnimal() {
		return animal;
	}

	public Player getOwner() {
		return owner;
	}

}
