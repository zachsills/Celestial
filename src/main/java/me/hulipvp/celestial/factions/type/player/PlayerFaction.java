package me.hulipvp.celestial.factions.type.player;

import lombok.Getter;
import me.hulipvp.celestial.factions.Faction;
import me.hulipvp.celestial.factions.Relation;
import me.hulipvp.celestial.factions.type.FactionType;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerFaction extends Faction {

    @Getter private final Set<UUID> invites;
    @Getter private final Set<FactionMember> members;

    @Getter private final Map<String, Relation> relations, relationInvites;

    public PlayerFaction(final UUID uuid, final String name) {
        super(uuid, FactionType.PLAYER, name);

        invites = new HashSet<>();
        members = new HashSet<>();

        relations = new HashMap<>();
        relationInvites = new HashMap<>();
    }

    @Override
    public void show(Player player) {

    }
}
