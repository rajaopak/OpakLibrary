package id.universemc.universelibs.universelibs.libs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerCooldown {

    private final HashMap<UUID, Long> cooldowns;
    private final JavaPlugin plugin;

    public PlayerCooldown(JavaPlugin plugin) {
        this.plugin = plugin;
        this.cooldowns = new HashMap<>();
        Task.asyncTimer(10, 10, () -> {
            for (UUID uuid : cooldowns.keySet()) {
                if (cooldowns.get(uuid) <= System.currentTimeMillis()) {
                    cooldowns.remove(uuid);
                }
            }
        });
    }

    public void setCooldown(UUID uuid, long seconds) {
        if (!hasCooldown(uuid)) {
            cooldowns.put(uuid, System.currentTimeMillis() + (seconds * 1000));
            return;
        }
        start(uuid);
    }

    public void setCooldownAction(UUID uuid, long seconds, Consumer<Player> consumer) {
        if (!hasCooldown(uuid)) {
            cooldowns.put(uuid, System.currentTimeMillis() + (seconds * 1000));
            consumer.accept(Bukkit.getPlayer(uuid));
            return;
        }
        start(uuid);
    }

    public void start(UUID uuid) {
        if (hasCooldown(uuid)) {
            if (cooldowns.get(uuid) <= System.currentTimeMillis()) {
                stop(uuid);
                Common.sendMessage(Bukkit.getPlayer(uuid), "&cYou have been removed from the cooldown.");
            } else {
                Common.sendMessage(Bukkit.getPlayer(uuid), "&cYou are still on cooldown for&e" + timeLeft((int) ((cooldowns.get(uuid) - System.currentTimeMillis()) / 1000)));
            }
        }
    }

    public void stop(UUID uuid) {
        if (hasCooldown(uuid)) {
            this.removeCooldown(uuid);
        }
    }

    public void removeCooldown(UUID player) {
        cooldowns.remove(player);
    }

    public long getCooldown(UUID player) {
        return cooldowns.get(player);
    }

    public boolean hasCooldown(UUID player) {
        return cooldowns.containsKey(player);
    }

    public String timeLeft(long timeoutSeconds) {
        long days = timeoutSeconds / 86400;
        long hours = (timeoutSeconds / 3600) % 24;
        long minutes = (timeoutSeconds / 60) % 60;
        long seconds = timeoutSeconds % 60;
        return (days > 0 ? " " + days + " day" + (days != 1 ? "s" : "") : "") + (hours > 0 ? " " + hours + " hour" + (hours != 1 ? "s" : "") : "")
                + (minutes > 0 ? " " + minutes + " minute" + (minutes != 1 ? "s" : "") : "") + (seconds > -5 ? " " + seconds + " second" + (seconds != 1 ? "s" : "") : "");
    }

}
