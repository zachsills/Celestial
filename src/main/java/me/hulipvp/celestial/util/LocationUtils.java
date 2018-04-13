package me.hulipvp.celestial.util;

import me.hulipvp.celestial.factions.claim.Claim;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    /**
     * String concatenation ultimately compiles out to a StringBuilder
     * so we're going to be using a string builder instead of the regular
     * string concatenation (+=)
     * <p>
     * Also for those of you asking why not a <tt>StringBuffer</tt>? Well,
     * a <tt>StringBuffer</tt> is synchronized while a StringBuilder is not.
     * So using a StringBuilder is more efficient in a Minecraft plugin as we're
     * only using a single thread and not trying to share a Buffer across multiple
     * threads
     *
     * @param location
     *          the location that we wish to turn into human readable
     *          form
     * @return
     *      a string with the location's attributes in human-readable form
     */
    public static String serializeLocation(final Location location) {
        return new StringBuilder()
                .append("@w;" + location.getWorld().getName())
                .append(":@x;" + location.getX())
                .append(":@x;" + location.getX())
                .append(":@y;" + location.getY())
                .append(":@z;" + location.getZ())
                .append(":@p;" + location.getPitch())
                .append(":@ya;" + location.getYaw())
                .toString();
    }

    /**
     * Get a location from a String
     * <p>
     * If the provided string is an invalid location string, then a location
     * with preset values of 0 for all attributes will be returned
     *
     * @param string
     *          the string that we wish to convert into a location
     * @return
     *      the location that was read from the provided string
     */
    public static Location deserializeLocation(final String string) {
        final Location location = new Location(Bukkit.getWorlds().get(0), 0.0D, 0.0D, 0.0D);
        final String[] arr = string.split(":");
        final int len = arr.length;

        for(int i = 0; i < len; ++i) {
            final String attribute = arr[i];
            final String[] split = attribute.split(";");
            if(split[0].equalsIgnoreCase("@w"))
                location.setWorld(Bukkit.getWorld(split[1]));

            if(split[0].equalsIgnoreCase("@x"))
                location.setX(Double.parseDouble(split[1]));

            if(split[0].equalsIgnoreCase("@y"))
                location.setY(Double.parseDouble(split[1]));

            if(split[0].equalsIgnoreCase("@z"))
                location.setZ(Double.parseDouble(split[1]));

            if(split[0].equalsIgnoreCase("@p"))
                location.setPitch(Float.parseFloat(split[1]));

            if(split[0].equalsIgnoreCase("@ya"))
                location.setYaw(Float.parseFloat(split[1]));
        }

        return location;
    }

    /**
     * Get a Claim from a String
     *
     * @param string
     *          the string which contains the two corners of the claim
     * @return
     *      a new Claim object that was constructed from the two locations of
     *      the string
     */
    public static Claim claimFromString(final String string) {
        final String[] split = string.split(":");
        final Location cornerOne = LocationUtils.deserializeLocation(split[0]);
        final Location cornerTwo = LocationUtils.deserializeLocation(split[1]);

        return new Claim(cornerOne, cornerTwo);
    }
}
