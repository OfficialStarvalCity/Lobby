package de.heliosdevelopment.helioslobby.crates;

import de.heliosdevelopment.helioslobby.player.LobbyPlayer;
import de.heliosdevelopment.helioslobby.player.PlayerManager;
import de.heliosdevelopment.helioslobby.utils.ActionBar;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Teeage on 29.07.2017.
 */
public class CratesListener implements Listener {
    private final CrateManager crateManager;

    public CratesListener(CrateManager crateManager) {
        this.crateManager = crateManager;
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.ENDER_CHEST) {
                CrateChest chest = crateManager.getChest(event.getClickedBlock().getLocation());
                if (chest != null) {
                    event.setCancelled(true);
                    if (!chest.isUsed()) {
                        LobbyPlayer lobbyPlayer = PlayerManager.getPlayer(event.getPlayer());
                        if (lobbyPlayer == null) return;
                        if (lobbyPlayer.getKeys() >= 1) {
                            lobbyPlayer.setKeys(lobbyPlayer.getKeys() - 1);
                            ActionBar.sendActionBar(event.getPlayer(), "§eDeine Kiste wird geöffnet...");
                            crateManager.openCrate(event.getPlayer(), chest);
                        } else {
                            ActionBar.sendActionBar(event.getPlayer(), "§cDu besitzt keine Schlüssel.");
                        }
                    } else {
                        ActionBar.sendActionBar(event.getPlayer(), "§cDiese Kiste wird bereits geöffnet!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.ENDER_CHEST) {
            crateManager.getChests().add(new CrateChest(event.getBlock().getLocation()));
        }
    }
}
