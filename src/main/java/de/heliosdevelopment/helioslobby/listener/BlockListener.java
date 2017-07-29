package de.heliosdevelopment.helioslobby.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;

public class BlockListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (!e.getPlayer().hasPermission("lobby.build"))
			e.setCancelled(true);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (!e.getPlayer().hasPermission("lobby.build"))
			e.setCancelled(true);
		else if (e.getBlock().getType() == Material.TNT)
			e.setCancelled(true);
	}

	@EventHandler
	public void onBlockFade(BlockFadeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onBlockDamage(BlockDamageEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onBlockSpread(BlockSpreadEvent e) {
		if (e.getSource().getType() == Material.FIRE)
			e.setCancelled(true);
	}

	@EventHandler
	public void onBlockBurn(BlockBurnEvent e) {
		e.setCancelled(true);
	}

}
