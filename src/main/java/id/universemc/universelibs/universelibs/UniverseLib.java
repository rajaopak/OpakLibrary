package id.universemc.universelibs.universelibs;

import id.universemc.universelibs.universelibs.example.TestCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class UniverseLib extends JavaPlugin {

    private static UniverseLib INSTANCE;

    public static UniverseLib getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;
        UniverseLibs.init(this);
        new TestCommand(this);
        /*new SpigotCommand(this, "test", Arrays.asList("test123", "t"), "perm.test",
                sender -> sender.sendMessage("No Arguments"),
                sender -> sender.sendMessage("No Permission"),
                sender -> sender.sendMessage("No SubCommand"),
                new TestSubCommand()).register();*/
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
