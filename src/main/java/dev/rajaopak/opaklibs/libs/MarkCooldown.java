package dev.rajaopak.opaklibs.libs;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.function.Consumer;

public class MarkCooldown {

    private final HashMap<String, Long> cooldowns;
    private final JavaPlugin plugin;

    public MarkCooldown(JavaPlugin plugin) {
        this.plugin = plugin;
        cooldowns = new HashMap<>();
    }

    public void setCooldown(String mark, long time) {
        if (!hasCooldown(mark)) {
            cooldowns.put(mark, System.currentTimeMillis() + (time * 1000));
            return;
        }
        start(mark);
    }

    public void setCooldown(String mark, long seconds, Consumer<String> consumer) {
        if (!hasCooldown(mark)) {
            cooldowns.put(mark, System.currentTimeMillis() + (seconds * 1000));
            consumer.accept(mark);
            return;
        }
        start(mark);
    }

    public void start(String mark) {
        if (hasCooldown(mark)) {
            if (cooldowns.get(mark) <= System.currentTimeMillis()) {
                stop(mark);
                Common.sendMessage(Bukkit.getPlayer(mark), "&cCooldown has been stopped.");
            } else {
                Common.sendMessage(Bukkit.getPlayer(mark), "&cCooldown is still active. Time left:&e" + timeLeft((int) ((cooldowns.get(mark) - System.currentTimeMillis()) / 1000)));
            }
        }
    }

    public void stop(String mark) {
        if (hasCooldown(mark)) {
            this.removeCooldown(mark);
        }
    }

    public long getCooldown(String mark) {
        return cooldowns.get(mark);
    }

    public void removeCooldown(String mark) {
        cooldowns.remove(mark);
    }

    public boolean hasCooldown(String mark) {
        return cooldowns.containsKey(mark);
    }

    public String timeLeft(long timeoutSeconds) {
        long days = timeoutSeconds / 86400;
        long hours = (timeoutSeconds / 3600) % 24;
        long minutes = (timeoutSeconds / 60) % 60;
        long seconds = timeoutSeconds % 60;
        return (days > 0 ? " " + days + " day" + (days != 1 ? "s" : "") : "") + (hours > 0 ? " " + hours + " hour" + (hours != 1 ? "s" : "") : "")
                + (minutes > 0 ? " " + minutes + " minute" + (minutes != 1 ? "s" : "") : "") + (seconds > -1 ? " " + seconds + " second" + (seconds != 1 ? "s" : "") : "");
    }

}
