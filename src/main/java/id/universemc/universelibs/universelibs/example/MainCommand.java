package id.universemc.universelibs.universelibs.example;

import id.universemc.universelibs.universelibs.commands.BaseCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class MainCommand extends BaseCommand {

    public MainCommand(JavaPlugin plugin) {
        super(plugin, "unilibs", Arrays.asList("universelibs", "unlibs", "unl"), "perm.test",
                sender -> sender.sendMessage("No Arguments"),
                sender -> sender.sendMessage("No Permission"),
                sender -> sender.sendMessage("No SubCommand"),
                new MainSubCommand());
        this.register();
    }

}