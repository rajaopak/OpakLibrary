package id.universemc.universelibs.universelibs.example;

import id.universemc.universelibs.universelibs.libs.CustomConfig;

public class ConfigFile {

    public static CustomConfig config;

    public static void init() {
        config = new CustomConfig("config.yml", null);
    }

    public static void saveAll() {
        config.saveConfig();
    }

    public static void reloadAll() {
        config.reloadConfig();
    }

}
