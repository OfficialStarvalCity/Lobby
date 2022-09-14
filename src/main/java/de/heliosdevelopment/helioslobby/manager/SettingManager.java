package de.heliosdevelopment.helioslobby.manager;

import de.heliosdevelopment.helioslobby.utils.Setting;

import java.util.ArrayList;
import java.util.List;

public class SettingManager {
    private final static List<Setting> settings = new ArrayList<>();

    public static void addSetting(Setting setting) {
        settings.add(setting);
    }

    public static List<Setting> getSettings() {
        return settings;
    }

    public static Setting getSetting(String name) {
        for (Setting setting : settings) {
            if (setting.getName().equals(name))
                return setting;
        }
        return null;
    }
}
