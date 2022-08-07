package de.heliosdevelopment.helioslobby.player.points;

import java.util.UUID;

public class PointsPlayer {

    private final UUID uuid;
    private int points;

    public PointsPlayer(UUID uuid, int points) {
        this.uuid = uuid;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public UUID getUuid() {
        return uuid;
    }

}
