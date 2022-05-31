package dev.rajaopak.OpakLibs.example;

import dev.rajaopak.OpakLibs.libs.CustomConfig;
import dev.rajaopak.OpakLibs.OpakLib;

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
