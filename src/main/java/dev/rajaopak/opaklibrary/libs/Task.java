package dev.rajaopak.opaklibrary.libs;

import dev.rajaopak.opaklibrary.OpakLibrary;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class Task {

    public static void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(OpakLibrary.INSTANCE, runnable);
    }

    public static void syncLater(long delay, Runnable runnable) {
        Bukkit.getScheduler().runTaskLater(OpakLibrary.INSTANCE, runnable, delay);
    }

    public static BukkitTask syncTimer(long delay, long runEvery, Runnable runnable) {
        return Bukkit.getScheduler().runTaskTimer(OpakLibrary.INSTANCE, runnable, delay, runEvery);
    }

    public static BukkitTask async(Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(OpakLibrary.INSTANCE, runnable);
    }

    public static void asyncLater(long delay, Runnable runnable) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(OpakLibrary.INSTANCE, runnable, delay);
    }

    public static void asyncTimer(long delay, long runEvery, Runnable runnable) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(OpakLibrary.INSTANCE, runnable, delay, runEvery);
    }

}
