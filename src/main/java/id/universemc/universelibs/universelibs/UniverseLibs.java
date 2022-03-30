package id.universemc.universelibs.universelibs;

import id.universemc.universelibs.universelibs.inventory.SimpleInventoryManager;
import id.universemc.universelibs.universelibs.libs.Common;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class UniverseLibs {

    public static JavaPlugin INSTANCE;
    public static boolean PLACEHOLDER_API = false;

    public static void init(JavaPlugin plugin) {
        INSTANCE = plugin;
        SimpleInventoryManager.register(plugin);
        PLACEHOLDER_API = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
        Common.log("&aUniverseLibs Successfully Loaded!");
    }
}
