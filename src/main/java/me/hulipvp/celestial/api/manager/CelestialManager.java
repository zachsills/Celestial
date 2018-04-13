package me.hulipvp.celestial.api.manager;

import lombok.Getter;
import me.hulipvp.celestial.api.object.CelestialObject;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Our main class that will handle all of our {@link CelestialObject}s
 *
 * @param <T>
 *     the type that must be a sub class of {@link CelestialObject}
 */
public abstract class CelestialManager<T extends CelestialObject> implements IManager<T> {

    @Getter private Map<String, T> stored;

    public CelestialManager() {
        stored = new HashMap<>();
    }

    /**
     * Perform an operation on every stored element of type T
     *
     * @param consumer
     *          the operation we want each element to perform
     */
    public void forEach(final Consumer<T> consumer) {
        for(final T t : getAll())
            consumer.accept(t);
    }

    /**
     * Filter the stored elements and then return a Stream of
     * those elements
     *
     * @param predicate
     *          the attribute we wish to check for every single element
     * @return
     *      a Stream of all the elements that surpassed the predicate
     *      check
     */
    protected Stream<T> filterQuickly(final Predicate<T> predicate) {
        return stored.values().stream()
                .filter(predicate);
    }

    /**
     * Quickly filter all of the stored elements and make them perform
     * an operation
     *
     * @param predicate
     *          the attribute we wish to check for every single element
     * @param consumer
     *          the operation we want each element to perform
     */
    protected void filterAndPerform(final Predicate<T> predicate, final Consumer<T> consumer) {
        filterQuickly(predicate).forEach(consumer);
    }

    /**
     * Remove and then return a stored value to manipulate it in whatever
     * way you choose
     *
     * @param uuid
     *          the uuid of the obj we wish to remove
     * @return
     *      a previously stored value what we removed
     */
    @Override
    public T remove(final UUID uuid) {
        return getStored().remove(uuid.toString());
    }

    /**
     * Get an <tt>unmodifiable</tt> of all the elements that were stored
     *
     * @return
     *      an unmodifiable collection so that you can get a "read-only"
     *      view of all of the stored elements
     */
    @Override
    public Collection<T> getAll() {
        return Collections.unmodifiableCollection(stored.values());
    }

    /**
     * Add an element to the list
     * <p>
     * If the provided type is null, then nothing will happen to prevent
     * <tt>Map#put()</tt> from throwing a null pointer
     *
     * @param t
     *      the element we wish to store
     */
    @Override
    public void add(final T t) {
        if(t == null)
            return; // Prevent put() call throwing NullPointer

        stored.put(t.getUuid().toString(), t);
    }

    /**
     * Get the amount of elements stored in this manager
     *
     * @return
     *      an integer of the amount of elements currently stored
     */
    @Override
    public int size() {
        return stored.size();
    }
}
