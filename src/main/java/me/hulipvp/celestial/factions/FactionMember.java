package me.hulipvp.celestial.factions;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

public class FactionMember {

    @Getter private final UUID uuid;

    @Getter @Setter private Rank rank;

    public FactionMember(final UUID uuid, final Rank rank) {
        this.uuid = uuid;
        this.rank = rank;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public boolean isOnline() {
        return getPlayer() != null;
    }

    public boolean isAtLeast(final Rank rank) {
        return this.rank.ordinal() >= rank.ordinal();
    }

    public boolean promote() {
        final Rank promotion = Rank.getPromotion(rank.ordinal());

        return changeRank(promotion);
    }

    public boolean demote() {
        final Rank demotion = Rank.getDemotion(rank.ordinal());

        return changeRank(demotion);
    }

    public boolean changeRank(final Rank rank) {
        if(this.rank == rank)
            return false;

        this.rank = rank;
        return true;
    }

    public enum Rank {
        MEMBER,
        CAPTAIN,
        COLEADER,
        LEADER;

        public String getFriendlyName() {
            return WordUtils.capitalizeFully(name());
        }

        public static Rank getByString(final String string) {
            return Arrays.stream(values())
                    .filter(rank -> rank.name().equalsIgnoreCase(string.toUpperCase()))
                    .findFirst().orElse(null);
        }

        public static Rank getPromotion(final int ordinal) {
            return values()[ordinal + 1 >= values().length ? ordinal : ordinal + 1];
        }

        public static Rank getDemotion(final int ordinal) {
            return values()[ordinal - 1 <= 0 ? ordinal : ordinal - 1];
        }
    }
}
