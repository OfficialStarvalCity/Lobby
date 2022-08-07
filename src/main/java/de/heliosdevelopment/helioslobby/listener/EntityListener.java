package de.heliosdevelopment.helioslobby.listener;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

import de.heliosdevelopment.helioslobby.extras.pet.PetManager;
import de.heliosdevelopment.helioslobby.navigator.NavigatorManager;

public class EntityListener implements Listener {

	@EventHandler
	public void onPlayerShearEntity(PlayerShearEntityEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onSheepDyeWool(SheepDyeWoolEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onSheepRegrowWool(SheepRegrowWoolEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onEntityBlockForm(EntityBlockFormEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onEntityDamageByBlock(EntityDamageByBlockEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof Player & !(e.getDamager() instanceof Player))) {
			e.setCancelled(true);
		}
		if(e.getDamager() instanceof Player){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity().getType() == EntityType.PLAYER) {
			Player p = (Player) e.getEntity();
			if (p.getFireTicks() > 0) {
				p.setFireTicks(0);
			}
			if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
				p.teleport(NavigatorManager.getItem("spawn").getLocation());
			}
			e.setCancelled(true);
		} else if (PetManager.isPet(e.getEntity().getEntityId()))
			e.setCancelled(true);
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (!(e.getEntity().getType() == EntityType.PLAYER)) {
			e.getDrops().clear();
			e.setDroppedExp(0);
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onEntityPortal(EntityPortalEvent e) {
		Player p = (Player) e.getEntity();

		p.teleport(NavigatorManager.getItem("spawn").getLocation());
		e.setCancelled(true);
	}


	/*@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent e) {
		if (PetManager.getPet(e.getEntity().getEntityId()) == null)
			e.setCancelled(true);
	}

	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent e) {
		if(e.getEntity() instanceof ArmorStand)
			return;
		if (PetManager.getPet(e.getEntity().getEntityId()) == null)
			e.setCancelled(true);
	}*/
	@EventHandler
	public void onArmorStandInteract(PlayerArmorStandManipulateEvent event){
		event.setCancelled(true);
	}


}
