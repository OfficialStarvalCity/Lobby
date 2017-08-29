package de.heliosdevelopment.helioslobby.friends;

import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.MySQL;
import de.heliosdevelopment.helioslobby.utils.Item;
import de.heliosdevelopment.helioslobby.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Teeage on 30.07.2017.
 */
public class FriendManager {

    //private final Inventory inventory = Bukkit.createInventory(null, 45, "§eFreunde");
    private final static MySQL mysql = Lobby.getInstance().getMysql();

    //public FriendManager(MySQL mysql) {
    //  this.mysql = mysql;
    //}

    private final List<FriendPlayer> players = new ArrayList<>();
    private int[] fields = new int[]{1, 2, 3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25};

    public FriendPlayer addPlayer(Player player) {
        FriendPlayer friendPlayer = mysql.getFriendPlayer(player);
        if (friendPlayer == null)
            friendPlayer = new FriendPlayer(player, new ArrayList<>());
        players.add(friendPlayer);
        return friendPlayer;
    }

    public void removePlayer(Player player) {
        FriendPlayer friendPlayer = getPlayer(player);
        if (friendPlayer != null)
            players.remove(friendPlayer);
    }

    public List<FriendPlayer> getPlayers() {
        return players;
    }

    public FriendPlayer getPlayer(Player player) {
        for (FriendPlayer friendPlayer : players)
            if (friendPlayer.getPlayer().getUniqueId().equals(player.getUniqueId()))
                return friendPlayer;
        return null;
    }

    public void openFriendInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 45, "§eFreunde §8» §7Menü");
        FriendPlayer friendPlayer = getPlayer(player);
        if (friendPlayer == null) return;
        for (Integer i = 0; i < inventory.getSize(); i++) new ItemCreator(Material.STAINED_GLASS_PANE, 1).durability(7).hideFlags().setName("§1").setSlot(inventory, i);

        for (int i = 0; i < friendPlayer.getFriends().size(); i++) {
            Item item = new Item(Material.SKULL_ITEM, 1, 3);
            String name = friendPlayer.getFriends().get(i);
            item.setSkullOwner(name);
            item.setName("§a" + name);
            inventory.setItem(fields[i], item.getItem());
        }

        player.openInventory(inventory);
    }

    public void openPlayerInventory(Player player, String name) {
        Inventory inventory = Bukkit.createInventory(null, 27, "§eFreunde §8» §7Infos");
        FriendPlayer friendPlayer = getPlayer(player);
        if(friendPlayer == null) return;
        for(int i = 0; i < inventory.getSize(); i++) new ItemCreator(Material.STAINED_GLASS_PANE, 1).durability(7).hideFlags().setName("§1").setSlot(inventory, i);


        new ItemCreator(Material.LAVA_BUCKET, 1).setName("§cFreundschaft beenden").setSlot(inventory, 10);
        Item item = new Item(Material.SKULL_ITEM, 1, 3);
        item.setSkullOwner(name);
        item.setName(name);
        inventory.setItem(13, item.getItem());

        new ItemCreator(Material.WOOD_DOOR, 1).setName("§cZurück").setSlot(inventory, 16);

        player.openInventory(inventory);
    }

    public void updatePlayer(Player player){
        FriendPlayer friendPlayer = getPlayer(player);
        if (friendPlayer == null)
            return;
        players.remove(friendPlayer);


    }


}
