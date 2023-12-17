package dev.rajaopak.opaklibrary;

import dev.rajaopak.opaklibrary.inventory.GuiBuilderManager;
import dev.rajaopak.opaklibrary.libs.ChatSession;
import dev.rajaopak.opaklibrary.libs.Common;
import dev.rajaopak.opaklibrary.libs.MarkCooldown;
import dev.rajaopak.opaklibrary.libs.PlayerCooldown;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class OpakLibrary {

    public static JavaPlugin INSTANCE;
    public static boolean PLACEHOLDER_API = false;

    private static PlayerCooldown cooldown;
    private static MarkCooldown cdMark;

    public static void init(JavaPlugin plugin) {
        INSTANCE = plugin;
        cooldown = new PlayerCooldown(plugin);
        cdMark = new MarkCooldown(plugin);
        PLACEHOLDER_API = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;

        GuiBuilderManager.register();

        plugin.getServer().getPluginManager().registerEvents(new ChatSession(), plugin);
        Common.log("OpakLibrary Successfully Loaded!");
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
