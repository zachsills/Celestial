package me.hulipvp.celestial.api.manager;

import me.hulipvp.celestial.api.object.CelestialObject;

import java.util.Collection;
import java.util.UUID;

/**
 * The interface that will be implemented by all of our managers
 * that will handle our objects
 *
 * @param <T>
 *     the type that must be a sub class of {@link CelestialObject}
 */
public interface IManager<T extends CelestialObject> {

    T get(final UUID uuid);

    T get(final String name);

    T remove(final UUID uuid);

    void add(final T t);

    int size();

    Collection<T> getAll();
}
