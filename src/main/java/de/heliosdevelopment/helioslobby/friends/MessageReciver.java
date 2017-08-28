package de.heliosdevelopment.helioslobby.friends;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class MessageReciver implements PluginMessageListener {

    private final FriendManager friendManager;

    public MessageReciver(FriendManager friendManager) {
        this.friendManager = friendManager;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("HeliosBungee")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        Player p = Bukkit.getPlayer(in.readUTF());
        if (p != null) {
            friendManager.updatePlayer(p);
        }

    }
}
