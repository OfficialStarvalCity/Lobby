package de.heliosdevelopment.helioslobby.manager;

import de.heliosdevelopment.helioslobby.extras.CosmeticManager;
import de.heliosdevelopment.helioslobby.player.LobbyPlayer;
import de.heliosdevelopment.helioslobby.player.PlayerManager;
import de.heliosdevelopment.helioslobby.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

public class DailyReward {

    public void spawnEntity(Location location) {
        Entity entity = location.getWorld().spawnEntity(location, EntityType.WITCH);
        entity.setCustomName("§eDaily Reward");

        try {
            Object nmsentity = entity.getClass().getMethod("getHandle").invoke(entity);
            Object nbttag = CosmeticManager.getNMSClass("NBTTagCompound").newInstance();
            nbttag.getClass().getMethod("setInt", String.class, int.class).invoke(nbttag, "NoAI", 1);
            nmsentity.getClass().getMethod("e", nbttag.getClass()).invoke(nmsentity, nbttag);
            nmsentity.getClass().getMethod("c", nbttag.getClass()).invoke(nmsentity, nbttag);
            nmsentity.getClass().getMethod("f", nbttag.getClass()).invoke(nmsentity, nbttag);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void despawnEntity(Location location) {
        for (Entity entity : location.getWorld().getEntities()) {
            if (entity.getCustomName() != null && entity.getCustomName().equals("§eDaily Reward"))
                entity.remove();
        }
    }

    public void openInventory(Player player) {
        LobbyPlayer lobbyPlayer = PlayerManager.getPlayer(player);
        if (lobbyPlayer == null) return;
        Inventory inventoy = Bukkit.createInventory(null, 9, "§eTägliche Belohnung");
        Item normalReward = new Item(Material.LEGACY_DOUBLE_PLANT);
        normalReward.setName("§aNormale Belohnung");
        if (((lobbyPlayer.getLastDailyReward() + (1000 * 60 * 60 * 24)) - System.currentTimeMillis()) <= 0)
            normalReward.setLore("§eVerfügbar!");
        else
            normalReward.setLore("§7In " + getTime((lobbyPlayer.getLastDailyReward() + (1000 * 60 * 60 * 24)) - System.currentTimeMillis()) + " §7verfügbar.");
        inventoy.setItem(2, normalReward.getItem());

        Item premiumReward = new Item(Material.LEGACY_DOUBLE_PLANT);
        premiumReward.setName("§aPremium Belohnung");
        if (player.hasPermission("lobby.premium")) {
            if (((lobbyPlayer.getLastPremiumReward() + (1000 * 60 * 60 * 24)) - System.currentTimeMillis()) <= 0)
                premiumReward.setLore("§eVerfügbar!");
            else
                premiumReward.setLore("§7In " + getTime((lobbyPlayer.getLastPremiumReward() + (1000 * 60 * 60 * 24)) - System.currentTimeMillis()) + " §7verfügbar.");
        } else {
            premiumReward.setLore("§cDu benötigst den Premium Rang!");
        }
        inventoy.setItem(6, premiumReward.getItem());
        player.playSound(player.getLocation(), SoundManager.getSound(SoundManager.Sound.CHICKEN_EGG_POP), 1, 1);

        player.openInventory(inventoy);
    }

    private String getTime(long time) {
        int hours = 0;
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(time);
        while (minutes >= 60) {
            hours++;
            minutes -= 60;
        }
        if (hours == 0)
            return "§a" + minutes + " Minuten";
        else
            return "§a" + hours + " Stunden§7 und §a" + minutes + " Minuten";
    }


}
