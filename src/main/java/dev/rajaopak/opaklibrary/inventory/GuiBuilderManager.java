package dev.rajaopak.opaklibrary.inventory;

import dev.rajaopak.opaklibrary.OpakLibrary;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Manager for SimpleInventory listeners.
 *
 * @author MrMicky, (edited by) rajaopak
 */
public class GuiBuilderManager {

    private static final AtomicBoolean REGISTERED = new AtomicBoolean(false);

    private GuiBuilderManager() {
        throw new UnsupportedOperationException();
    }

    /**
     * Register listeners for SimpleInventory.
     *
     * @throws IllegalStateException if SimpleInventory is already registered
     */
    public static void register() {
        if (REGISTERED.getAndSet(true)) {
            throw new IllegalStateException("OpakInventory is already registered");
        }

        Bukkit.getPluginManager().registerEvents(new InventoryListener(OpakLibrary.INSTANCE), OpakLibrary.INSTANCE);
    }

    /**
     * Close all open SimpleInventory inventories.
     */
    public static void closeAll() {
        Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.getOpenInventory().getTopInventory().getHolder() instanceof GuiBuilder)
                .forEach(Player::closeInventory);
    }

    public static final class InventoryListener implements Listener {

        private final Plugin plugin;

        public InventoryListener(Plugin plugin) {
            this.plugin = plugin;
        }

        @EventHandler
        public void onInventoryClick(InventoryClickEvent e) {
            if (e.getInventory().getHolder() instanceof GuiBuilder && e.getClickedInventory() != null && e.getClickedInventory().getType() != InventoryType.PLAYER) {
                GuiBuilder inv = (GuiBuilder) e.getInventory().getHolder();

                if (inv.isTakeAble()) {
                    inv.handleClick(e);
                    return;
                }

                boolean wasCancelled = e.isCancelled();
                e.setCancelled(true);

                inv.handleClick(e);

                // This prevents un-canceling the event if another plugin canceled it before
                if (!wasCancelled && !e.isCancelled()) {
                    e.setCancelled(false);
                }
            }
        }

        @EventHandler
        public void onInventoryDrag(InventoryDragEvent e) {
            if (e.getInventory().getHolder() instanceof GuiBuilder) {
                GuiBuilder inv = (GuiBuilder) e.getInventory().getHolder();

                inv.handleDrag(e);
            }
        }

        @EventHandler
        public void onInventoryOpen(InventoryOpenEvent e) {
            if (e.getInventory().getHolder() instanceof GuiBuilder) {
                GuiBuilder inv = (GuiBuilder) e.getInventory().getHolder();

                inv.handleOpen(e);
            }
        }

        @EventHandler
        public void onInventoryClose(InventoryCloseEvent e) {
            if (e.getInventory().getHolder() instanceof GuiBuilder) {
                GuiBuilder inv = (GuiBuilder) e.getInventory().getHolder();

                if (inv.handleClose(e)) {
                    Bukkit.getScheduler().runTask(this.plugin, () -> inv.open(e.getPlayer()));
                }

                if (inv.isUnCloseable()) {
                    Bukkit.getScheduler().runTaskLater(this.plugin, () -> inv.open(e.getPlayer()), 1);
                }
            }
        }

        @EventHandler
        public void onPluginDisable(PluginDisableEvent e) {
            if (e.getPlugin() == this.plugin) {
                closeAll();

                REGISTERED.set(false);
            }
        }
    }

}
