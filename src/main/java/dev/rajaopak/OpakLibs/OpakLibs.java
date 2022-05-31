package dev.rajaopak.OpakLibs;

import dev.rajaopak.OpakLibs.inventory.SimpleInventoryManager;
import dev.rajaopak.OpakLibs.libs.Common;
import dev.rajaopak.OpakLibs.libs.MarkCooldown;
import dev.rajaopak.OpakLibs.libs.PlayerCooldown;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class OpakLibs {

    public static JavaPlugin INSTANCE;
    public static boolean PLACEHOLDER_API = false;

    private static PlayerCooldown cooldown;
    private static MarkCooldown cdMark;

    public static void init(JavaPlugin plugin) {
        INSTANCE = plugin;
        cooldown = new PlayerCooldown(plugin);
        cdMark = new MarkCooldown(plugin);
        SimpleInventoryManager.register(plugin);
        new PlayerCooldown(plugin);
        PLACEHOLDER_API = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
        Common.log("&aUniverseLibs Successfully Loaded!");
    }

    public static JavaPlugin getInstance() {
        return INSTANCE;
    }

    public static PlayerCooldown getCooldown() {
        return cooldown;
    }

    public static MarkCooldown getCooldownMark() {
        return cdMark;
    }
}
