package de.heliosdevelopment.helioslobby.utils;

import de.heliosdevelopment.helioslobby.HeliosPointsAPI;
import de.heliosdevelopment.helioslobby.Lobby;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PointsManager {

    private static HeliosPointsAPI heliosPoints;
    private static Economy vault;

    private static boolean pointsSystemEnabled = false;

    public static void load() {
        if (checkVault()) {
            System.out.println("[HeliosLobby] Hooked into Vault");
            pointsSystemEnabled = true;
        } else if (checkGamePoints()) {
            System.out.println("[HeliosLobby] Hooked into HeliosPointsAPI");
            pointsSystemEnabled = true;
        }
    }

    public static void addPoints(Player player, int points) {
        if (!pointsSystemEnabled || points == 0)
            return;
        if (heliosPoints != null)
            HeliosPointsAPI.addPoints(player.getUniqueId(), points);
        else if (vault != null)
            vault.depositPlayer(player, points);
    }

    public static boolean removePoints(Player player, int points) {
        if (!pointsSystemEnabled || points == 0)
            return false;
        if (heliosPoints != null)
            HeliosPointsAPI.removePoints(player.getUniqueId(), points);
        else if (vault != null)
            vault.withdrawPlayer(player, points);
        return true;
    }

    public static int getPoints(Player player) {
        if (heliosPoints != null)
            HeliosPointsAPI.getPoints(player.getUniqueId());
        else if (vault != null)
            return (int) vault.getBalance(player);
        return 0;

    }

    private static boolean checkGamePoints() {
        if (Bukkit.getPluginManager().isPluginEnabled("HeliosPointsAPI")) {
            if (heliosPoints != null)
                heliosPoints = (HeliosPointsAPI) Bukkit.getPluginManager().getPlugin("HeliosPointsAPI");
            return true;
        }
        return false;
    }

    private static boolean checkVault() {
        if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            RegisteredServiceProvider<Economy> economyProvider = Lobby.getInstance().getServer().getServicesManager()
                    .getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economyProvider != null) {
                vault = economyProvider.getProvider();
            }
        }
        return (vault != null);
    }

}
