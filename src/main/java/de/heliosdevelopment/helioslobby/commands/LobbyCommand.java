package de.heliosdevelopment.helioslobby.commands;

import org.bukkit.command.CommandExecutor;

import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.manager.ChatManager;

public abstract class LobbyCommand implements CommandExecutor{
	protected final Lobby plugin = Lobby.getInstance();
	protected final ChatManager chatManager = plugin.getChatManager();
	protected final String prefix = chatManager.getMessage("prefix");

}
