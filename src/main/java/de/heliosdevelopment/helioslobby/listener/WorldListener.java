package de.heliosdevelopment.helioslobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.WorldSaveEvent;

public class WorldListener implements Listener {

	@EventHandler
	public void onBedEnter(PlayerBedEnterEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onExpBottle(ExpBottleEvent e) {
		e.setExperience(0);
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onFurnaceSmelt(FurnaceSmeltEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onFurnaceBurn(FurnaceBurnEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onWetherChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onThunderChange(ThunderChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onWorldSave(WorldSaveEvent e) {
		e.getWorld().setAutoSave(true);
	}
}
