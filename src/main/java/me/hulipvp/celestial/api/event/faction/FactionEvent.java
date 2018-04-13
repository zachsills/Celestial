package me.hulipvp.celestial.api.event.faction;

import lombok.Getter;
import me.hulipvp.celestial.api.event.CelestialEvent;
import me.hulipvp.celestial.factions.Faction;

public class FactionEvent extends CelestialEvent {

    @Getter private final Faction faction;

    public FactionEvent(final Faction faction) {
        this.faction = faction;
    }
}
