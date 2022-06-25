package dev.rajaopak.OpakLibs.example;

import dev.rajaopak.OpakLibs.libs.Common;
import dev.rajaopak.OpakLibs.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReloadSubCommand extends SubCommand {
    @Override
    public @NotNull String getName() {
        return "reload";
    }

    @Override
    public @Nullable List<String> getNames() {
        return null;
    }

    @Override
    public @Nullable String getUsage() {
        return "reload";
    }

    @Override
    public @Nullable String getPermission() {
        return "universelibs.reload";
    }

    @Override
    public @Nullable List<String> parseTabCompletions(JavaPlugin plugin, CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void execute(JavaPlugin plugin, CommandSender sender, String[] args) {
        ConfigFile.reloadAll();
        Common.sendMessage(sender, "&aAll File has been reloaded!", true);
    }
}
