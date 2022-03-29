package id.universemc.universelibs.universelibs.libs;

import id.universemc.universelibs.universelibs.UniverseLibs;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class Task {

    public static BukkitTask sync(Runnable runnable) {
        return Bukkit.getScheduler().runTask(UniverseLibs.INSTANCE, runnable);
    }

    public static BukkitTask syncLater(long delay, Runnable runnable) {
        return Bukkit.getScheduler().runTaskLater(UniverseLibs.INSTANCE, runnable, delay);
    }

    public static BukkitTask syncTimer(long delay, long runEvery, Runnable runnable) {
        return Bukkit.getScheduler().runTaskTimer(UniverseLibs.INSTANCE, runnable, delay, runEvery);
    }

    public static BukkitTask async(Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(UniverseLibs.INSTANCE, runnable);
    }

    public static BukkitTask asyncLater(long delay, Runnable runnable) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(UniverseLibs.INSTANCE, runnable, delay);
    }

    public static BukkitTask asyncTimer(long delay, long runEvery, Runnable runnable) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(UniverseLibs.INSTANCE, runnable, delay, runEvery);
    }

}
