package de.heliosdevelopment.helioslobby.crates;


import org.bukkit.inventory.ItemStack;

/**
 * Created by Teeage on 29.07.2017.
 */
public class Crate {

    private final String name;
    private final CrateType type;
    private int cosmeticId;
    private int amount;

    public Crate(String name, CrateType type, int i){
        this.name = name;
        this.type = type;
        if(type == CrateType.COINS)
            this.amount = i;
        else
            this.cosmeticId = i;
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

    public ItemStack getItem(){
        return null;
    }
}
