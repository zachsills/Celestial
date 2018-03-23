package me.hulipvp.celestial.factions.type.player;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Arrays;
import java.util.UUID;

public class FactionMember {

    @Getter private final UUID uuid;
    @Getter @Setter private Rank rank;

    public FactionMember(final UUID uuid, final Rank rank) {
        this.uuid = uuid;
        this.rank = rank;
    }

    public boolean isAtLeast(final Rank rank) {
        return this.rank.ordinal() >= rank.ordinal();
    }

    public enum Rank {
        MEMBER,
        CAPTAIN,
        COLEADER,
        LEADER;

        public static Rank getByString(@NonNull final String string) {
            return Arrays.stream(values())
                    .filter(rank -> rank.name().equalsIgnoreCase(string))
                    .findFirst().orElse(null);
        }
    }
}
