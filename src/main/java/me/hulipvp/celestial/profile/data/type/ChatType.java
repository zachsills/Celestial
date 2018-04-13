package me.hulipvp.celestial.profile.data.type;

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
public enum ChatType {

    PUBLIC("Global", ChatColor.GREEN, "p", "g", "public", "global"),
    FACTION("Faction", ChatColor.RED, "f", "t", "fac", "faction", "team"),
    ALLY("Ally", ChatColor.LIGHT_PURPLE, "a", "ally");

    @Getter private final String friendlyName;
    @Getter private final ChatColor color;
    @Getter private final List<String> aliases;

    ChatType(final String friendlyName, final ChatColor color, final String... aliases) {
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
     * Returns the next {@link ChatType} in a sequential order
     *
     * @param mode
     *          the provided mode that will be used to find the next {@link ChatType}
     * @return
     *      the next mode that comes after the provided mode
     */
    public ChatType getNext(final ChatType mode) {
        final int modes = ChatType.values().length;

        return ChatType.values()[mode.ordinal() + 1 >= modes ? 0 : mode.ordinal() + 1];
    }

    /**
     * Return a mode that contains the given alias<br>
     * Will return <null>null</null> if no {@link ChatType} is found with
     * the given alias
     *
     * @param alias
     *          the mode you wish to find with the given alias
     * @return
     *      an {@link ChatType} if a mode with the given alias is found,
     *      otherwise null
     */
    public ChatType getByAlias(final String alias) {
        return Arrays.stream(ChatType.values())
                .filter(mode -> mode.getAliases().contains(alias))
                .findFirst()
                .orElse(null);
    }
}
