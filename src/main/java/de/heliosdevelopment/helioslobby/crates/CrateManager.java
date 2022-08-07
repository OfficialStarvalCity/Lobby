package de.heliosdevelopment.helioslobby.crates;

import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.extras.CosmeticItem;
import de.heliosdevelopment.helioslobby.extras.CosmeticManager;
import de.heliosdevelopment.helioslobby.manager.SoundManager;
import de.heliosdevelopment.helioslobby.player.LobbyPlayer;
import de.heliosdevelopment.helioslobby.player.PlayerManager;
import de.heliosdevelopment.helioslobby.utils.HoloAPI;
import de.heliosdevelopment.helioslobby.utils.PointsManager;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Teeage on 29.07.2017.
 */
public class CrateManager {

    private final Set<Crate> crates = new HashSet<>();
    private final Set<CrateChest> chests = new HashSet<>();
    private final Random random = new Random();
    private final String prefix = Lobby.getInstance().getChatManager().getMessage("prefix");

    public CrateManager() {
        for (CosmeticItem item : CosmeticManager.getCosmetics())
            crates.add(new Crate(item.getName(), CrateType.COSMETIC, item.getId(), item.getType().toString()));
        crates.add(new Crate("50 Points", CrateType.COINS, 50, "POINTS"));
        crates.add(new Crate("100 Points", CrateType.COINS, 100, "POINTS"));
        crates.add(new Crate("200 Points", CrateType.COINS, 200, "POINTS"));
        crates.add(new Crate("400 Points", CrateType.COINS, 400, "POINTS"));
        crates.add(new Crate("600 Points", CrateType.COINS, 600, "POINTS"));
        crates.add(new Crate("800 Points", CrateType.COINS, 800, "POINTS"));
        crates.add(new Crate("1000 Points", CrateType.COINS, 1000, "POINTS"));

        File file = new File(Lobby.getInstance().getDataFolder() + "//crates.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if (cfg.getStringList("crates") != null) {
            for (String s : cfg.getStringList("crates"))
                chests.add(new CrateChest(stringToLoc(s)));
        }
    }

    public void save() throws IOException {
        File file = new File(Lobby.getInstance().getDataFolder() + "//crates.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        List<String> strings = new ArrayList<>();
        for (CrateChest chest : chests)
            strings.add(locToString(chest.getLocation()));
        cfg.set("crates", strings);
        cfg.save(file);

    }

    public void openCrate(Player player, CrateChest crateChest) {
        LobbyPlayer lobbyPlayer = PlayerManager.getPlayer(player);
        if (lobbyPlayer == null) return;
        crateChest.setUsed(true);
        new BukkitRunnable() {
            int counter = 30;
            Crate currentCrate = null;
            HoloAPI currentHolo = null;

            @Override
            public void run() {
                if (counter == 0) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    if (currentCrate.getType() == CrateType.COINS) {
                        PointsManager.addPoints(player, currentCrate.getAmount());
                        player.sendMessage(prefix + "Du hast §e" + currentCrate.getAmount() + " §7Punkte erhalten.");
                    } else {
                        if (lobbyPlayer.getCosmetics().contains(currentCrate.getCosmeticId())) {
                            PointsManager.addPoints(player, 100);
                            player.sendMessage(prefix + "§aDa du das Item bereits besitzt hast du 100 Punkte erhalten.");
                        } else {
                            lobbyPlayer.getCosmetics().add(currentCrate.getCosmeticId());
                            player.sendMessage(prefix + "§eDu hast folgendes Item erhalten:");
                            player.sendMessage(prefix + "§e" + currentCrate.getDescription() + "§7 | " + currentCrate.getName());
                        }
                    }
                    Location location = crateChest.getLocation().clone().subtract(5, 0, 5);
                    for (int i = 0; i < 5; i++) {
                        Firework fw = (Firework) location.getWorld().spawnEntity(location.clone().add(random.nextInt(100) / 10,
                                0, random.nextInt(100) / 10),
                                EntityType.FIREWORK);
                        FireworkMeta fwm = fw.getFireworkMeta();
                        fwm.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(FireworkEffect.Type.BALL).with(FireworkEffect.Type.BALL_LARGE)
                                .with(FireworkEffect.Type.STAR).withColor(Color.ORANGE).withColor(Color.YELLOW).withFade(Color.PURPLE)
                                .withFade(Color.RED).build());
                        fwm.setPower(1);
                        fw.setFireworkMeta(fwm);
                    }
                    cancel();
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            crateChest.setUsed(false);
                            currentHolo.destroy(player);
                            cancel();
                        }
                    }.runTaskLater(Lobby.getInstance(), 60);

                } else {
                    if (currentHolo != null)
                        currentHolo.destroy(player);
                    currentCrate = (Crate) crates.toArray()[random.nextInt(crates.toArray().length)];
                    currentHolo = new HoloAPI(crateChest.getHoloLocation(), "§e" + currentCrate.getDescription() + "§7 | " + currentCrate.getName());
                    currentHolo.display(player);
                    player.playSound(currentHolo.getLocation(), SoundManager.getSound(SoundManager.Sound.CLICK), 1,1);
                }
                counter--;
            }
        }.runTaskTimerAsynchronously(Lobby.getInstance(), 5, 5);

    }

    public CrateChest getChest(Location location) {
        for (CrateChest chest : chests) {
            if (chest.getLocation().equals(location))
                return chest;
        }
        return null;
    }

    public Set<Crate> getCrates() {
        return crates;
    }

    public Set<CrateChest> getChests() {
        return chests;
    }

    private String locToString(Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ()
                + "," + location.getYaw() + "," + location.getPitch();
    }

    private Location stringToLoc(String s) {
        if (s == null)
            return null;
        String[] a = s.split(",");
        World w = Bukkit.getServer().getWorld(a[0]);
        if (w == null)
            return null;

        Double x = Double.parseDouble(a[1]);
        Double y = Double.parseDouble(a[2]);
        Double z = Double.parseDouble(a[3]);
        float yaw = Float.parseFloat(a[4]);
        float pitch = Float.parseFloat(a[5]);
        return new Location(w, x, y, z, yaw, pitch);
    }
    /*public static void openCratesInventory(Player player){
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
    private static void getItem(Player player) {
        ItemStack item = player.getInventory().getItem(14);
        if(item == null)
            return;
        Object reward;
        if(item.getType() == Material.NAME_TAG){

        }else{
            CosmeticItem cosmetic = CosmeticManager.getCosmeticItem(item.getType(), item.getItemMeta().getDisplayName());
        }
        new BukkitRunnable(){
            @Override
            public void run() {

                inCrate.remove(player);
                cancel();
            }
        }.runTaskLater(Lobby.getInstance(), 50);
    }*/
}
