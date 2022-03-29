package id.universemc.universelibs.universelibs.libs;

import de.themoep.minedown.MineDown;
import id.universemc.universelibs.universelibs.UniverseLibs;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * modified by rajaopak
 * @author aglerr
 */
public class Common {

    public static String PREFIX = "[UniverseLibs]";
    public static boolean USE_PREFIX = true;

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
            return PREFIX + " ";
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

        if(sender instanceof Player && UniverseLibs.PLACEHOLDER_API){
            if (usePrefix)
                sender.sendMessage(PlaceholderAPI.setPlaceholders((Player) sender, color(getPrefix()+ message)));
            sender.sendMessage(PlaceholderAPI.setPlaceholders((Player) sender, color(message)));
            return;
        }

        if (usePrefix)
            sender.sendMessage(color(getPrefix()+ message));
        sender.sendMessage(color(message));
    }

    public static List<String> color(List<String> messages) {
        return messages.stream().map(Common::color).collect(Collectors.toList());
    }

    public static String color(String message) {
        return BaseComponent.toLegacyText(MineDown.parse(message));
    }

    public static List<String> tryParsePAPI(Player player, List<String> messages){
        if(UniverseLibs.PLACEHOLDER_API){
            return PlaceholderAPI.setPlaceholders(player, color(messages));
        }
        return color(messages);
    }

    public static String tryParsePAPI(Player player, String message){
        if(UniverseLibs.PLACEHOLDER_API){
            return PlaceholderAPI.setPlaceholders(player, color(message));
        }
        return color(message);
    }

    public static void log(String... messages) {
        for (String message : messages) {
            Bukkit.getConsoleSender().sendMessage(
                    getPrefix() + color("&r" + message)
            );
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
                Bukkit.getVersion().contains("1.18");
    }

    public static boolean supportHexColor() {
        return Bukkit.getVersion().contains("1.16") ||
                Bukkit.getVersion().contains("1.17") ||
                Bukkit.getVersion().contains("1.18");
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

}
