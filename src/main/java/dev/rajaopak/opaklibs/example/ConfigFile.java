package dev.rajaopak.opaklibs.example;

import dev.rajaopak.opaklibs.libs.CustomConfig;
import dev.rajaopak.opaklibs.OpakLib;

public class ConfigFile {

    public static CustomConfig config;

    public static void init() {
        config = new CustomConfig(OpakLib.getInstance(), "config.yml", null);
    }

    public static void saveAll() {
        config.saveConfig();
    }

    public static void reloadAll() {
        config.reloadConfig();
    }

}
