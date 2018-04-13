package me.hulipvp.celestial.util;

import org.bukkit.ChatColor;

public class StringUtils {

    /**
     * Remove the chat colors from a string that has already been
     * translated
     *
     * @param string
     *          the string that we wish to remove the colors from
     * @return
     *      the string that has had all of it's chat colors removed
     */
    public static String strip(final String string) {
        return ChatColor.stripColor(string);
    }

    /**
     * Translate the color codes inside of a string and get a colored
     * string to send to a player/entity that can see messages
     *
     * @param string
     *          a string that you wish to translate all the color codes in
     * @return
     *      a string that has been 'colorized'
     */
    public static String color(final String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
