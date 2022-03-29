package id.universemc.universelibs.universelibs.example;

import id.universemc.universelibs.universelibs.UniverseLib;
import id.universemc.universelibs.universelibs.commands.SpigotCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class TestCommand extends SpigotCommand {

    public TestCommand(JavaPlugin plugin) {
        super(plugin, "test", Arrays.asList("test123", "t"), "perm.test",
                sender -> sender.sendMessage("No Arguments"),
                sender -> sender.sendMessage("No Permission"),
                sender -> sender.sendMessage("No SubCommand"),
                new TestSubCommand());
        this.register();
    }

}