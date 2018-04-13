package me.hulipvp.celestial.api.object;

import org.bson.Document;

/**
 *
 * The interface for all of the objects that we will be storing
 * into the database
 *
 */
public interface IObject {

    Document toDocument();

    void fromDocument(final Document document);
}
