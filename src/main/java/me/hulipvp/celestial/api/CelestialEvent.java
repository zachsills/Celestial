package me.hulipvp.celestial.api;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * Our main {@link Event} class so we don't have to keep creating the
 * redundant annoying {@link HandlerList} every time
 *
 */
public abstract class CelestialEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
