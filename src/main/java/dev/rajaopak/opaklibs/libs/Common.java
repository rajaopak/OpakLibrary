package dev.rajaopak.opaklibs.libs;

import dev.rajaopak.opaklibs.OpakLibs;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * modified by rajaopak
 * @author aglerr
 */
public class Common {

    public static String PREFIX = "[OpakLibs]";
    public static boolean USE_PREFIX = true;

    private static final String nmsVersion;

    static {
        nmsVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];
    }

    public static void setPrefix(String prefix) {
        PREFIX = prefix;
    }

    public static void usePrefix(boolean usePrefix) {
        USE_PREFIX = usePrefix;
    }

    public static boolean getPrefixStatus() {
        return USE_PREFIX;
    }

    public static String getPrefix() {
        if (USE_PREFIX)
            return color(PREFIX) + " ";
        return "";
    }

    /**
     * @param sender   The sender of the command
     * @param messages The messages to send
     *                 This method will send the messages to the sender without the prefix
     */
    public static void sendMessage(CommandSender sender, List<String> messages) {
        messages.forEach(message -> sendMessage(sender, message));
    }

    /**
     * @param sender    The sender of the command
     * @param messages  The messages to send
     *                  This method will send the messages to the sender with the prefix
     */
    public static void sendMessage(CommandSender sender, List<String> messages, boolean usePrefix) {
        messages.forEach(message -> sendMessage(sender, message, usePrefix));
    }

    /**
     * @param sender    The sender of the Command
     * @param component The component to send
     *                  This method will send the TextComponent to the sender
     */
    public static void sendMessage(CommandSender sender, TextComponent component) {
        sender.spigot().sendMessage(component);
    }

    /**
     * @param sender  The sender of the command
     * @param message The message to send
     *                This method will send the message to the sender.
     */
    public static void sendMessage(CommandSender sender, String message) {
        sendMessage(sender, message, false);
    }

    /**
     * @param sender    The sender of the command
     * @param message   The message to send
     * @param usePrefix Whether to use the prefix or not
     *                  This method will send the message to the sender with the prefix
     */
    public static void sendMessage(CommandSender sender, String message, boolean usePrefix) {
        if(message.isEmpty() || message.equalsIgnoreCase("null")){
            return;
        }

        if(sender instanceof Player && OpakLibs.PLACEHOLDER_API){
            if (usePrefix) {
                sender.sendMessage(PlaceholderAPI.setPlaceholders((Player) sender, color(getPrefix() + message)));
                return;
            }
            sender.sendMessage(PlaceholderAPI.setPlaceholders((Player) sender, color(message)));
            return;
        }

        if (usePrefix) {
            sender.sendMessage(color(getPrefix() + message));
            return;
        }
        sender.sendMessage(color(message));
    }

    public static BaseComponent baseComponent(String text){
        return new TextComponent(color(text));
    }

    public static TextComponent textComponent(String text) {
        return new TextComponent(color(text));
    }

    public static TextComponent textComponent(String text, @Nullable ClickEvent clickEvent, @Nullable HoverEvent hoverEvent) {
        TextComponent component = textComponent(text);

        if (clickEvent != null) {
            component.setClickEvent(clickEvent);
        }

        if (hoverEvent != null) {
            component.setHoverEvent(hoverEvent);
        }

        return component;
    }

    public static List<String> color(List<String> messages) {
        return messages.stream().map(Common::color).collect(Collectors.toList());
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> tryParsePAPI(Player player, List<String> messages){
        if(OpakLibs.PLACEHOLDER_API){
            return PlaceholderAPI.setPlaceholders(player, color(messages));
        }
        return color(messages);
    }

    public static String tryParsePAPI(Player player, String message){
        if(OpakLibs.PLACEHOLDER_API){
            return PlaceholderAPI.setPlaceholders(player, color(message));
        }
        return color(message);
    }

    public static void log(String... messages) {
        for (String message : messages) {
            OpakLibs.INSTANCE.getLogger().info(color(message));
        }
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean hasCustomModelData() {
        return Bukkit.getVersion().contains("1.14") ||
                Bukkit.getVersion().contains("1.15") ||
                Bukkit.getVersion().contains("1.16") ||
                Bukkit.getVersion().contains("1.17") ||
                Bukkit.getVersion().contains("1.18") ||
                Bukkit.getVersion().contains("1.19");
    }

    public static boolean supportHexColor() {
        return Bukkit.getVersion().contains("1.16") ||
                Bukkit.getVersion().contains("1.17") ||
                Bukkit.getVersion().contains("1.18") ||
                Bukkit.getVersion().contains("1.19");
    }

    public static boolean hasOffhand() {
        return !Bukkit.getVersion().contains("1.7") &&
                !Bukkit.getVersion().contains("1.8");
    }

    public static String digits(double d){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(0);
        return numberFormat.format(d);
    }

    public static String getNmsVersion() {
        return nmsVersion;
    }

    public static String formatTime(int seconds) {
        if (seconds == 0) {
            return "0s";
        }

        long minute = seconds / 60;
        seconds = seconds % 60;
        long hour = minute / 60;
        minute = minute % 60;
        long day = hour / 24;
        hour = hour % 24;

        StringBuilder time = new StringBuilder();
        if (day != 0) {
            time.append(day).append("d ");
        }
        if (hour != 0) {
            time.append(hour).append("h ");
        }
        if (minute != 0) {
            time.append(minute).append("m ");
        }
        if (seconds != 0) {
            time.append(seconds).append("s");
        }

        return time.toString().trim();
    }
}
