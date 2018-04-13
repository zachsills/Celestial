package me.hulipvp.celestial.profile;

import com.mongodb.BasicDBObject;
import lombok.Getter;
import lombok.Setter;
import me.hulipvp.celestial.Celestial;
import me.hulipvp.celestial.api.object.CelestialObject;
import me.hulipvp.celestial.factions.FactionManager;
import me.hulipvp.celestial.factions.type.player.PlayerFaction;
import me.hulipvp.celestial.profile.data.object.ProfileSetting;
import me.hulipvp.celestial.profile.data.object.ProfileStatistic;
import me.hulipvp.celestial.profile.data.type.ChatType;
import me.hulipvp.celestial.profile.data.type.SettingType;
import me.hulipvp.celestial.profile.data.type.StatisticType;
import me.hulipvp.celestial.util.StringUtils;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * Our main {@link Profile} object for storing all of our player's data as they
 * play on the server
 *
 */
public class Profile extends CelestialObject {

    @Getter @Setter private String name;

    @Getter @Setter private UUID faction;

    @Getter @Setter private int lives, balance;
    @Getter @Setter private long deathban;

    @Getter @Setter private ChatType chatType;

    @Getter private Set<ProfileSetting> settings;
    @Getter private Set<ProfileStatistic> statistics;

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
        super(uuid);

        chatType = ChatType.PUBLIC;

        settings = new HashSet<>();
        statistics = new HashSet<>();

        Celestial.getInstance().getBackend().load(this);

        if(cache)
            ProfileManager.get().add(this);
    }

    public Profile(final UUID uuid) {
        this(uuid, true);
    }

    /**
     * See whether or not this Profile currently belongs to a Faction
     *
     * @return
     *      <tt>true</tt> if the Profile's faction is an existing obj
     *      otherwise <tt>false</tt>
     */
    public boolean hasFaction() {
        return faction != null;
    }

    /**
     * Get the Profile's {@link PlayerFaction} obj to use
     * <p>
     * This will return null if the {@link FactionManager} doesn't happen
     * to store a faction with the Profile's faction UUID
     *
     * @return
     *      the Profile's {@link PlayerFaction} obj
     */
    public PlayerFaction getFactionObj() {
        return (PlayerFaction) FactionManager.get().get(faction);
    }

    /**
     * See whether or not this Profile has a setting of a specific type,
     * this is only used when the Profile is being loaded
     *
     * @param type
     *          the type of the setting we wish to find
     * @return
     *      <tt>true</tt> if the setting of a matching type was found
     *      otherwise <tt>false</tt>
     */
    public boolean hasSetting(final SettingType type) {
        return settings.stream()
                .anyMatch(setting -> setting.getType() == type);
    }

    /**
     * Get a setting of a specific type
     *
     * @param type
     *          the type of the setting we wish to find
     * @return
     *      a setting with the specified type
     */
    public ProfileSetting getSetting(final SettingType type) {
          return settings.stream()
                .filter(setting -> setting.getType() == type)
                .findFirst().get();
    }

    /**
     * See whether or not this Profile has a statistic of a specific type,
     * this is only used when the Profile is being loaded
     *
     * @param type
     *          the type of the statistic we wish to find
     * @return
     *      <tt>true</tt> if the statistic of a matching type was found
     *      otherwise <tt>false</tt>
     */
    public boolean hasStatistic(final StatisticType type) {
        return statistics.stream()
                .anyMatch(statistic -> statistic.getType() == type);
    }

    /**
     * Get a statistic of a specific type
     *
     * @param type
     *          the type of the statistic we wish to find
     * @return
     *      a statistic with the specified type
     */
    public ProfileStatistic getStatistic(final StatisticType type) {
        return statistics.stream()
                .filter(statistic -> statistic.getType() == type)
                .findFirst().get();
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
     * Forcefully save the Profile to the database
     */
    public void save() {
        Celestial.getInstance().getBackend().saveAsync(this);
    }

    /**
     * Send a message to the {@link Player} that owns this profile
     * <br>
     * The message translates chat color codes before sending the message
     * to the player so no need to translate the thing
     *
     * @param message
     *          the message you wish to send do the {@link Player}
     */
    public void sendMessage(final String message) {
        if(getPlayer() == null)
            return;

        getPlayer().sendMessage(StringUtils.color(message));
    }

    /**
     * Return a {@link Document} with all of the {@link Profile}'s attributes we wish to
     * store
     *
     * @return
     *      the {@link Document} with all of the {@link Profile}'s useful information
     */
    @Override
    public Document toDocument() {
        final Document document = super.toDocument()
                .append("name", name)
                .append("lives", lives)
                .append("balance", balance)
                .append("deathban", deathban);

        if(faction != null)
            document.append("faction", faction.toString());

        if(!settings.isEmpty()) {
            final BasicDBObject settings = new BasicDBObject();
            for(final SettingType type : SettingType.values())
                settings.append(type.getKey(), getSetting(type).getValue());

            document.append("settings", settings);
        }

        if(!statistics.isEmpty()) {
            final BasicDBObject statistics = new BasicDBObject();
            for(final StatisticType type : StatisticType.values())
                statistics.append(type.getKey(), getStatistic(type).getValue());

            document.append("stats", statistics);
        }

        return document;
    }

    /**
     * Load all of the Profile's important attributes that were stored
     * into the database
     *
     * @param document
     *          the document with all of the Profile's important information
     */
    @Override
    public void fromDocument(final Document document) {
        lives = document.getInteger("lives");
        balance = document.getInteger("balance");
        deathban = document.getLong("deathban");

        if(document.get("faction") != null)
            faction = UUID.fromString(document.getString("faction"));

        if(document.get("settings") != null) {
            final Document settings = (Document) document.get("settings");
            for(final String key : settings.keySet()) {
                final boolean value = settings.getBoolean(key);
                final SettingType type = SettingType.getByKey(key);

                this.settings.add(new ProfileSetting(type, value));
            }
        }

        if(document.get("stats") != null) {
            final Document statistics = (Document) document.get("stats");
            for(final String key : statistics.keySet()) {
                final int value = statistics.getInteger(key);
                final StatisticType type = StatisticType.getByKey(key);

                this.statistics.add(new ProfileStatistic(type, value));
            }
        }

        for(final SettingType type : SettingType.values()) {
            if(!hasSetting(type))
                settings.add(new ProfileSetting(type));
        }

        for(final StatisticType type : StatisticType.values()) {
            if(!hasStatistic(type))
                statistics.add(new ProfileStatistic(type));
        }
    }
}
