package me.hulipvp.celestial.storage.backend;

import me.hulipvp.celestial.api.object.CelestialObject;
import org.bukkit.plugin.java.JavaPlugin;

public interface IBackend<T extends CelestialObject> {

    boolean load(final JavaPlugin plugin);

    void close();

    void create(final T t);

    void save(final T t);

    void saveAsync(final T t);

    void load(final T t);

    void delete(final T t);

    void loadFactions();
}