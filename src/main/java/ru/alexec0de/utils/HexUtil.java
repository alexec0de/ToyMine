package ru.alexec0de.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexUtil {

    public static String translate(String message) {
        Pattern pattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String color = matcher.group(1);
            String replacement = ChatColor.of("#" + color).toString();
            message = message.replace("&#" + color, replacement);
        }
        message = message.replace("\n", "\n");
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
