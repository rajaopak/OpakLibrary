package dev.rajaopak.OpakLibs.libs;

import dev.rajaopak.OpakLibs.OpakLibs;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class Task {

    public static BukkitTask sync(Runnable runnable) {
        return Bukkit.getScheduler().runTask(OpakLibs.INSTANCE, runnable);
    }

    public static BukkitTask syncLater(long delay, Runnable runnable) {
        return Bukkit.getScheduler().runTaskLater(OpakLibs.INSTANCE, runnable, delay);
    }

    public static BukkitTask syncTimer(long delay, long runEvery, Runnable runnable) {
        return Bukkit.getScheduler().runTaskTimer(OpakLibs.INSTANCE, runnable, delay, runEvery);
    }

    public static BukkitTask async(Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(OpakLibs.INSTANCE, runnable);
    }

    public static BukkitTask asyncLater(long delay, Runnable runnable) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(OpakLibs.INSTANCE, runnable, delay);
    }

    public static BukkitTask asyncTimer(long delay, long runEvery, Runnable runnable) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(OpakLibs.INSTANCE, runnable, delay, runEvery);
    }

}
