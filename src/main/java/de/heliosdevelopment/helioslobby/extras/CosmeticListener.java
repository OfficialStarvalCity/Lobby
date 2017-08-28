package de.heliosdevelopment.helioslobby.extras;

import de.heliosdevelopment.helioslobby.player.LobbyPlayer;
import de.heliosdevelopment.helioslobby.player.PlayerManager;
import de.heliosdevelopment.helioslobby.utils.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
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
import de.heliosdevelopment.helioslobby.extras.util.AnvilGUI;
import de.heliosdevelopment.helioslobby.utils.Item;

public class CosmeticListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getInventory().getName().equalsIgnoreCase("§7Cosmetics")) {
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR
                    || event.getCurrentItem().getItemMeta().getDisplayName() == null)
                return;
            event.setCancelled(true);
            // CosmeticItem cosmetic = CosmeticManager.getCosmeticItem(
            // Integer.valueOf(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(2))));
            LobbyPlayer lobbyPlayer = PlayerManager.getPlayer(p);
            if (lobbyPlayer == null) return;
            CosmeticItem cosmetic = CosmeticManager.getCosmeticItem(event.getCurrentItem().getType(),
                    ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
            if (cosmetic != null) {
                if (lobbyPlayer.getCosmetics().contains(cosmetic.getId())
                        || p.hasPermission("lobby.unlockall"))
                    cosmetic.equipItem(p);
                else {
                    CosmeticManager.getInBuying().put(p, cosmetic.getId());
                    CosmeticManager.openBuyingInventory(p, cosmetic.getId());
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
                            CosmeticManager.openMainInventory(p);
                        // else
                        // Management.openManagementInventory(p);
                        return;
                    case "Extras zurücksetzen":
                        ParticleManager.removePlayer(p);
                        p.getInventory().setArmorContents(null);
                        PetManager.despawnPet(p);
                        return;
                    case "Hut entfernen":
                        p.getInventory().setHelmet(null);
                        p.updateInventory();
                        return;
                    case "Partikeleffekt entfernen":
                        ParticleManager.removePlayer(p);
                        return;
                    case "Haustier ins Tierheim geben":
                        PetManager.despawnPet(p);
                        return;
                    default:
                        CosmeticType type = CosmeticType
                                .getCosmeticTypebyName(event.getCurrentItem().getItemMeta().getDisplayName());
                        if (type != null)
                            CosmeticManager.openInventory(p, type, 1);
                }
            }
        } else if (event.getInventory().getName().equalsIgnoreCase("§ePet")) {
            event.setCancelled(true);
            Pet pet = PetManager.getPet(p);
            switch (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())) {
                case "Name ändern":
                    p.closeInventory();
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            final AnvilGUI gui = new AnvilGUI(p, event1 -> {
                                if (event1.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
                                    event1.setWillClose(true);
                                    event1.setWillDestroy(true);
                                    if (event1.getName().equalsIgnoreCase("jeb_")
                                            || event1.getName().equalsIgnoreCase("dinnerbone")) {
                                        event1.setWillClose(false);
                                        event1.setWillDestroy(false);
                                        p.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix") + "§cDieser Name ist nicht erlaubt!");
                                        return;
                                    }
                                    assert pet != null;
                                    pet.setName(ChatColor.translateAlternateColorCodes('&', event1.getName()));
                                } else {
                                    event1.setWillClose(false);
                                    event1.setWillDestroy(false);
                                }
                            });
                            Item i = new Item(Material.NAME_TAG);
                            assert pet != null;
                            i.setName(pet.getName());
                            gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, i.getItem());
                            gui.open();
                        }

                    }.runTaskLater(Lobby.getInstance(), 3);
                    return;
                case "Füttern (Premium)":
                    if (p.hasPermission("lobby.premium")) {
                        assert pet != null;
                        pet.changeAge();
                    } else
                        p.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix") + "§cDu benötigst hierfür den Premium Rang!");
                    break;
                case "Diät machen (Premium)":
                    if (p.hasPermission("lobby.premium")) {
                        assert pet != null;
                        pet.changeAge();
                    } else
                        p.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix") + "§cDu benötigst hierfür den Premium Rang!");
                    break;
                case "Reiten (Premium)":
                    if (p.hasPermission("lobby.premium")) {
                        assert pet != null;
                        pet.rideAnimal(p);
                    } else
                        p.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix") + "§cDu benötigst hierfür den Premium Rang!");
                    break;
                case "Färben (Premium)":
                    if (p.hasPermission("lobby.premium")) {
                        CosmeticManager.openColorInventory(p);
                        return;
                    } else
                        p.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix") + "§cDu benötigst hierfür den Premium Rang!");
                    break;
                case "Haustier freilassen":
                    PetManager.despawnPet(p);
                    p.closeInventory();
                    return;
                default:
                    return;
            }
            openInventory(p, pet);
        } else if (event.getInventory().getName().equalsIgnoreCase("§eFärben")) {
            event.setCancelled(true);
            Pet pet = PetManager.getPet(p);
            if (pet == null) return;
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Zurück")) {
                openInventory(p, pet);
                return;
            }
            DyeColor color = DyeColor
                    .valueOf(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
            if (pet.getAnimal().getType() == EntityType.SHEEP) {
                Sheep sheep = (Sheep) pet.getAnimal();
                sheep.setColor(color);
                p.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix") + "§7Dein Haustier hat nun die Farbe §e" + color.name());
            }
        } else if (event.getInventory().getName().equalsIgnoreCase("§eKaufen")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§aKaufen")) {
                //TODO POINTS
                Integer id = CosmeticManager.getInBuying().get(p);
                if (id == null) return;
                LobbyPlayer lobbyPlayer = PlayerManager.getPlayer(p);
                if (lobbyPlayer == null) return;
                if (!lobbyPlayer.getCosmetics().contains(id))
                    lobbyPlayer.getCosmetics().add(id);
                p.closeInventory();
                ActionBar.sendActionBar(p, Lobby.getInstance().getChatManager().getMessage("prefix") + "§aDu hast dir erfolgreich ein neues Extra gekauft.");
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§cAbbrechen")) {
                CosmeticManager.getInBuying().remove(p);
                CosmeticManager.openMainInventory(p);
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
        Item pane = new Item(Material.STAINED_GLASS_PANE, 1, 15);
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
