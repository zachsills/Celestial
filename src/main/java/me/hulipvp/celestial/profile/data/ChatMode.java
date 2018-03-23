package me.hulipvp.celestial.profile.data;

import lombok.Getter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

/**
 *
 * This will be used to handle what chat the player will be
 * talking in
 *
 */
public enum ChatMode {

    PUBLIC("Global", ChatColor.GREEN, "p", "g", "public", "global"),
    FACTION("Faction", ChatColor.RED, "f", "t", "fac", "faction", "team"),
    ALLY("Ally", ChatColor.LIGHT_PURPLE, "a", "ally");

    @Getter private final String friendlyName;
    @Getter private final ChatColor color;
    @Getter private final List<String> aliases;

    ChatMode(final String friendlyName, final ChatColor color, final String... aliases) {
        this.friendlyName = friendlyName;
        this.color = color;
        this.aliases = Arrays.asList(aliases);
    }

    /**
     * Return the display of the mode so it looks nice in chat
     *
     * @return
     *      a String with the <code>color</code> and <code>friendlyName</code> of the mode
     */
    public String getDisplay() {
        return color + friendlyName.toLowerCase();
    }

    /**
     * Returns the chat friendly prefix of the mode
     *
     * @return
     *      a String that has the <code>color</code> and capitalized <code>friendlyName</code>
     *      of the mode
     */
    public String getPrefix() {
        return color + "(" + WordUtils.capitalize(friendlyName) + ") ";
    }

    /**
     * Returns the next {@link ChatMode} in a sequential order
     *
     * @param mode
     *          the provided mode that will be used to find the next {@link ChatMode}
     * @return
     *      the next mode that comes after the provided mode
     */
    public ChatMode getNext(final ChatMode mode) {
        final int modes = ChatMode.values().length;

        return ChatMode.values()[mode.ordinal() + 1 >= modes ? 0 : mode.ordinal() + 1];
    }

    /**
     * Return a mode that contains the given alias<br>
     * Will return <null>null</null> if no {@link ChatMode} is found with
     * the given alias
     *
     * @param alias
     *          the mode you wish to find with the given alias
     * @return
     *      an {@link ChatMode} if a mode with the given alias is found,
     *      otherwise null
     */
    public ChatMode getByAlias(final String alias) {
        return Arrays.stream(ChatMode.values())
                .filter(mode -> mode.getAliases().contains(alias))
                .findFirst()
                .orElse(null);
    }
}
