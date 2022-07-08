package dev.rajaopak.opaklibs.example;

import dev.rajaopak.opaklibs.libs.Common;
import dev.rajaopak.opaklibs.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class TestSubCommand extends SubCommand {
    @Override
    public @NotNull String getName() {
        return "Hello";
    }

    @Override
    public @Nullable String getUsage() {
        return "Hello";
    }

    @Override
    public @Nullable String getPermission() {
        return null;
    }

    @Override
    public @Nullable List<String> parseTabCompletions(JavaPlugin plugin, CommandSender sender, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void execute(JavaPlugin plugin, CommandSender sender, String[] args) {
        Common.sendMessage(sender, "&eHello World!");
    }
}
