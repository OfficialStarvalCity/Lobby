package de.heliosdevelopment.helioslobby.sql;

public class DBSkin {

    private final String signature;
    private final String value;

    public DBSkin(String signature, String value){
        this.value = value;
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    public String getValue() {
        return value;
    }
}