package dev.rajaopak.opaklibrary.libs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class ChatSession implements Listener {

    private final Plugin plugin;
    private Player player;
    private Function<Complete, Action> completeActionFunction;
    private Consumer<Player> closeListener;

    public ChatSession plugin() {
        this.plugin = OpakLibrary.INSTANCE;
        return this;
    }

    public void open(Player player) {
        this.player = player;

        Bukkit.getPluginManager().registerEvents(this, this.plugin);

        ChatUtil.sendMessage(player, "&eType \"--close\" to close/cancel the session.");
        ChatUtil.sendMessage(player, "&eEnter your message in the chat.");
    }

    public ChatSession onComplete(Function<Complete, Action> completeActionFunction) {
        this.completeActionFunction = completeActionFunction;
        return this;
    }

    public ChatSession onClose(Consumer<Player> closeListener) {
        this.closeListener = closeListener;
        return this;
    }

    private void close(Player player) {
        this.player = null;
        HandlerList.unregisterAll(this);
        if (this.closeListener != null) {
            this.closeListener.accept(player);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.getPlayer() == this.player) {
            e.setCancelled(true);
            if (e.getMessage().equalsIgnoreCase("--close")) {
                this.close(e.getPlayer());
                ChatUtil.sendMessage(e.getPlayer(), "&7You have been close the message session.");
                return;
            }

            Action actions = this.completeActionFunction.apply(new Complete(e.getPlayer(), e.getFormat(), e.getMessage()));

            actions.accept(e.getPlayer(), e.getMessage());
            this.close(e.getPlayer());
        }
    }

    public interface Action extends BiConsumer<Player, String> {
        static Action run(Runnable runnable) {
            return (player1, s) -> {
                runnable.run();
            };
        }
    }

    public static class Complete {

        private final Player player;

        private final String format;
        private final String message;
        public Complete(Player player, String format, String message) {
            this.player = player;
            this.format = format;
            this.message = message;
        }

        public Player getPlayer() {
            return player;
        }

        public String getFormat() {
            return format;
        }

        public String getMessage() {
            return message;
        }
    }
}
