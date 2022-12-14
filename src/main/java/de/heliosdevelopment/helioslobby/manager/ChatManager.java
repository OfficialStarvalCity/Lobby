package de.heliosdevelopment.helioslobby.manager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import de.heliosdevelopment.helioslobby.Lobby;

public class ChatManager {
	private final static File file = new File(Lobby.getInstance().getDataFolder() + "//messages.yml");
	private final static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

	private final HashMap<String, String> messages = new HashMap<>();

	public ChatManager() {
		initMessages();
	}

	private void initMessages() {
		cfg.set("prefix", "&7[&aLobby&7] ");
		cfg.set("nopermissions", "&cDu hast keine Rechte dafür!");

		cfg.set("addpoints", "&7You added §a%v0% %v1% §7points.");
		cfg.set("removepoints", "&7You removed §a%v0% %v1% §7points.");
		cfg.set("setpoints", "&7You have set §a%v0% §7points to §a%v1%.");
		cfg.set("getpoints", "&7The player §a%v0% §7has §a%v1% §7points.");
		cfg.set("playerdoesnotexist", "&cThe §a%v0% §cdoesn't exist!");
		cfg.set("notavalidnumber", "&cThis is not a valid number!");

		try {
			cfg.save(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (String key : cfg.getConfigurationSection("").getKeys(false)) {
			messages.put(key, ChatColor.translateAlternateColorCodes('&', cfg.getString(key)));
		}
	}

	public String getMessage(String key) {
		return messages.get(key);
	}

	public String getMessage(String key, String[] args) {
		String message = messages.get(key);
		for (int i = 0; i < args.length; i++) {
			if (message.contains("%v" + i + "%"))
				message = message.replace("%v" + i + "%", args[i]);
		}
		return message;
	}
}
