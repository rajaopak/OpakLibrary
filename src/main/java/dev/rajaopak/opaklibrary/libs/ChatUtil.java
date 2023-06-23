package dev.rajaopak.opaklibrary.libs;

import com.google.common.base.Preconditions;
import dev.rajaopak.opaklibrary.OpakLibrary;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static net.md_5.bungee.api.ChatColor.COLOR_CHAR;

public class ChatUtil {

    public static final Pattern HEX_PATTERN = Pattern.compile("&#(\\w{5}[0-9a-f])");
    private static final Pattern START_WITH_COLORS =
            Pattern.compile("(?i)^(" + COLOR_CHAR + "[0-9A-FK-ORX])+");
    private static final Pattern NEWLINE_PATTERN = Pattern.compile("\\\\n|\\{nl}");
    private static final Pattern COLOR = Pattern.compile("(?i)" + COLOR_CHAR + "[0-9A-FK-ORX]");

    public static String color(String text) {
        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(
                    buffer,
                    COLOR_CHAR
                            + "x"
                            + COLOR_CHAR
                            + group.charAt(0)
                            + COLOR_CHAR
                            + group.charAt(1)
                            + COLOR_CHAR
                            + group.charAt(2)
                            + COLOR_CHAR
                            + group.charAt(3)
                            + COLOR_CHAR
                            + group.charAt(4)
                            + COLOR_CHAR
                            + group.charAt(5));
        }

        return ChatColor.translateAlternateColorCodes(
                '&', matcher.appendTail(buffer).toString());
    }

    public static TextComponent colors(String text) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text);
    }

    public static TextComponent colors(TextComponent text) {
        return text.content(LegacyComponentSerializer.legacyAmpersand().deserialize(text.content()).content());
    }

    public static List<String> color(List<String> text) {
        return text.stream().map(ChatUtil::color).collect(Collectors.toList());
    }

    public static List<TextComponent> colors(List<String> text) {
        return text.stream().map(ChatUtil::colors).collect(Collectors.toList());
    }

    public static List<TextComponent> colorss(List<TextComponent> text) {
        return text.stream().map(ChatUtil::colors).collect(Collectors.toList());
    }

    public static void sendMessage(CommandSender sender, String msg) {
        sender.sendMessage(color(msg));
    }

    public static void sendMessage(Audience sender, TextComponent component) {
        sender.sendMessage(component);
    }

    public static void sendMessage(CommandSender sender, List<String> msg) {
        for (String text : color(msg)) {
            sender.sendMessage(text);
        }
    }

    public static void sendMessage(Audience sender, List<String> msg) {
        for (TextComponent text : colors(msg)) {
            sender.sendMessage(text);
        }
    }

    public static void sendMessagee(Audience sender, List<TextComponent> msg) {
        for (TextComponent text : colorss(msg)) {
            sender.sendMessage(text);
        }
    }

    public static void info(String msg) {
        OpakLibrary.getInstance().getLogger().info(color(msg));
    }

    public static void logSevere(String msg) {
        OpakLibrary.getInstance().getLogger().severe(msg);
    }

    public static void logSevere(String msg, Throwable throwable) {
        OpakLibrary.getInstance().getLogger().log(Level.SEVERE, msg, throwable);
    }

    public static List<String> wordWrap(@Nullable String rawString, int lineLength) {
        // A null string is a single line
        if (rawString == null) {
            return Collections.emptyList();
        }

        // A string shorter than the lineWidth is a single line
        if (rawString.length() <= lineLength && !rawString.contains("\n")) {
            return Collections.singletonList(rawString);
        }

        char[] rawChars = (rawString + ' ').toCharArray(); // add a trailing space to trigger pagination
        StringBuilder word = new StringBuilder();
        StringBuilder line = new StringBuilder();
        List<String> lines = new LinkedList<>();
        int lineColorChars = 0;
        String lastColor = "";

        for (int i = 0; i < rawChars.length; i++) {
            char c = rawChars[i];

            // skip chat color modifiers
            if (c == COLOR_CHAR || c == '&') {
                word.append(ChatColor.getByChar(rawChars[i + 1]));
                lineColorChars += 2;
                lastColor = ChatColor.getByChar(rawChars[i + 1]).toString();
                i++; // Eat the next character as we have already processed it
                continue;
            }

            if (c == ' ' || c == '\n') {

                if (line.length() == 0 && word.length() > lineLength) { // special case: extremely long word begins a line
                    for (String partialWord : word.toString().split("(?<=\\G.{" + lineLength + "})")) {
                        lines.add(partialWord);
                    }
                } else if (line.length() + 1 + word.length() - lineColorChars == lineLength) { // Line exactly the correct length...newline
                    if (line.length() > 0) {
                        line.append(' ');
                    }
                    line.append(lastColor).append(word);
                    lines.add(line.toString());
                    line = new StringBuilder();
                    lineColorChars = 0;
                } else if (line.length() + 1 + word.length() - lineColorChars > lineLength) { // Line too long...break the line
                    for (String partialWord : word.toString().split("(?<=\\G.{" + lineLength + "})")) {
                        lines.add(line.toString());
                        line = new StringBuilder(partialWord);
                    }
                    lineColorChars = 0;
                } else {
                    if (line.length() > 0) {
                        line.append(' ');
                    }
                    line.append(word);
                }
                word = new StringBuilder();

                if (c == '\n') { // Newline forces the line to flush
                    lines.add(line.toString());
                    line = new StringBuilder();
                }
            } else {
                word.append(c);
            }
        }

        if (line.length() > 0) {
            lines.add(line.toString());
        }

        for (int i = 1; i < lines.size(); i++) {
            final String pLine = lines.get(i - 1);
            final String subLine = lines.get(i);

            String color = org.bukkit.ChatColor.getLastColors(pLine);
            lines.set(i, color + subLine);
        }

        return lines;
    }

    public static String rgbGradient(String str, Color from, Color to, Interpolator interpolator) {

        // interpolate each component separately
        final double[] red = interpolator.interpolate(from.getRed(), to.getRed(), str.length());
        final double[] green = interpolator.interpolate(from.getGreen(), to.getGreen(), str.length());
        final double[] blue = interpolator.interpolate(from.getBlue(), to.getBlue(), str.length());

        final StringBuilder builder = new StringBuilder();

        // create a string that matches the input-string but has
        // the different color applied to each char
        for (int i = 0; i < str.length(); i++) {
            builder.append(
                            ChatColor.of(
                                    new Color(
                                            (int) Math.round(red[i]),
                                            (int) Math.round(green[i]),
                                            (int) Math.round(blue[i]))))
                    .append(str.charAt(i));
        }

        return builder.toString();
    }

    public static String hsvGradient(String str, Color from, Color to, Interpolator interpolator) {
        // returns a float-array where hsv[0] = hue, hsv[1] = saturation, hsv[2] = value/brightness
        final float[] hsvFrom = Color.RGBtoHSB(from.getRed(), from.getGreen(), from.getBlue(), null);
        final float[] hsvTo = Color.RGBtoHSB(to.getRed(), to.getGreen(), to.getBlue(), null);

        final double[] h = interpolator.interpolate(hsvFrom[0], hsvTo[0], str.length());
        final double[] s = interpolator.interpolate(hsvFrom[1], hsvTo[1], str.length());
        final double[] v = interpolator.interpolate(hsvFrom[2], hsvTo[2], str.length());

        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            builder
                    .append(
                            ChatColor.of(
                                    Color.getHSBColor((float) h[i], (float) s[i], (float) v[i])))
                    .append(str.charAt(i));
        }

        return builder.toString();
    }

    public static String multiRgbGradient(
            String str, Color[] colors, double @Nullable [] portions, Interpolator interpolator) {
        final double[] p;
        if (portions == null) {
            p = new double[colors.length - 1];
            Arrays.fill(p, 1 / (double) p.length);
        } else {
            p = portions;
        }

        Preconditions.checkArgument(colors.length >= 2);
        Preconditions.checkArgument(p.length == colors.length - 1);

        final StringBuilder builder = new StringBuilder();
        int strIndex = 0;

        for (int i = 0; i < colors.length - 1; i++) {
            builder.append(
                    rgbGradient(
                            str.substring(strIndex, strIndex + (int) (p[i] * str.length())),
                            colors[i],
                            colors[i + 1],
                            interpolator));
            strIndex += p[i] * str.length();
        }
        return builder.toString();
    }

    public static String multiHsvQuadraticGradient(String str, boolean first) {
        final StringBuilder builder = new StringBuilder();

        builder.append(
                hsvGradient(
                        str.substring(0, (int) (0.2 * str.length())),
                        Color.RED,
                        Color.GREEN,
                        (from, to, max) -> quadratic(from, to, max, first)));

        for (int i = (int) (0.2 * str.length()); i < (int) (0.8 * str.length()); i++) {
            builder.append(ChatColor.of(Color.GREEN)).append(str.charAt(i));
        }

        builder.append(
                hsvGradient(
                        str.substring((int) (0.8 * str.length())),
                        Color.GREEN,
                        Color.RED,
                        (from, to, max) -> quadratic(from, to, max, !first)));

        return builder.toString();
    }

    public static double[] linear(double from, double to, int max) {
        final double[] res = new double[max];
        for (int i = 0; i < max; i++) {
            res[i] = from + i * ((to - from) / (max - 1));
        }
        return res;
    }

    /**
     * mode == true: starts of "slow" and becomes "faster", see the orange curve mode == false: starts
     * of "fast" and becomes "slower", see the yellow curve
     *
     * @param from
     * @param to
     * @param max
     * @param mode the mode
     * @return double[] result of quadratic calculation
     */
    public static double[] quadratic(double from, double to, int max, boolean mode) {
        final double[] results = new double[max];
        if (mode) {
            double a = (to - from) / (max * max);
            for (int i = 0; i < results.length; i++) {
                results[i] = a * i * i + from;
            }
        } else {
            double a = (from - to) / (max * max);
            double b = -2 * a * max;
            for (int i = 0; i < results.length; i++) {
                results[i] = a * i * i + b * i + from;
            }
        }
        return results;
    }

    @FunctionalInterface
    public interface Interpolator {
        double[] interpolate(double from, double to, int max);
    }
}
