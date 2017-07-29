package de.heliosdevelopment.helioslobby.crates;

import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by Teeage on 29.07.2017.
 */
public class CrateManager {

    private static final Set<Crate> crates = new HashSet<>();
    private static final Set<Player> inCrate = new HashSet<>();

    public static void openCratesInventory(Player player){
            int max = crates.size();
            Inventory inv = Bukkit.createInventory(null, 27, "§7Reward");
            Random r = new Random();
            Item glass = new Item(Material.STAINED_GLASS_PANE);
            inCrate.add(player);
            new BukkitRunnable() {
                int time = 0;

                @Override
                public void run() {
                    if (time >= 12) {
                        inCrate.remove(player);
                        cancel();
                    }
                    for (int i = 0; i < 27; i++) {
                        glass.setColor(r.nextInt(16));
                        inv.setItem(i, glass.getItem());
                    }
                    inv.setItem(13, ((Crate)crates.toArray()[0]).getItem());
                    time++;
                    player.openInventory(inv);
                }

            }.runTaskTimer(Lobby.getInstance(), 4, 4);


    }
}
