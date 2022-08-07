package de.heliosdevelopment.helioslobby.extras.paticle;

import de.heliosdevelopment.helioslobby.extras.CosmeticManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.heliosdevelopment.helioslobby.extras.CosmeticItem;
import de.heliosdevelopment.helioslobby.extras.CosmeticType;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

public class ParticleCosmetic extends CosmeticItem {

    private final Enum<?> effect;
    private final double radiusX;
    private final double radiusY;
    private final double radiusZ;
    private final double speed;
    private final int amount;

    public ParticleCosmetic(String name, CosmeticType type, Material mat, int requeredLevel, Enum<?> effect,
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

    public Object getPaket(Location location) {
        try {
            return CosmeticManager.getNMSClass("PacketPlayOutWorldParticles").getConstructor(CosmeticManager.getNMSClass("EnumParticle"), boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, Array.newInstance(int.class, 2).getClass()).newInstance(effect, false, (float) location.getX(), (float) location.getY(),
                    (float) location.getZ(), (float) radiusX, (float) radiusY, (float) radiusZ, (float) speed,
                    amount, new int[]{17, 3});
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


        //return new PacketPlayOutWorldParticles((EnumParticle) effect, false, (float) location.getX(), (float) location.getY(),
        //     (float) location.getZ(), (float) radiusX, (float) radiusY, (float) radiusZ, (float) speed,
        //  amount, 17, 3);
        return null;

    }

    public Enum<?> getEffect() {
        return effect;
    }
}
