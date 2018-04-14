package me.hulipvp.celestial.factions;

import lombok.Getter;
import lombok.Setter;
import me.hulipvp.celestial.api.object.CelestialObject;
import me.hulipvp.celestial.factions.claim.Claim;
import me.hulipvp.celestial.factions.type.FactionType;
import me.hulipvp.celestial.util.LocationUtils;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 *
 * The parent class for all of the {@link Faction} types
 * that will be stored on the server
 *
 */
public abstract class Faction extends CelestialObject {

    @Getter private FactionType type;

    @Getter @Setter private String name;
    @Getter @Setter private Claim claim;
    @Getter @Setter private Location home;

    public Faction(final UUID uuid, final FactionType type, final String name) {
        super(getFinalUuid(uuid));

        this.type = type;
        this.name = name;

        FactionManager.get().add(this);
    }

    /**
     * Get an {@link UUID} to identify this Faction
     *
     * @param uuid
     *          the uuid that was previously used for the Faction, if the Faction
     *          was created before
     * @return
     *      a final UUID which will be used to identify this Faction
     */
    public static UUID getFinalUuid(final UUID uuid) {
        UUID finalUuid;
        if(uuid == null) {
            do {
                finalUuid = UUID.randomUUID();
            } while(FactionManager.get().get(finalUuid) != null);
        } else {
            finalUuid = uuid;
        }

        return finalUuid;
    }

    public abstract void show(final Player player);

    public Document toDocument() {
        final Document document = super.toDocument()
                .append("name", name)
                .append("type", type.name().toLowerCase());

        if(home != null)
            document.append("home", home.toString());

        if(claim != null)
            document.append("claim", claim.toString());

        return document;
    }

    public void fromDocument(final Document document) {
        name = document.getString("name");
        type = FactionType.valueOf(document.getString("type").toUpperCase());

        if(document.get("home") != null)
            home = LocationUtils.deserializeLocation(document.getString("home"));

        if(document.get("claim") != null)
            claim = LocationUtils.claimFromString(document.getString("claim"));
    }

    @Override
    public boolean equals(final Object obj) {
        if(!(obj instanceof Faction))
            return false;

        final Faction faction = (Faction) obj;
        return super.equals(obj) && faction.getType() == type;
    }
}
