package de.heliosdevelopment.helioslobby.friends;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.heliosdevelopment.helioslobby.Lobby;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FriendListener implements Listener {

    private final FriendManager friendManager;

    public FriendListener(FriendManager friendManager) {
        this.friendManager = friendManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        for(String s : friendManager.addPlayer(event.getPlayer()).getFriends())
            System.out.println(s);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        friendManager.removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if(event.getCurrentItem() == null) return;

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack currentItem = event.getCurrentItem();

        if(event.getView().getTitle().equalsIgnoreCase("§eFreunde §8» §7Menü")) {

            if(currentItem.getType() == Material.PLAYER_HEAD)
                friendManager.openPlayerInventory(player, currentItem.getItemMeta().getDisplayName());

        } else if(event.getView().getTitle().equalsIgnoreCase("§eFreunde §8» §7Infos")) {
            event.setCancelled(true);

            if(currentItem.getType() == Material.LAVA_BUCKET) {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("remove");
                out.writeUTF(ChatColor.stripColor(event.getClickedInventory().getItem(13).getItemMeta().getDisplayName()));
                player.sendPluginMessage(Lobby.getInstance(), "Lobby", out.toByteArray());
                player.closeInventory();
            }
            else if(currentItem.getType() == Material.OAK_DOOR)
                friendManager.openFriendInventory(player);

        }

    }

}
