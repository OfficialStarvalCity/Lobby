package de.heliosdevelopment.helioslobby;

import de.heliosdevelopment.helioslobby.commands.*;
import de.heliosdevelopment.helioslobby.commands.points.HeliosPointsCommand;
import de.heliosdevelopment.helioslobby.crates.CrateManager;
import de.heliosdevelopment.helioslobby.crates.CratesListener;
import de.heliosdevelopment.helioslobby.friends.FriendListener;
import de.heliosdevelopment.helioslobby.friends.FriendManager;
import de.heliosdevelopment.helioslobby.friends.MessageReceiver;
import de.heliosdevelopment.helioslobby.item.ItemManager;
import de.heliosdevelopment.helioslobby.listener.*;
import de.heliosdevelopment.helioslobby.manager.DailyReward;
import de.heliosdevelopment.helioslobby.manager.SettingManager;
import de.heliosdevelopment.helioslobby.player.points.PointsPlayerManager;
import de.heliosdevelopment.helioslobby.utils.PointsManager;
import de.heliosdevelopment.helioslobby.utils.Setting;
import de.heliosdevelopment.helioslobby.utils.SettingCategory;
import de.heliosdevelopment.helioslobby.utils.SettingType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.heliosdevelopment.helioslobby.extras.CosmeticListener;
import de.heliosdevelopment.helioslobby.extras.CosmeticManager;
import de.heliosdevelopment.helioslobby.manager.ChatManager;
import de.heliosdevelopment.helioslobby.navigator.NavigatorManager;
import de.heliosdevelopment.helioslobby.player.PlayerManager;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public class Lobby extends JavaPlugin {

    private static Lobby instance;
    private boolean silentLobby = false;
    private MySQL mysql;
    private ChatManager chatManager;
    private CrateManager crateManager;
    private FriendManager friendManager;
    private ItemManager itemManager;

    private PointsMySQL pointsmysql;
    private DatabaseType databaseType;

	/*
     * lobby.clearchat lobby.set lobby.remove lobby.gamemode lobby.vanish
	 * 
	 * lobby.unlockall
	 * 
	 * lobby.antilaby.bypass lobby.firework
	 * 
	 * lobby.interact
	 * 
	 * lobby.build
	 * 
	 * lobby.advanced
	 * 
	 */

    @Override
    public void onEnable() {
        instance = this;
        chatManager = new ChatManager();

        if (!getConfig().contains("isSilentLobby"))
            getConfig().set("isSilentLobby", false);
        if (!getConfig().contains("silentLobbyServer"))
            getConfig().set("silentLobbyServer", "SilentLobby-1");
        if (!getConfig().contains("defaultLobbyServer"))
            getConfig().set("defaultLobbyServer", "Lobby-1");
        if (!getConfig().contains("host"))
            getConfig().set("host", "localhost");
        if (!getConfig().contains("port"))
            getConfig().set("port", "3306");
        if (!getConfig().contains("database"))
            getConfig().set("database", "database");
        if (!getConfig().contains("pointsdatabase"))
            getConfig().set("pointsdatabase", "pointsdatabase");
        if (!getConfig().contains("username"))
            getConfig().set("username", "username");
        if (!getConfig().contains("password"))
            getConfig().set("password", "password");

        check("settings.general.scoreboard", true);
        check("settings.general.vipfirework", true);
        check("settings.general.teleporttolastlocation", true);
        check("settings.item.extrasitem", true);
        check("settings.item.nickitem", true);
        check("settings.item.silentlobbyitem", true);
        check("settings.item.hideplayeritem", true);
        check("settings.item.friendsitem", true);

        check("scoreboard.title", "  &6Helios Development  ");
        check("scoreboard.layout", Arrays.asList("&9", " &7Rang", "    &8» %rank%", "&1", " §7Website", "    &8» §eheliosdevelopment.de", "&2",
                " &7Onlinezeit", "    &8» &a%onlinetime% h", "&3"));
        saveConfig();

        SettingManager.addSetting(new Setting("scoreboard", SettingCategory.GENERAL, SettingType.GLOBAL));
        SettingManager.addSetting(new Setting("vipfirework", SettingCategory.GENERAL, SettingType.GLOBAL));
        SettingManager.addSetting(new Setting("teleporttolastlocation", SettingCategory.GENERAL, SettingType.GLOBAL));
        SettingManager.addSetting(new Setting("nickitem", SettingCategory.ITEM, SettingType.GLOBAL));
        SettingManager.addSetting(new Setting("silentlobbyitem", SettingCategory.ITEM, SettingType.GLOBAL));
        SettingManager.addSetting(new Setting("hideplayeritem", SettingCategory.ITEM, SettingType.GLOBAL));
        SettingManager.addSetting(new Setting("friendsitem", SettingCategory.ITEM, SettingType.GLOBAL));
        SettingManager.addSetting(new Setting("extrasitem", SettingCategory.ITEM, SettingType.GLOBAL));

        mysql = new MySQL(getConfig().getString("host"), Integer.valueOf(getConfig().getString("port")),
                getConfig().getString("database"), getConfig().getString("username"),
                getConfig().getString("password"));

        pointsmysql = new PointsMySQL(getConfig().getString("host"), Integer.valueOf(getConfig().getString("port")),
                getConfig().getString("pointsdatabase"), getConfig().getString("username"),
                getConfig().getString("password"));

        silentLobby = getConfig().getBoolean("isSilentLobby");
        DailyReward dailyReward = new DailyReward();
        CosmeticManager.init();
        NavigatorManager.init();
        crateManager = new CrateManager();
        friendManager = new FriendManager();
        itemManager = new ItemManager(friendManager, getConfig().getString("defaultLobbyServer"), getConfig().getString("silentLobbyServer"));
        PointsManager.load();


        getCommand("set").setExecutor(new SetCommand());
        getCommand("remove").setExecutor(new RemoveCommand());
        getCommand("clearchat").setExecutor(new ClearChatCommand());
        getCommand("clear").setExecutor(new ClearCommand());
        getCommand("gm").setExecutor(new GmCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("spawnwitch").setExecutor(new DailyRewardCommand(dailyReward));
        getCommand("despawnwitch").setExecutor(new DailyRewardCommand(dailyReward));
        getCommand("keys").setExecutor(new KeyCommand());

        getCommand("points").setExecutor(new HeliosPointsCommand());


        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "Lobby");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "HeliosBungee", new MessageReceiver(friendManager));

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new FriendListener(friendManager), this);
        Bukkit.getPluginManager().registerEvents(new EntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new CosmeticListener(), this);
        Bukkit.getPluginManager().registerEvents(new RewardListener(dailyReward), this);
        Bukkit.getPluginManager().registerEvents(new CratesListener(crateManager), this);

        for (Constructor<?> packetPlayOutWorldParticles : CosmeticManager.getNMSClass("PacketPlayOutWorldParticles").getConstructors()) {
            System.out.println( packetPlayOutWorldParticles.toGenericString());

        }


        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerManager.loadPlayer(player);
            PointsPlayerManager.loadPlayer(player);
            CosmeticManager.equipItems(player);
            friendManager.addPlayer(player);
        }
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            CosmeticManager.updateCosmetics(player);
            PlayerManager.unloadPlayer(player);
            PointsPlayerManager.unloadPlayer(player);
            friendManager.removePlayer(player);
        }
        try {
            crateManager.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mysql.close();
    }

    private void check(String path, Object value) {
        if (!getConfig().contains(path)) {
            getConfig().set(path, value);
        }
    }

    public static Lobby getInstance() {
        return instance;
    }

    public MySQL getMysql() {
        return mysql;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public boolean isSilentLobby() {
        return silentLobby;
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public PointsMySQL getPointsMysql() {
        return pointsmysql;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }
}
