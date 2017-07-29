package de.heliosdevelopment.helioslobby.extras.paticle;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.heliosdevelopment.helioslobby.extras.CosmeticItem;
import de.heliosdevelopment.helioslobby.extras.CosmeticType;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class ParticleCosmetic extends CosmeticItem {

	private final EnumParticle effect;
	private final double radiusX;
	private final double radiusY;
	private final double radiusZ;
	private final double speed;
	private final int amount;

	public ParticleCosmetic(String name, CosmeticType type, Material mat, int requeredLevel, EnumParticle effect,
			double radiusX, double radiusY, double radiusZ, double speed, int amount) {
		super(name, type, mat, requeredLevel);
		this.effect = effect;
		this.radiusX = radiusX;
		this.radiusY = radiusY;
		this.radiusZ = radiusZ;
		this.speed = speed;
		this.amount = amount;
	}

	@Override
	public void equipItem(Player player) {
		ParticleManager.addPlayer(player, this);
	}

	public Packet<?> getPaket(Location location) {
		return new PacketPlayOutWorldParticles(effect, false, (float) location.getX(), (float) location.getY(),
				(float) location.getZ(), (float) radiusX, (float) radiusY, (float) radiusZ, (float) speed,
                amount, 17, 3);

	}

	public EnumParticle getEffect() {
		return effect;
	}
}
