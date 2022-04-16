package id.universemc.universelibs.universelibs;

import id.universemc.universelibs.universelibs.inventory.SimpleInventoryManager;
import id.universemc.universelibs.universelibs.libs.Common;
import id.universemc.universelibs.universelibs.libs.MarkCooldown;
import id.universemc.universelibs.universelibs.libs.PlayerCooldown;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class UniverseLibs {

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

    public static PlayerCooldown getCooldown() {
        return cooldown;
    }

    public static MarkCooldown getCooldownMark() {
        return cdMark;
    }
}
