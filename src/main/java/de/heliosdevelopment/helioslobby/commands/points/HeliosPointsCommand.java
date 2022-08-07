package de.heliosdevelopment.helioslobby.commands.points;

import java.util.UUID;

import de.heliosdevelopment.helioslobby.HeliosPointsAPI;
import de.heliosdevelopment.helioslobby.Lobby;
import de.heliosdevelopment.helioslobby.manager.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HeliosPointsCommand implements CommandExecutor {

    private final ChatManager chatManager = new ChatManager();
    private final String prefix = chatManager.getMessage("prefix");

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("points")) {
            if (args.length == 0) {
                sender.sendMessage("§7**********§8[§eHeliosPoints§8]§7**********");
                sender.sendMessage("§aDeveloper: §eTeeage");
                sender.sendMessage("§aVersion: §e" + Lobby.getInstance().getDescription().getVersion());
                sender.sendMessage("§aHelp Page §e/points help");
                sender.sendMessage("§7*********************************");
            }
            if (args.length >= 1) {
                if (!sender.hasPermission("heliospoints.admin")) {
                    sender.sendMessage(prefix + chatManager.getMessage("nopermissions"));
                    return true;
                }
                if (args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage("§7**********§8[§eHeliosPoints§8]§7**********");
                    sender.sendMessage("§a/points add (player) (amount)");
                    sender.sendMessage("§a/points remove (player) (amount)");
                    sender.sendMessage("§a/points set (player) (amount)");
                    sender.sendMessage("§a/points see (player)");
                    sender.sendMessage("§7*********************************");
                }
                if (args[0].equalsIgnoreCase("add")) {
                    if (args.length == 3) {
                        UUID uuid = null;
                        OfflinePlayer target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            target = Bukkit.getOfflinePlayer(args[1]);
                            if (target == null) {
                                sender.sendMessage(prefix
                                        + chatManager.getMessage("playerdoesnotexist", new String[] { args[1] }));
                                return true;
                            }
                            uuid = target.getUniqueId();
                        } else {
                            uuid = target.getUniqueId();
                        }
                        Integer value = 0;
                        try {
                            value = Integer.valueOf(args[2]);
                        } catch (NumberFormatException exception) {
                            sender.sendMessage(prefix + chatManager.getMessage("notavalidnumber"));
                            return true;
                        }
                        HeliosPointsAPI.addPoints(uuid, value);

                        sender.sendMessage(prefix + chatManager.getMessage("addpoints",
                                new String[] { target.getName(), value.toString() }));
                    } else {
                        sender.sendMessage(prefix + "§c/points add (player) (amount)");
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (args.length == 3) {
                        UUID uuid = null;
                        OfflinePlayer target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            target = Bukkit.getOfflinePlayer(args[1]);
                            if (target == null) {
                                sender.sendMessage(prefix
                                        + chatManager.getMessage("playerdoesnotexist", new String[] { args[1] }));
                                return true;
                            }
                            uuid = target.getUniqueId();
                        } else {
                            uuid = target.getUniqueId();
                        }
                        Integer value = 0;
                        try {
                            value = Integer.valueOf(args[2]);
                        } catch (NumberFormatException exception) {
                            sender.sendMessage(prefix + chatManager.getMessage("notavalidnumber"));
                            return true;
                        }
                        HeliosPointsAPI.removePoints(uuid, value);
                        sender.sendMessage(prefix + chatManager.getMessage("removepoints",
                                new String[] { target.getName(), value.toString() }));
                    } else {
                        sender.sendMessage(prefix + "§c/heliospoints remove (player) (amount)");
                    }
                } else if (args[0].equalsIgnoreCase("set")) {
                    if (args.length == 3) {
                        UUID uuid = null;
                        OfflinePlayer target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            target = Bukkit.getOfflinePlayer(args[1]);
                            if (target == null) {
                                sender.sendMessage(prefix
                                        + chatManager.getMessage("playerdoesnotexist", new String[] { args[1] }));
                                return true;
                            }
                            uuid = target.getUniqueId();
                        } else {
                            uuid = target.getUniqueId();
                        }
                        Integer value = 0;
                        try {
                            value = Integer.valueOf(args[2]);
                        } catch (NumberFormatException exception) {
                            sender.sendMessage(prefix + chatManager.getMessage("notavalidnumber"));
                            return true;
                        }
                        HeliosPointsAPI.setPoints(uuid, value);
                        sender.sendMessage(prefix + chatManager.getMessage("setpoints",
                                new String[] { target.getName(), value.toString() }));
                    } else {
                        sender.sendMessage(prefix + "§c/heliospoints set (player) (amount)");
                    }
                } else if (args[0].equalsIgnoreCase("see")) {
                    if (args.length == 2) {
                        UUID uuid = null;
                        OfflinePlayer target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            target = Bukkit.getOfflinePlayer(args[1]);
                            if (target == null) {
                                sender.sendMessage(prefix
                                        + chatManager.getMessage("playerdoesnotexist", new String[] { args[1] }));
                                return true;
                            }
                            uuid = target.getUniqueId();
                        } else {
                            uuid = target.getUniqueId();
                        }
                        sender.sendMessage(prefix + chatManager.getMessage("getpoints", new String[] { target.getName(),
                                Integer.valueOf(HeliosPointsAPI.getPoints(uuid)).toString() }));
                    } else {
                        sender.sendMessage(prefix + "§c/heliospoints see (player)");
                    }
                }
            }

        }
        return true;
    }
}
