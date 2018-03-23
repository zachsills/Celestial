package me.hulipvp.celestial.factions;

import lombok.Getter;
import lombok.Setter;
import me.hulipvp.celestial.factions.claim.Claim;
import me.hulipvp.celestial.factions.type.FactionType;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * The parent class for all of the {@link Faction} types
 * that will be stored on the server
 *
 */
public abstract class Faction {

    @Getter private static final Map<String, Faction> factions = new HashMap<>();

    @Getter private final UUID uuid;
    @Getter private final FactionType type;

    @Getter @Setter private String name;
    @Getter @Setter private Claim claim;
    @Getter @Setter private Location home;

    public Faction(final UUID uuid, final FactionType type, final String name) {
        UUID finalUuid;
        if(uuid == null) {
            do {
                finalUuid = UUID.randomUUID();
            } while(getFaction(finalUuid) != null);
        } else {
            finalUuid = uuid;
        }
        this.uuid = finalUuid;

        this.type = type;
        this.name = name;
    }

    public static Faction getFaction(final UUID uuid) {
        return factions.get(uuid.toString());
    }

    public static Faction getFaction(final String name) {
        return factions.values().stream()
                .filter(faction -> faction.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public abstract void show(final Player player);

    public Document toDocument() {
        return new Document()
                .append("uuid", uuid)
                .append("type", type.name())
                .append("name", name)
                .append("claim", claim != null ? claim.toString() : null)
                .append("home", home != null ? home.toString() : null);
    }

    public void fromDocument(final Document document) {

    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Faction))
            return false;

        final Faction faction = (Faction) obj;
        return faction.getType() == type && faction.getUuid().toString().equals(uuid.toString());
    }
}
