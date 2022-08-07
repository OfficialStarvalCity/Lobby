package de.heliosdevelopment.helioslobby.listener;

import de.heliosdevelopment.helioslobby.manager.SettingManager;
import de.heliosdevelopment.helioslobby.utils.SettingCategory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.extras.CosmeticManager;
import de.heliosdevelopment.helioslobby.item.ItemManager;
import de.heliosdevelopment.helioslobby.item.LobbyItem;
import de.heliosdevelopment.helioslobby.manager.ScoreboardManager;
import de.heliosdevelopment.helioslobby.navigator.NavigatorManager;
import de.heliosdevelopment.helioslobby.player.PlayerManager;

public class PlayerListener implements Listener {

    private final ItemManager itemManager = Lobby.getInstance().getItemManager();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        itemManager.clearInventory(player);
        itemManager.getItems(player);
        ScoreboardManager.updateScoreboard(player);
        PlayerManager.loadPlayer(player);
        if (!Lobby.getInstance().isSilentLobby()) {
            CosmeticManager.equipItems(player);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
        }
        if (SettingManager.getSetting("vipfirework").isEnabled()) {
            if (player.hasPermission("lobby.firework") && !Lobby.getInstance().isSilentLobby())
                Bukkit.getScheduler().runTaskLater(Lobby.getInstance(), () -> {
                    Firework fw = (Firework) Bukkit.getWorlds().get(0).spawnEntity(player.getLocation(),
                            EntityType.FIREWORK);
                    FireworkMeta fwm = fw.getFireworkMeta();
                    fwm.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(Type.BALL).with(Type.BALL_LARGE)
                            .with(Type.STAR).withColor(Color.ORANGE).withColor(Color.YELLOW).withFade(Color.PURPLE)
                            .withFade(Color.RED).build());
                    fwm.setPower(1);
                    fw.setFireworkMeta(fwm);
                }, 60);
        }
        Bukkit.getScheduler().runTaskLater(Lobby.getInstance(), () -> {
            if (SettingManager.getSetting("teleporttolastlocation").isEnabled()) {
                Location location = Lobby.getInstance().getMysql().getLocation(player.getUniqueId().toString());
                if (location == null) {
                    Location spawn = NavigatorManager.getItem("spawn").getLocation();
                    if (spawn != null)
                        player.teleport(spawn);
                } else {
                    player.teleport(location);
                }
            } else {
                Location spawn = NavigatorManager.getItem("spawn").getLocation();
                if (spawn != null)
                    player.teleport(spawn);
            }

        }, 10);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (Lobby.getInstance().isSilentLobby()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix")
                    + "§cDu kannst in der Silent Lobby nicht schreiben.");
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        CosmeticManager.updateCosmetics(e.getPlayer());
        PlayerManager.unloadPlayer(e.getPlayer());
        Lobby.getInstance().getMysql().addPlayerLocation(e.getPlayer().getUniqueId().toString(), e.getPlayer().getLocation());
        e.setQuitMessage(null);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        Player player = e.getEntity();

        e.setDeathMessage(null);
        e.setDroppedExp(0);
        e.getDrops().clear();

        player.setHealth(20.0D);
        player.setFoodLevel(0);
        player.setFireTicks(0);
        Location spawn = NavigatorManager.getItem("spawn").getLocation();
        if (spawn != null)
            player.teleport(spawn);
        itemManager.clearInventory(player);
        itemManager.getItems(player);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.PHYSICAL) && e.getClickedBlock().getType().equals(Material.LEGACY_SOIL))
            e.setCancelled(true);
        if (e.getClickedBlock() != null && e.getClickedBlock().getType() != null)
            if (!e.getPlayer().hasPermission("lobby.interact")) {
                if (e.getClickedBlock().getType() == Material.OAK_TRAPDOOR
                        || e.getClickedBlock().getType() == Material.CHEST
                        || e.getClickedBlock().getType() == Material.LEGACY_WORKBENCH
                        || e.getClickedBlock().getType() == Material.ENCHANTING_TABLE
                        || e.getClickedBlock().getType() == Material.LEVER)
                    e.setCancelled(true);
            }
        Player player = e.getPlayer();
        if ((player.getItemInHand() == null) || (player.getItemInHand().getItemMeta() == null)) {
            return;
        }
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            LobbyItem item = itemManager.getItem(e.getItem().getItemMeta().getDisplayName());
            if (item != null)
                item.onInteract(player);
        }
    }

}
