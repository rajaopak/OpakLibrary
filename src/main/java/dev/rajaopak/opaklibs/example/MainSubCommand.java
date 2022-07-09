package dev.rajaopak.opaklibs.example;

import dev.rajaopak.opaklibs.OpakLib;
import dev.rajaopak.opaklibs.commands.SubCommand;
import dev.rajaopak.opaklibs.inventory.RefreshGui;
import dev.rajaopak.opaklibs.inventory.SimpleInventory;
import dev.rajaopak.opaklibs.libs.Common;
import dev.rajaopak.opaklibs.libs.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
        return "opaklibs.test.gui";
    }

    @Override
    public @Nullable List<String> parseTabCompletions(JavaPlugin plugin, CommandSender sender, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void execute(JavaPlugin plugin, CommandSender sender, String[] args) {
        SimpleInventory inv = new SimpleInventory(54, "Test Inventory");
        RefreshGui refreshGui = new RefreshGui(inv);
        refreshGui.start(() -> {
            inv.setItems(inv.getBorders(), new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                    .build());
            inv.setItem(22, new ItemBuilder(Material.WRITTEN_BOOK)
                    .setName("&eUptime")
                    .setLore("&e" + Common.formatTime((int) OpakLib.getInstance().getUpTime()))
                    .build());
        }, 5);
        inv.open((Player) sender);
    }
}
