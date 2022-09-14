package de.heliosdevelopment.helioslobby.utils;


import de.heliosdevelopment.helioslobby.Lobby;

public class Setting {

    private final String name;
    private final SettingType type;
    private final SettingCategory category;
    private boolean enabled;

    public Setting(String name, SettingCategory category, SettingType type) {
        this.name = name;
        this.category = category;
        this.enabled = Lobby.getInstance().getConfig().getBoolean("settings." + category.toString().toLowerCase() + "." + name.toLowerCase());
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public SettingType getType() {
        return type;
    }

    public SettingCategory getCategory() {
        return category;
    }
}
