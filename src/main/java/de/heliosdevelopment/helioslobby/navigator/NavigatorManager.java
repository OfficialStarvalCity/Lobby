package de.heliosdevelopment.helioslobby.navigator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.MySQL;
import de.heliosdevelopment.helioslobby.utils.ItemCreator;
import org.yaml.snakeyaml.Yaml;

public class NavigatorManager {

    private final static Inventory inventory = Bukkit.createInventory(null, 27, "§eNavigator");
    private final static List<NavigatorItem> items = new ArrayList<>();
    private final static MySQL mysql = Lobby.getInstance().getMysql();
    private final static File file = new File(Lobby.getInstance().getDataFolder() + "//navigator.yml");
    private final static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public static void openNavigator(Player player) {
        player.openInventory(inventory);
    }

    public static void init() {
        if (!file.exists()) {
            cfg.set("spawn.name", "Spawn");
            cfg.set("spawn.slot", 22);
            cfg.set("spawn.material", "BEACON");
        }
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String string : cfg.getKeys(false)) {
            items.add(new NavigatorItem(cfg.getString(string + ".name"), cfg.getInt(string + ".slot"), mysql.getSpawn(string), Material.valueOf(cfg.getString(string + ".material"))));
        }

        for (Integer i = 0; i < inventory.getSize(); i++) {
            new ItemCreator(Material.STAINED_GLASS_PANE, 1).durability(7).hideFlags().setName("§1").setSlot(inventory,
                    i);
        }
        for (NavigatorItem item : items) {
            new ItemCreator(item.getMaterial(), 1).setName("§a" + item.getName()).hideFlags().setSlot(inventory,
                    item.getSlot());
        }
    }

    public static List<NavigatorItem> getItems() {
        return items;
    }

    public static NavigatorItem getItem(String name) {
        for (NavigatorItem item : items)
            if (item.getName().toLowerCase().equals(name.toLowerCase()))
                return item;
        return null;
    }

}
