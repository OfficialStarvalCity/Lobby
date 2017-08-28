package de.heliosdevelopment.helioslobby.crates;

import org.bukkit.Location;

public class CrateChest {
    private boolean used;
    private Location location;
    private Location holoLocation;

    public CrateChest(Location location) {
        this.location = location;
        this.holoLocation = location.clone().add(0.5, 0.5, 0.5);
        this.used = false;
    }

    public Location getLocation() { return location; }

    public Location getHoloLocation() {
        return holoLocation;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
