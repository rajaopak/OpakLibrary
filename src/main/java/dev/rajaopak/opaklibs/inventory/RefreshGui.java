package dev.rajaopak.opaklibs.inventory;

import dev.rajaopak.opaklibs.OpakLibs;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class RefreshGui extends BukkitRunnable {

    private final SimpleInventory inv;
    private Runnable runnable;

    public RefreshGui(SimpleInventory inv) {
        this.inv = inv;
    }

    public void start(@NotNull Runnable runnable, int ticks) {
        this.runnable = runnable;
        runTaskTimer(OpakLibs.getInstance(), 0, ticks);
    }

    @Override
    public void run() {
        if (inv.getInventory().getViewers().isEmpty()) {
            this.cancel();
        }

        runnable.run();
        inv.updateInventory();
    }
}
