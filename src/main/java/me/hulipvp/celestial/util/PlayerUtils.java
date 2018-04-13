package me.hulipvp.celestial.util;

import me.hulipvp.celestial.util.api.player.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerUtils {

    public static UUID getUUIDof(final String name) {
        final Player player = Bukkit.getPlayer(name);
        if(player != null)
            return player.getUniqueId();

        final UUID uuid = UUIDFetcher.getUUID(name);
        return uuid;
    }

    public static boolean isValidPlayer(final String name) {
        final UUID uuid = UUIDFetcher.getUUID(name);
        if(uuid != null)
            return Bukkit.getOfflinePlayer(uuid).hasPlayedBefore();

        return false;
    }
}
