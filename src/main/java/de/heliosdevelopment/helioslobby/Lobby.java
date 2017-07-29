package de.heliosdevelopment.helioslobby;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.heliosdevelopment.helioslobby.commands.ClearChatCommand;
import de.heliosdevelopment.helioslobby.commands.ClearCommand;
import de.heliosdevelopment.helioslobby.commands.GmCommand;
import de.heliosdevelopment.helioslobby.commands.RemoveCommand;
import de.heliosdevelopment.helioslobby.commands.SetCommand;
import de.heliosdevelopment.helioslobby.commands.VanishCommand;
import de.heliosdevelopment.helioslobby.extras.CosmeticListener;
import de.heliosdevelopment.helioslobby.extras.CosmeticManager;
import de.heliosdevelopment.helioslobby.listener.BlockListener;
import de.heliosdevelopment.helioslobby.listener.EntityListener;
import de.heliosdevelopment.helioslobby.listener.InventoryListener;
import de.heliosdevelopment.helioslobby.listener.PlayerListener;
import de.heliosdevelopment.helioslobby.listener.WorldListener;
import de.heliosdevelopment.helioslobby.manager.ChatManager;
import de.heliosdevelopment.helioslobby.navigator.NavigatorManager;
import de.heliosdevelopment.helioslobby.player.PlayerManager;

public class Lobby extends JavaPlugin {

	private static Lobby instance;
	private final boolean silentLobby = false;
	private MySQL mysql;
	private ChatManager chatManager;

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

		try{
			mysql = new MySQL();
		}catch(Exception exception){
			exception.printStackTrace();
		}

		getCommand("set").setExecutor(new SetCommand());
		getCommand("remove").setExecutor(new RemoveCommand());
		getCommand("clearchat").setExecutor(new ClearChatCommand());
		getCommand("clear").setExecutor(new ClearCommand());
		getCommand("gm").setExecutor(new GmCommand());
		getCommand("vanish").setExecutor(new VanishCommand());

		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new EntityListener(), this);
		Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
		Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new CosmeticListener(), this);
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		CosmeticManager.init();
		NavigatorManager.init();
		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerManager.loadPlayer(player);
			CosmeticManager.equitItems(player);
		}
	}

	@Override
	public void onDisable() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			CosmeticManager.updateCosmetics(player);
			PlayerManager.unloadPlayer(player);
		}
		mysql.close();
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

}
