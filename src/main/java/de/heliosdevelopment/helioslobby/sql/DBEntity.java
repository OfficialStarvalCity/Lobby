package de.heliosdevelopment.helioslobby.sql;

import java.util.UUID;


public class DBEntity {

    private UUID uniqueId;
    private String name;
    private boolean nickEnabled;
    private DBSkin dbSkin;

    public DBEntity(UUID uniqueId,String name, boolean nickEnabled,DBSkin dbSkin){
        this.uniqueId = uniqueId;
        this.name = name;
        this.nickEnabled = nickEnabled;
        this.dbSkin = dbSkin;
    }

    public String getName() {
        return name;
    }

    public DBSkin getDbSkin() {
        return dbSkin;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public boolean isNickEnabled() {
        return nickEnabled;
    }

    public void setDbSkin(DBSkin dbSkin) {
        this.dbSkin = dbSkin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickEnabled(boolean nickEnabled) {
        this.nickEnabled = nickEnabled;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }
}