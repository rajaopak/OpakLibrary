package dev.rajaopak.OpakLibs.example;

import dev.rajaopak.OpakLibs.commands.BaseCommand;
import dev.rajaopak.OpakLibs.libs.Common;
import dev.rajaopak.OpakLibs.messages.CenteredMessage;
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