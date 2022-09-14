package de.heliosdevelopment.helioslobby.item;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.*;

import de.heliosdevelopment.helioslobby.friends.FriendManager;
import de.heliosdevelopment.helioslobby.manager.SettingManager;
import de.heliosdevelopment.helioslobby.player.LobbyPlayer;
import de.heliosdevelopment.helioslobby.player.PlayerManager;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;

import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.extras.CosmeticManager;
import de.heliosdevelopment.helioslobby.manager.HideManager;
import de.heliosdevelopment.helioslobby.navigator.NavigatorManager;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemManager {

    private final List<LobbyItem> items = new ArrayList<>();
    private final static List<Player> cooldown = new ArrayList<>();

    public ItemManager(FriendManager friendManager, String defaulLobby, String silentLobby) {
        items.add(new LobbyItem("§7× §eNavigator §7×", Material.LEGACY_RECORD_4, "", null, null) {
            @Override
            public void onInteract(Player player) {
                NavigatorManager.openNavigator(player);
            }
        });
        if (SettingManager.getSetting("extrasitem").isEnabled()) {
            items.add(new LobbyItem("§7× §eExtras §7×", Material.CHEST, "", null, null) {
                @Override
                public void onInteract(Player player) {
                    if (Lobby.getInstance().isSilentLobby()) {
                        player.sendMessage(Lobby.getInstance().getChatManager().getMessage("prefix")
                                + "§cExtras sind in der SilentLobby nicht verfügbar!");
                    } else {
                        CosmeticManager.openMainInventory(player);
                    }
                }
            });
        }
        if (SettingManager.getSetting("hideplayeritem").isEnabled()) {
            Dye dye = new Dye();
            dye.setColor(DyeColor.LIME);
            items.add(new LobbyItem("§7× §eSpieler Verstecken §7×", Material.BLAZE_ROD, "", null,
                    Arrays.asList("§7× §eSpieler anzeigen §7×", "§7× §eSpieler Verstecken §7×"), dye) {
                @Override
                public void onInteract(Player player) {
                    HideManager.setVisibility(player, player.getItemInHand());
                }
            });
        }
        if (SettingManager.getSetting("silentlobbyitem").isEnabled()) {
            items.add(new LobbyItem(
                    Lobby.getInstance().isSilentLobby() ? "§7× §eNormale Lobby §7×" : "§7× §eSilent Hub §7×",
                    Lobby.getInstance().isSilentLobby() ? Material.FEATHER : Material.TNT, "lobby.advanced", null, null) {
                @Override
                public void onInteract(Player player) {
                    String server;
                    if (Lobby.getInstance().isSilentLobby())
                        server = defaulLobby;
                    else
                        server = silentLobby;
                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(b);
                    try {
                        out.writeUTF("Connect");
                        out.writeUTF(server);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    player.sendPluginMessage(Lobby.getInstance(), "BungeeCord", b.toByteArray());
                }
            });
        }
        if (SettingManager.getSetting("friendsitem").isEnabled()) {
            items.add(new LobbyItem("§7× §eFreunde §7×", Material.PLAYER_HEAD, "", null, null) {
                @Override
                public void onInteract(Player player) {
                    friendManager.openFriendInventory(player);
                }
            });
        }
    }

    public void getItems(Player player) {

        final List<LobbyItem> list = new ArrayList<>();
        for (LobbyItem item : items) {
            if (!Objects.equals(item.getPermission(), "")) {
                if (player.hasPermission(item.getPermission())) {
                    list.add(item);
                }
            } else {
                if (item.getDye() != null) {
                    LobbyPlayer lobbyPlayer = PlayerManager.getPlayer(player);
                    if (lobbyPlayer != null) {
                        switch (lobbyPlayer.getVisibility()) {
                            case ALL:
                                item.getDye().setColor(DyeColor.LIME);
                                break;
                            case NONE:
                                item.getDye().setColor(DyeColor.GRAY);
                                item.setName("§7× §eSpieler anzeigen §7×");
                                break;
                            case PREMIUM:
                                item.getDye().setColor(DyeColor.PURPLE);
                                break;
                        }
                    }
                }
                list.add(item);
            }
        }

        int[] position = getPosition(list.size());
        for (int i = 0; i < list.size(); i++) {
            ItemStack item = list.get(i).getItem();
            if (item.getType() == Material.PLAYER_HEAD) {
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                meta.setOwner(player.getName());
                item.setItemMeta(meta);
            }
            player.getInventory().setItem(position != null ? position[i] : 0, item);
        }
    }

    public LobbyItem getItem(String name) {
        for (LobbyItem item : items) {
            if (item.getName().equalsIgnoreCase(name))
                return item;
            if (item.getAlias() != null && item.getAlias().contains(name))
                return item;
        }
        return null;
    }

    public void clearInventory(Player player) {
        player.getActivePotionEffects().clear();
        player.getInventory().clear();

        player.setLevel(0);
        player.setExp(0.0F);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setGameMode(GameMode.ADVENTURE);

        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setFireTicks(0);
    }

    private int[] getPosition(int items) {
        if ((items < 1) || (items > 9))
            throw new IllegalArgumentException("Items must be between 1-9 (" + items + ")");
        switch (items) {
            case 1:
                return new int[]{4};
            case 2:
                return new int[]{3, 5};
            case 3:
                return new int[]{1, 4, 7};
            case 4:
                return new int[]{1, 3, 5, 7};
            case 5:
                return new int[]{1, 2, 4, 6, 7};
            case 6:
                return new int[]{1, 2, 3, 5, 6, 7};
            case 7:
                return new int[]{1, 2, 3, 4, 5, 6, 7};
            case 8:
                return new int[]{0, 1, 2, 3, 5, 6, 7, 8};
            case 9:
                return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        }
        return null;
    }

    public List<LobbyItem> getItems() {
        return items;
    }

}
