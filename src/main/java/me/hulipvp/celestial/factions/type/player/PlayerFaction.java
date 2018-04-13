package me.hulipvp.celestial.factions.type.player;

import lombok.Getter;
import me.hulipvp.celestial.factions.Faction;
import me.hulipvp.celestial.factions.FactionMember;
import me.hulipvp.celestial.factions.Relation;
import me.hulipvp.celestial.factions.type.FactionType;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerFaction extends Faction implements Comparable<PlayerFaction> {

    @Getter private final Set<UUID> invites;
    @Getter private final Set<FactionMember> members;

    @Getter private final Map<String, Relation> relations, relationInvites;

    public PlayerFaction(final UUID uuid, final String name, final UUID leader) {
        super(uuid, FactionType.PLAYER, name);

        invites = new HashSet<>();

        members = new HashSet<>();
        addMember(new FactionMember(uuid, FactionMember.Rank.LEADER));

        relations = new HashMap<>();
        relationInvites = new HashMap<>();
    }

    public Set<Player> getOnlinePlayers() {
        return members.stream()
                .filter(FactionMember::isOnline)
                .map(FactionMember::getPlayer)
                .collect(Collectors.toSet());
    }

    public int getOnlineSize() {
        return getOnlinePlayers().size();
    }

    public boolean isOnline() {
        return getOnlineSize() > 0;
    }

    public void addMember(final FactionMember member) {
        members.add(member);
    }

    public void removeMember(final FactionMember member) {
        members.remove(member);
    }

    public void removeMember(final UUID uuid) {
        members.remove(getMember(uuid));
    }

    public FactionMember getMember(final UUID uuid) {
        for(final FactionMember member : members) {
            if(member.getUuid().toString().equals(uuid.toString()))
                return member;
        }

        return null;
    }

    public FactionMember getMember(final Player player) {
        return getMember(player.getUniqueId());
    }

    public Relation getRelation(final PlayerFaction faction) {
        return relations.get(faction.getUuidString());
    }

    public boolean hasRelation(final PlayerFaction faction) {
        return getRelation(faction) != null;
    }

    public boolean isAllied(final PlayerFaction faction) {
        if(!hasRelation(faction))
            return false;

        return getRelation(faction) == Relation.ALLY;
    }

    public boolean isEnemy(final PlayerFaction faction) {
        if(!hasRelation(faction))
            return false;

        return getRelation(faction) == Relation.ENEMY;
    }

    @Override
    public void show(final Player player) {

    }

    @Override
    public int compareTo(final PlayerFaction other) {
        return other.getOnlineSize() - getOnlineSize();
    }
}
