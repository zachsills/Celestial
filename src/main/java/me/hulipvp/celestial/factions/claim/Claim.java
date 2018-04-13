package me.hulipvp.celestial.factions.claim;

import lombok.Getter;
import me.hulipvp.celestial.util.LocationUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * Our {@link Claim} class that will be used to claim territory for Factions
 * and detect if certain locations are inside of the {@link Claim}
 *
 */
public class Claim {

    @Getter private final Location cornerOne, cornerTwo;

    private Cuboid cuboid;

    public Claim(final Location cornerOne, final Location cornerTwo) {
        this.cornerOne = cornerOne;
        this.cornerTwo = cornerTwo;
    }

    public Cuboid getCuboid() {
        if(cuboid == null)
            cuboid = new Cuboid(cornerOne, cornerTwo);

        return cuboid;
    }

    public boolean isInside(final Location location) {
        return getCuboid().isInCuboid(location);
    }

    public boolean isInside(final Player player) {
        return getCuboid().isInCuboid(player);
    }

    @Override
    public String toString() {
        return LocationUtils.serializeLocation(cornerOne) + ":" + LocationUtils.serializeLocation(cornerTwo);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Claim))
            return false;

        final Claim otherClaim = (Claim) obj;
        return otherClaim.getCornerOne() == cornerOne && otherClaim.getCornerTwo() == cornerTwo;
    }
}
