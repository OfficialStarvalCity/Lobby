package de.heliosdevelopment.helioslobby.extras;

import de.heliosdevelopment.helioslobby.manager.SoundManager;
import de.heliosdevelopment.helioslobby.player.LobbyPlayer;
import de.heliosdevelopment.helioslobby.player.PlayerManager;
import de.heliosdevelopment.helioslobby.utils.PointsManager;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.material.Dye;
import org.bukkit.scheduler.BukkitRunnable;

import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.extras.paticle.ParticleManager;
import de.heliosdevelopment.helioslobby.extras.pet.Pet;
import de.heliosdevelopment.helioslobby.extras.pet.PetManager;
import de.heliosdevelopment.helioslobby.utils.Item;

public class CosmeticListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase("§7Cosmetics")) {
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR
                    || event.getCurrentItem().getItemMeta().getDisplayName() == null)
                return;
            event.setCancelled(true);
            LobbyPlayer lobbyPlayer = PlayerManager.getPlayer(player);
            if (lobbyPlayer == null) return;
            CosmeticItem cosmetic = CosmeticManager.getCosmeticItem(event.getCurrentItem().getType(),
                    ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
            if (cosmetic != null) {
                if (lobbyPlayer.getCosmetics().contains(cosmetic.getId()) || player.hasPermission("lobby.unlockall"))
                    cosmetic.equipItem(player);
                else {
                    CosmeticManager.getInBuying().put(player, cosmetic.getId());
                    CosmeticManager.openBuyingInventory(player, cosmetic.getId());
                }

            } else {
                switch (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())) {
                    case "Noch im Labor":
                        return;
                    case " ":
                        return;
                    case "Nächste Seite":
                        return;
                    case "Vorherige Seite":
                        return;
                    case "Zurück":
                        if (event.getClickedInventory().getSize() == 45)
                            CosmeticManager.openMainInventory(player);
                        return;
                    case "Extras zurücksetzen":
                        ParticleManager.removePlayer(player);
                        player.getInventory().setArmorContents(null);
                        PetManager.despawnPet(player);
                        return;
                    case "Hut entfernen":
                        player.getInventory().setHelmet(null);
                        player.updateInventory();
                        return;
                    case "Partikeleffekt entfernen":
                        ParticleManager.removePlayer(player);
                        return;
                    case "Haustier ins Tierheim geben":
                        PetManager.despawnPet(player);
                        return;
                    default:
                        CosmeticType type = CosmeticType
                                .getCosmeticTypebyName(event.getCurrentItem().getItemMeta().getDisplayName());
                        if (type != null)
                            CosmeticManager.openInventory(player, type, 1);
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase("§ePet")) {
            event.setCancelled(true);
            Pet pet = PetManager.getPet(player);
            switch (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())) {
                case "Name ändern":
                    player.closeInventory();
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            new net.wesjd.anvilgui.AnvilGUI.Builder()
                                    .onComplete((player, text) -> {                                    //called when the inventory output slot is clicked
                                        if(text != "jeb_" || text != "dinnerbone") {
                                            assert pet != null;
                                            pet.setName(ChatColor.translateAlternateColorCodes('&', text));
                                            return net.wesjd.anvilgui.AnvilGUI.Response.close();
                                        } else {
                                            player.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix") + "§cDieser Name ist nicht erlaubt!");
                                            return net.wesjd.anvilgui.AnvilGUI.Response.text("Incorrect.");
                                        }
                                    })
                                    .open(player);


                            Item i = new Item(Material.NAME_TAG);
                            assert pet != null;
                            i.setName(pet.getName());
                        }

                    }.runTaskLater(Lobby.getInstance(), 3);
                    return;
                case "Füttern (Premium)":
                    if (player.hasPermission("lobby.premium")) {
                        assert pet != null;
                        pet.changeAge();
                    } else
                        player.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix") + "§cDu benötigst hierfür den Premium Rang!");
                    break;
                case "Diät machen (Premium)":
                    if (player.hasPermission("lobby.premium")) {
                        assert pet != null;
                        pet.changeAge();
                    } else
                        player.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix") + "§cDu benötigst hierfür den Premium Rang!");
                    break;
                case "Reiten (Premium)":
                    if (player.hasPermission("lobby.premium")) {
                        assert pet != null;
                        pet.rideAnimal(player);
                    } else
                        player.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix") + "§cDu benötigst hierfür den Premium Rang!");
                    break;
                case "Färben (Premium)":
                    if (player.hasPermission("lobby.premium")) {
                        CosmeticManager.openColorInventory(player);
                        return;
                    } else
                        player.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix") + "§cDu benötigst hierfür den Premium Rang!");
                    break;
                case "Haustier freilassen":
                    PetManager.despawnPet(player);
                    player.closeInventory();
                    return;
                default:
                    return;
            }
            openInventory(player, pet);
        } else if (event.getView().getTitle().equalsIgnoreCase("§eFärben")) {
            event.setCancelled(true);
            Pet pet = PetManager.getPet(player);
            if (pet == null) return;
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Zurück")) {
                openInventory(player, pet);
                return;
            }
            DyeColor color = DyeColor
                    .valueOf(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
            if (pet.getAnimal().getType() == EntityType.SHEEP) {
                Sheep sheep = (Sheep) pet.getAnimal();
                sheep.setColor(color);
                player.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix") + "§7Dein Haustier hat nun die Farbe §e" + color.name());
            }
        } else if (event.getView().getTitle().equalsIgnoreCase("§eExtra kaufen")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§aKaufen")) {
                Integer id = CosmeticManager.getInBuying().get(player);
                if (id == null) return;
                LobbyPlayer lobbyPlayer = PlayerManager.getPlayer(player);
                if (lobbyPlayer == null) return;

                CosmeticItem item = CosmeticManager.getCosmeticItem(id);
                if (item == null)
                    return;
                if (PointsManager.removePoints(player, item.getPrice())) {
                    if (!lobbyPlayer.getCosmetics().contains(id))
                        lobbyPlayer.getCosmetics().add(id);
                    player.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix") + "§aDu hast dir erfolgreich ein neues Extra gekauft.");
                    player.playSound(player.getLocation(), SoundManager.getSound(SoundManager.Sound.LEVEL_UP), 1,1);
                } else {
                    player.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix") + "§cDu hast nicht genug Punkte!");
                    player.playSound(player.getLocation(), SoundManager.getSound(SoundManager.Sound.ITEM_BREAK), 1,1);
                }
                CosmeticManager.getInBuying().remove(player);
                player.closeInventory();
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§cAbbrechen")) {
                CosmeticManager.getInBuying().remove(player);
                CosmeticManager.openMainInventory(player);
                player.playSound(player.getLocation(), SoundManager.getSound(SoundManager.Sound.WOOD_CLICK), 1,1);
            }
            event.setCancelled(true);

        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        ParticleManager.removePlayer(p);
        PetManager.despawnPet(p);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getFrom().getBlockX() != event.getTo().getBlockX()
                || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
            final Pet pet = PetManager.getPets().get(event.getPlayer());
            if (pet != null) {
                pet.followPlayer();
            }
            final Player player = event.getPlayer();
            if (ParticleManager.getParticle().containsKey(player))
                ParticleManager.playEffect(player);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() != null) {
            Pet pet = PetManager.getPet(event.getRightClicked().getEntityId());
            if (pet != null)
                if (pet.getOwner() == event.getPlayer())
                    openInventory(event.getPlayer(), pet);
        }
    }

    private void openInventory(Player p, Pet pet) {
        Inventory inv = Bukkit.createInventory(null, 27, "§ePet");
        Item pane = new Item(Material.BLACK_STAINED_GLASS_PANE, 1, 0);
        pane.setName(" ");
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, pane.getItem());
        }

        Item changeName = new Item(Material.NAME_TAG);
        changeName.setName("§eName ändern");

        Item changeAge = new Item(Material.GLOWSTONE_DUST);
        if (pet.isAdult())
            changeAge.setName("§eDiät machen §7(§6Premium§7)");
        else
            changeAge.setName("§eFüttern §7(§6Premium§7)");

        Dye dye = new Dye();
        dye.setColor(DyeColor.CYAN);
        Item color = new Item(dye);
        color.setName("§eFärben §7(§6Premium§7)");

        Item ridePet = new Item(Material.SADDLE);
        ridePet.setName("§eReiten §7(§6Premium§7)");

        Item removePet = new Item(Material.BARRIER);
        removePet.setName("§eHaustier freilassen");

        if (pet.getAnimal().getType() == EntityType.SHEEP) {
            inv.setItem(9, changeName.getItem());
            inv.setItem(11, changeAge.getItem());
            inv.setItem(13, color.getItem());
            inv.setItem(15, ridePet.getItem());
            inv.setItem(17, removePet.getItem());
        } else {
            inv.setItem(10, changeName.getItem());
            inv.setItem(12, changeAge.getItem());
            inv.setItem(14, ridePet.getItem());
            inv.setItem(16, removePet.getItem());
        }

        p.openInventory(inv);
    }

}
