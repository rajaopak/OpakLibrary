package dev.rajaopak.opaklibs.example;

import dev.rajaopak.opaklibs.libs.Common;
import dev.rajaopak.opaklibs.OpakLibs;
import dev.rajaopak.opaklibs.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CooldownSubCommand extends SubCommand {
    @Override
    public @NotNull String getName() {
        return "testcooldown";
    }

    @Override
    public @Nullable String getUsage() {
        return "testcooldown";
    }

    @Override
    public @Nullable String getPermission() {
        return null;
    }

    @Override
    public @Nullable List<String> parseTabCompletions(JavaPlugin plugin, CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void execute(JavaPlugin plugin, CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            OpakLibs.getCooldown().setCooldownAction(player.getUniqueId(), 5, p -> {
                Common.sendMessage(p, "&aHello!");
            });
        }
    }
}
