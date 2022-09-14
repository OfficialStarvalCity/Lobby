package de.heliosdevelopment.helioslobby;


import de.heliosdevelopment.helioslobby.sql.DBSkin;

public class NickData {
    private final String name;
    private final DBSkin dbSkin;

    public NickData(String name, DBSkin dbSkin) {
        this.name = name;
        this.dbSkin = dbSkin;
    }

    public DBSkin getDbSkin() {
        return dbSkin;
    }

    public String getName() {
        return name;
    }
}