package id.universemc.universelibs.universelibs.commands;

import id.universemc.universelibs.universelibs.libs.Common;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

/**
 * modified by rajaopak
 * @author aglerr
 */
public class BaseCommand extends Command implements Listener {

    private final Map<String, SubCommand> subCommandMap = new HashMap<>();

    private final JavaPlugin plugin;
    private final @NotNull String COMMAND_NAME;
    private final @NotNull List<String> COMMAND_ALIASES;
    private final @Nullable String COMMAND_PERMISSION;
    protected final Consumer<CommandSender> onNoArgs;
    protected final Consumer<CommandSender> onNoPermission;
    protected final Consumer<CommandSender> onNoSubcommand;

    public BaseCommand(JavaPlugin plugin, @NotNull String name, @NotNull List<String> aliases,
                       @Nullable String COMMAND_PERMISSION, Consumer<CommandSender> onNoArgs,
                       Consumer<CommandSender> onNoPermission, Consumer<CommandSender> onNoSubcommand,
                       @Nullable SubCommand... subCommands) {
        super(name, "universe commands", "/" + name, aliases);
        this.plugin = plugin;
        this.COMMAND_NAME = name;
        this.COMMAND_ALIASES = aliases;
        this.COMMAND_PERMISSION = COMMAND_PERMISSION;
        this.onNoArgs = onNoArgs;
        this.onNoPermission = onNoPermission;
        this.onNoSubcommand = onNoSubcommand;
        this.setPermission(COMMAND_PERMISSION);
        this.setAliases(COMMAND_ALIASES);
        this.setName(COMMAND_NAME);
        this.setLabel(COMMAND_NAME);

        if (subCommands != null) {
            for (SubCommand subCommand : subCommands) {
                this.subCommandMap.put(subCommand.getName().toLowerCase(), subCommand);
                this.setUsage("/" + COMMAND_NAME + " " + subCommand.getUsage());
            }
        } else {
            this.setUsage("/" + COMMAND_NAME);
        }
    }

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            commandMap.register(getLabel(), this);
        } catch (Exception e) {
            Common.log("&cFailed to register command! [" + getName() + "]");
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(COMMAND_PERMISSION != null && !sender.hasPermission(COMMAND_PERMISSION)){
            onNoPermission.accept(sender);
            return true;
        }
        if (args.length == 0) {
            onNoArgs.accept(sender);
            return true;
        }

        SubCommand subCommand = this.subCommandMap.get(args[0].toLowerCase());
        if (subCommand == null) {
            onNoSubcommand.accept(sender);
            return true;
        }

        if (subCommand.getPermission() != null &&
                !sender.hasPermission(subCommand.getPermission())) {
            onNoPermission.accept(sender);
            return true;
        }

        subCommand.execute(plugin, sender, args);
        return true;
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> suggestions = new ArrayList<>();
            for (SubCommand subCommand : subCommandMap.values()) {
                if (subCommand.getPermission() == null) {
                    suggestions.add(subCommand.getName());
                    continue;
                }
                if (sender.hasPermission(subCommand.getPermission())) {
                    suggestions.add(subCommand.getName());
                }
            }
            return suggestions;
        }
        if (args.length >= 2) {
            SubCommand subCommand = subCommandMap.get(args[0].toLowerCase());
            if (subCommand == null) {
                return new ArrayList<>();
            }
            if (subCommand.getPermission() == null ||
                    sender.hasPermission(subCommand.getPermission())) {
                return Objects.requireNonNull(subCommand.parseTabCompletions(plugin, sender, args));
            }
        }
        return new ArrayList<>();
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandSendEvent event) {

        Player p = event.getPlayer();

        if (event.getCommands().stream().anyMatch(command -> command.startsWith(COMMAND_NAME))) {
            if (COMMAND_PERMISSION != null && !p.hasPermission(COMMAND_PERMISSION)) {
                event.getCommands().remove(COMMAND_NAME);
            } else {
                event.getCommands().add(COMMAND_NAME);
            }
        }

    }

}
