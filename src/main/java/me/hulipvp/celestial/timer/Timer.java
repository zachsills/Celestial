package me.hulipvp.celestial.timer;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 *
 * Our main {@link Timer} class that will be used as our parent class
 * for our player and server timers
 *
 */
public abstract class Timer {

    @Getter private final UUID uuid;

    @Getter @Setter private long time;
    @Getter @Setter private boolean paused;

    /**
     * The super constructor for our wittle tiny child {@link Timer} classes
     *
     * @param time
     *          the length, in milliseconds, for our {@link Timer} to run for
     */
    public Timer(final long time) {
        this.uuid = UUID.randomUUID();
        this.time = time;
    }
}
