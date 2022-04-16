package id.universemc.universelibs.universelibs.example;

import id.universemc.universelibs.universelibs.commands.BaseCommand;
import id.universemc.universelibs.universelibs.libs.Common;
import id.universemc.universelibs.universelibs.messages.CenteredMessage;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class MainCommand extends BaseCommand {

    public MainCommand(JavaPlugin plugin) {
        super(plugin, "unilibs", Arrays.asList("universelibs", "unlibs", "unl"), "universelibs.test",
                sender -> {
                    Common.sendMessage(sender, " ");
                    CenteredMessage.sendMessage(sender, "&bUniv&eerse&aLibs &6Made by rajaopak");
                    Common.sendMessage(sender, " ");
                },
                sender -> Common.sendMessage(sender, "&cYou don't have permission to use this command!"),
                sender -> Common.sendMessage(sender, "&cPlease specify correct Subcommand!"),
                new MainSubCommand(), new ReloadSubCommand(), new CooldownSubCommand());
        this.register();
    }

}