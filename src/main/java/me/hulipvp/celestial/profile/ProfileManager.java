package me.hulipvp.celestial.profile;

import me.hulipvp.celestial.Celestial;
import me.hulipvp.celestial.api.manager.CelestialManager;
import me.hulipvp.celestial.profile.data.type.SettingType;
import me.hulipvp.celestial.util.PlayerUtils;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ProfileManager extends CelestialManager<Profile> {

    /**
     * Just for quicker access as I'm too lazy to access it from the
     * main class
     *
     * @return
     *      the ProfileManager instance we created when the plugin enabled
     */
    public static ProfileManager get() {
        return Celestial.getInstance().getProfileManager();
    }

    /**
     * Get a Profile by the provided UUID
     *
     * @param uuid
     *          the UUID of the profile you wish to find
     * @return
     *      a Profile with the matching UUID
     */
    @Override
    public Profile get(final UUID uuid) {
        if(getStored().containsKey(uuid.toString()))
            return getStored().get(uuid.toString());

        return new Profile(uuid);
    }

    /**
     * Get a Profile by a name
     * <p>
     * If no UUID is found with the provided UUID, then this
     * will return <tt>null</tt>
     *
     * @param name
     *          the name of the Profile you wish to find
     * @return
     *      a Profile where the UUID of the name matches the Profile
     */
    @Override
    public Profile get(final String name) {
        final UUID uuid = PlayerUtils.getUUIDof(name);
        if(uuid == null)
            return null;

        return get(uuid);
    }

    /**
     * A quicker way of getting a Profile instead by just
     * getting the Player's UUID
     *
     * @param player
     *          the Player whose Profile we wish to find
     * @return
     *      the Profile of the Player
     */
    public Profile get(final Player player) {
        return get(player.getUniqueId());
    }

    /**
     * Send a message to all of the stored {@link Profile}s on
     * the server
     *
     * @param message
     *          the message we wish to broadcast to every {@link Profile}
     */
    public void sendAll(final String message) {
        filterAndPerform(profile -> profile.getSetting(SettingType.PUBLIC_CHAT).getValue(), profile -> profile.sendMessage(message));
    }
}
