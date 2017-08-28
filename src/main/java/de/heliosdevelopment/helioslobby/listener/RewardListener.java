package de.heliosdevelopment.helioslobby.listener;

import de.heliosdevelopment.helioslobby.manager.DailyReward;
import de.heliosdevelopment.helioslobby.player.LobbyPlayer;
import de.heliosdevelopment.helioslobby.player.PlayerManager;
import de.heliosdevelopment.helioslobby.utils.ActionBar;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class RewardListener implements Listener {
    private final DailyReward reward;

    public RewardListener(DailyReward dailyReward) {
        this.reward = dailyReward;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Villager) {
            if (event.getRightClicked().getCustomName() != null && event.getRightClicked().getCustomName().equals("§eDaily Reward")) {
                event.setCancelled(true);
                if(event.getPlayer().getItemInHand() != null && event.getPlayer().getItemInHand().getType().equals(Material.SHEARS))
                    event.getRightClicked().remove();
                else
                    reward.openInventory(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().hasPermission("lobby.setreward")) {
            String message = event.getMessage().toLowerCase();
            if (message.startsWith("/setdailyreward")) {
                reward.spawnEntity(event.getPlayer().getLocation());
                event.setCancelled(true);
            } else if (message.startsWith("/deletedailyreward")) {
                reward.despawnEntity(event.getPlayer().getLocation());
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Villager)
            event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory().getName() != null && event.getClickedInventory().getName().equals("§eTägliche Belohnung")) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§aNormale Belohnung")) {
                    LobbyPlayer lobbyPlayer = PlayerManager.getPlayer((Player) event.getWhoClicked());
                    if (lobbyPlayer != null) {
                        if (((lobbyPlayer.getLastDailyReward() + (1000 * 60 * 60 * 24)) - System.currentTimeMillis()) <= 0) {
                            //TODO Coins setzen
                            lobbyPlayer.setLastDailyReward(System.currentTimeMillis());
                            reward.openInventory((Player) event.getWhoClicked());
                        } else {
                            ActionBar.sendActionBar((Player) event.getWhoClicked(), "§cDu musst leider noch warten...");
                        }
                    }
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§aPremium Belohnung")) {
                    LobbyPlayer lobbyPlayer = PlayerManager.getPlayer((Player) event.getWhoClicked());
                    if (lobbyPlayer != null) {
                        if (((lobbyPlayer.getLastPremiumReward() + (1000 * 60 * 60 * 24)) - System.currentTimeMillis()) <= 0) {
                            //TODO Coins setzen
                            lobbyPlayer.setLastPremiumReward(System.currentTimeMillis());
                            reward.openInventory((Player) event.getWhoClicked());
                        } else {
                            ActionBar.sendActionBar((Player) event.getWhoClicked(), "§cDu musst leider noch warten...");
                        }
                    }
                }
            }
        }
    }
}
