package id.universemc.universelibs.universelibs.example;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigValue {

    public static String PREFIX;

    public static void init(FileConfiguration config) {
        PREFIX = config.getString("prefix");
    }

}
