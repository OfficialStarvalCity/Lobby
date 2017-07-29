package de.heliosdevelopment.helioslobby.extras.paticle;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.heliosdevelopment.helioslobby.extras.CosmeticItem;
import de.heliosdevelopment.helioslobby.extras.CosmeticManager;
import de.heliosdevelopment.helioslobby.extras.CosmeticType;
import net.minecraft.server.v1_8_R3.Packet;

public class ParticleManager {

	private static final Map<Player, ParticleCosmetic> particle = new HashMap<>();

	public static void addPlayer(Player player, ParticleCosmetic cosmetic) {
		particle.put(player, cosmetic);
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

	public static ParticleCosmetic getCosmeticByID(int id) {
		for (CosmeticItem cosmetic : CosmeticManager.getItems(CosmeticType.PARTICLE))
			if (((ParticleCosmetic) cosmetic).getEffect().c() == id)
				return ((ParticleCosmetic) cosmetic);
		return null;
	}

	public static Map<Player, ParticleCosmetic> getParticle() {
		return particle;
	}

	private static void sendPacket(Packet<?> packet) {
		for (Player player : Bukkit.getOnlinePlayers())
			// if
			// (SettingHandler.getPlayerSettings(player).getSettingValue(SettingHandler.getSetting(6)))
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

}
