package me.hulipvp.celestial.profile;

import lombok.Getter;
import lombok.Setter;
import me.hulipvp.celestial.profile.data.ChatMode;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * Our main {@link Profile} object for storing all of our player's data as they
 * play on the server
 *
 */
public class Profile {

    @Getter private static final Map<String, Profile> profiles = new HashMap<>();

    @Getter private final UUID uuid;

    @Getter @Setter private String name;
    @Getter @Setter private UUID faction;
    @Getter @Setter private int lives, balance;
    @Getter @Setter private long deathban;
    @Getter @Setter private ChatMode chatMode;

    /**
     * Construct a new {@link Profile} instance
     *
     * @param uuid
     *          the {@link UUID} of the player
     * @param cache
     *          whether or not we want to store the new {@link Profile}, mostly for just if we want to check a singular attribute
     *          such as the deathban or amount of lives on {@link Player}'s initial connection
     */
    public Profile(final UUID uuid, final boolean cache) {
        this.uuid = uuid;

        this.chatMode = ChatMode.PUBLIC;

        if(cache)
            profiles.put(uuid.toString(), this);
    }

    /**
     * Return the {@link Player} object from this Profile's {@link UUID}
     *
     * @return
     *      the {@link Player} object retrieved from the uuid
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    /**
     * Return a {@link Document} with all of the {@link Profile}'s attributes we wish to
     * store
     *
     * @return
     *      the {@link Document} with all of the {@link Profile}'s useful information
     */
    public Document toDocument() {
        return new Document()
                .append("uuid", uuid.toString())
                .append("name", name)
                .append("faction", faction != null ? faction.toString() : null)
                .append("lives", lives)
                .append("balance", balance)
                .append("deathban", deathban);
    }

    public void fromDocument(final Document document) {
        this.faction = document.getString("faction") != null ? UUID.fromString(document.getString("faction")) : null;
        this.lives = document.getInteger("lives");
        this.balance = document.getInteger("balance");
        this.deathban = document.getLong("deathban");
    }
}
