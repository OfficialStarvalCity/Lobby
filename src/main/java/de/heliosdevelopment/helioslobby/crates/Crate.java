package de.heliosdevelopment.helioslobby.crates;


/**
 * Created by Teeage on 29.07.2017.
 */
public class Crate {

    private final String name;
    private final CrateType type;
    private int cosmeticId;
    private int amount;
    private String description;

    public Crate(String name, CrateType type, int i, String description) {
        this.name = name;
        this.type = type;
        if (type == CrateType.COINS)
            this.amount = i;
        else
            this.cosmeticId = i;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public CrateType getType() {
        return type;
    }

    public int getCosmeticId() {
        return cosmeticId;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
