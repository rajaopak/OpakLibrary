package id.universemc.universelibs.universelibs.example;

import id.universemc.universelibs.universelibs.commands.SubCommand;
import id.universemc.universelibs.universelibs.inventory.SimpleInventory;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class MainSubCommand extends SubCommand {

    @Override
    public @NotNull String getName() {
        return "gui";
    }

    @Override
    public @Nullable String getUsage() {
        return "gui";
    }

    @Override
    public @Nullable String getPermission() {
        return "perm.test.gui";
    }

    @Override
    public @Nullable List<String> parseTabCompletions(JavaPlugin plugin, CommandSender sender, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void execute(JavaPlugin plugin, CommandSender sender, String[] args) {
        SimpleInventory inv = new SimpleInventory(54, "Test Inventory");
        inv.setItems(inv.getBorders(), new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        inv.open((Player) sender);
    }
}
