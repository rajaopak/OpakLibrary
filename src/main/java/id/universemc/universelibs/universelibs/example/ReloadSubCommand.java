package id.universemc.universelibs.universelibs.example;

import id.universemc.universelibs.universelibs.commands.SubCommand;
import id.universemc.universelibs.universelibs.libs.Common;
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
