package dev.rajaopak.opaklibrary.inventory;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

/**
 * Updating gui, usage for animation or else
 *
 * @author rajaopak
 */
public class RefreshGui extends BukkitRunnable {

    private final Plugin plugin;
    private final GuiBuilder inv;
    private Runnable runnable;

    public RefreshGui(Plugin plugin, GuiBuilder inv) {
        this.plugin = plugin;
        this.inv = inv;
    }

    public void start(@NotNull Runnable runnable, int ticks) {
        this.runnable = runnable;
        runTaskTimerAsynchronously(this.plugin, 0, ticks);
    }

    @Override
    public void run() {
        if (this.inv.getInventory().getViewers().isEmpty()) {
            this.cancel();
        }

        this.runnable.run();
        this.inv.updateInventory();
    }
}
