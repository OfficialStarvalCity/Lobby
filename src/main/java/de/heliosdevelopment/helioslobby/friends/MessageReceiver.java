package de.heliosdevelopment.helioslobby.friends;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import de.heliosdevelopment.helioslobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

public class MessageReceiver implements PluginMessageListener {

    private final FriendManager friendManager;

    public MessageReceiver(FriendManager friendManager) {
        this.friendManager = friendManager;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("HeliosBungee")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if(!subchannel.equalsIgnoreCase("UpdateFriends"))
            return;
        Player target = Bukkit.getPlayer(in.readUTF());
        if (target != null) {
            new BukkitRunnable(){

                @Override
                public void run() {
                    friendManager.updatePlayer(target);
                }
            }.runTaskLater(Lobby.getInstance(), 50);
        }

    }
}
