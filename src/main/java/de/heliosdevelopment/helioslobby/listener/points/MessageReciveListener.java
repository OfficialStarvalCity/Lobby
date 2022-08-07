package de.heliosdevelopment.helioslobby.listener.points;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import de.heliosdevelopment.helioslobby.HeliosPointsAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.UUID;

public class MessageReciveListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("HeliosBungee")) {
            return;
        }
        System.out.println("MessageReciveListener active");
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equalsIgnoreCase("UpdatePoints")) {
            System.out.println("MessageReciveListener subchannel");
            UUID uuid = UUID.fromString(in.readUTF());
            Integer points = Integer.valueOf(in.readUTF().trim());
            System.out.println("MessageReciveListener "+uuid.toString());
            System.out.println("MessageReciveListener "+points.toString());
            HeliosPointsAPI.addPoints(uuid, points);
        }
    }
}
