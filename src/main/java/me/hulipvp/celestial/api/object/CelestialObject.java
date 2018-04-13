package me.hulipvp.celestial.api.object;

import lombok.Getter;
import org.bson.Document;

import java.util.UUID;

/**
 *
 * The main class for all of our objects that we wish to store inside
 * of our database
 * <p>
 * Basically this class only has one attribute, and that is the UUID and
 * that is because I don't want to create a UUID attribute for every single
 * object and that just feels quite redundant to me--and I'm lazy
 *
 */
public abstract class CelestialObject implements IObject {

    @Getter public final UUID uuid;

    /**
     * Construct a new {@link CelestialObject}
     *
     * @param uuid
     *          the uuid of the new obj
     */
    public CelestialObject(final UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Get the obj's UUID as a string
     *
     * @return
     *      the UUID converted to a string
     */
    public String getUuidString() {
        return uuid.toString();
    }

    /* Gotta love those equal methods */
    public boolean equals(final UUID uuid) {
        return equals(uuid.toString());
    }

    public boolean equals(final String string) {
        return this.uuid.toString().equals(string);
    }

    @Override
    public Document toDocument() {
        return new Document().append("uuid", getUuidString());
    }

    @Override
    public boolean equals(final Object obj) {
        if(!(obj instanceof CelestialObject))
            return false;

        final CelestialObject otherObj = (CelestialObject) obj;
        return otherObj.equals(uuid);
    }
}
