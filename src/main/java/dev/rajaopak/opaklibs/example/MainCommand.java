package dev.rajaopak.opaklibs.example;

import dev.rajaopak.opaklibs.commands.BaseCommand;
import dev.rajaopak.opaklibs.libs.Common;
import dev.rajaopak.opaklibs.messages.CenteredMessage;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class MainCommand extends BaseCommand {

    public MainCommand(JavaPlugin plugin) {
        super(plugin, "oplibs", Arrays.asList("opaklibs", "olibs", "opl"), "opaklibs.test",
                sender -> {
                    Common.sendMessage(sender, " ");
                    CenteredMessage.sendMessage(sender, "&bOpak&aLibs &6Made by rajaopak");
                    Common.sendMessage(sender, " ");
                },
                sender -> Common.sendMessage(sender, "&cYou don't have permission to use this command!"),
                sender -> Common.sendMessage(sender, "&cPlease specify correct Subcommand!"),
                null,
                new MainSubCommand(), new ReloadSubCommand(), new CooldownSubCommand());
        this.register();
    }

}