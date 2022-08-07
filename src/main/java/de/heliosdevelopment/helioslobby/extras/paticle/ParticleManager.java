package de.heliosdevelopment.helioslobby.extras.paticle;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.heliosdevelopment.helioslobby.extras.CosmeticManager;

public class ParticleManager {

    private static final Map<Player, ParticleCosmetic> particle = new HashMap<>();

    public static void addPlayer(Player player, ParticleCosmetic cosmetic) {
        particle.put(player, cosmetic);
    }

    private static String nmsver;

    static {

        nmsver = Bukkit.getServer().getClass().getPackage().getName();
        nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
    }

    public static synchronized void removePlayer(Player p) {
        ParticleCosmetic particlePlayer = particle.get(p);
        if (particlePlayer != null) {
            particle.remove(p);
        }
    }

    public static void playEffect(Player p) {
        ParticleCosmetic cosmetic = particle.get(p);
        if (cosmetic != null) {
            sendPacket(cosmetic.getPaket(p.getLocation()));
        }
    }

    /*public static ParticleCosmetic getCosmeticByID(int id) {
        for (CosmeticItem cosmetic : CosmeticManager.getItems(CosmeticType.PARTICLE)) {
            if (((ParticleCosmetic) cosmetic).getEffect().c() == id)
                return ((ParticleCosmetic) cosmetic);
        }
        return null;
    }*/

    public static Map<Player, ParticleCosmetic> getParticle() {
        return particle;
    }

    private static void sendPacket(Object packet) {
        try {
            Class<?> c5 = Class.forName("net.minecraft.server." + nmsver + ".Packet");
            for (Player player : Bukkit.getOnlinePlayers()) {
                Object nmsPlayer = player.getClass().getMethod("getHandle").invoke(player);
                Object playerConnection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
                playerConnection.getClass().getMethod("sendPacket", CosmeticManager.getNMSClass("Packet")).invoke(playerConnection, packet);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
