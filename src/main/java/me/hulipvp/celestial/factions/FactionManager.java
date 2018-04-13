package me.hulipvp.celestial.factions;

import lombok.Getter;
import lombok.Setter;
import me.hulipvp.celestial.Celestial;
import me.hulipvp.celestial.factions.type.FactionType;
import me.hulipvp.celestial.factions.type.player.PlayerFaction;
import me.hulipvp.celestial.factions.type.system.SystemFaction;
import me.hulipvp.celestial.profile.Profile;
import me.hulipvp.celestial.profile.ProfileManager;
import me.hulipvp.celestial.api.manager.CelestialManager;
import org.bson.Document;

import java.util.*;
import java.util.stream.Collectors;

public class FactionManager extends CelestialManager<Faction> {

    /* The sorted list of PlayerFactions for /f list */
    @Getter @Setter private static Collection<PlayerFaction> topFactions;

    /**
     * Just for quicker access as I'm too lazy to access it from the
     * main class
     *
     * @return
     *      the ProfileManager instance we created when the plugin enabled
     */
    public static FactionManager get() {
        return Celestial.getInstance().getFactionManager();
    }

    /**
     * Get a <tt>Set</tt> of {@link PlayerFaction}s that are stored into
     * the server
     *
     * @return
     *      a <tt>Set</tt> of {@link PlayerFaction}s
     */
    public Set<PlayerFaction> getPlayerFactions() {
        return filterQuickly(PlayerFaction.class::isInstance)
                .map(PlayerFaction.class::cast)
                .collect(Collectors.toSet());
    }

    /**
     * Get a <tt>Set</tt> of {@link SystemFaction}s that are stored into
     * the server
     *
     * @return
     *      a <tt>Set</tt> of {@link SystemFaction}s
     */
    public Set<SystemFaction> getSystemFactions() {
        return filterQuickly(SystemFaction.class::isInstance)
                .map(SystemFaction.class::cast)
                .collect(Collectors.toSet());
    }

    /**
     * Get a {@link Faction} object that is stored on the server by retrieving it
     * from it's {@link UUID}
     * <p>
     * If a Faction is not found with a matching UUID, then this will return
     * <tt>null</tt>
     *
     * @param uuid
     *          the {@link UUID} that you wish to find a matching Faction
     *          with
     * @return
     *      a {@link Faction} with a matching UUID
     */
    @Override
    public Faction get(final UUID uuid) {
        return getStored().get(uuid.toString());
    }

    /**
     * Get a {@link Faction} object that is stored on the server by retrieving it
     * from it's name<br>
     * If a Faction is not found with a matching name, then this will return
     * <tt>null</tt>
     *
     * @param name
     *          the name that you wish to find a matching Faction
     *          with
     * @return
     *      a {@link Faction} with a matching name
     */
    @Override
    public Faction get(final String name) {
        for(final Faction faction : getStored().values()) {
            if(faction.getName().equalsIgnoreCase(name))
                return faction;
        }

        return null;
    }

    /**
     * Get a <tt>Set</tt> of {@link Faction}s from the provided string
     * <p>
     * This is used in instances where a Faction could have the same name as the provided
     * string and also a Faction containing a member with the same name
     *
     * @param string
     *          the name of the Faction and/or name of the Player you wish
     *          to find
     * @return
     *      a <tt>Set</tt> of {@link Faction}s with the matching name
     *      or a matching member name
     */
    public Set<Faction> getFactions(final String string) {
        final Set<Faction> factions = new HashSet<>();
        final Faction factionByName = get(string);
        if(factionByName != null)
            factions.add(factionByName);

        final Profile profile = ProfileManager.get().get(string);
        if(profile != null && profile.hasFaction())
            factions.add(profile.getFactionObj());

        return factions;
    }

    /**
     * Sort the list of PlayerFactions by eliminating offline factions and comparing
     * them by their online count
     * <p>
     * The comparison is automatically handled as {@link PlayerFaction} implements
     * {@link Comparable<PlayerFaction>}, compared upon the online player count
     *
     */
    public void sortFactionList() {
        final List<PlayerFaction> factions = getPlayerFactions().stream()
                .filter(PlayerFaction::isOnline)
                .sorted()
                .collect(Collectors.toList());

        setTopFactions(Collections.unmodifiableList(factions));
    }

    /**
     * Since there are multiple types of Factions, we have to explicitly create them
     * when loading them from the database
     *
     * @param document
     *          the document which stores all of the Faction's usueful
     *          information
     */
    public void load(final Document document) {
        final FactionType type = FactionType.valueOf(document.getString("type").toUpperCase());
        if(type == null)
            return;

        final Faction faction;
        final UUID uuid = UUID.fromString(document.getString("uuid"));
        final String name = document.getString("name");
        switch(type) {
            case PLAYER:
                final UUID leader = UUID.fromString(document.getString("leader"));

                faction = new PlayerFaction(uuid, name, leader);
                break;
            case SYSTEM:
                faction = new SystemFaction(uuid, name);
                break;
            default:
                faction = null;
        }

        if(faction != null)
            Celestial.getInstance().getBackend().load(faction);
    }
}
