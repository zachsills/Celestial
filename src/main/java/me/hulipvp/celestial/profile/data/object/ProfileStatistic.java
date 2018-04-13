package me.hulipvp.celestial.profile.data.object;

import lombok.Getter;
import lombok.Setter;
import me.hulipvp.celestial.profile.data.type.StatisticType;

public class ProfileStatistic {

    @Getter private final StatisticType type;

    @Getter @Setter private int value;

    public ProfileStatistic(final StatisticType type, final int value) {
        this.type = type;
        this.value = value;
    }

    public ProfileStatistic(final StatisticType type) {
        this(type, 0);
    }

    public void increment() {
        value++;
    }
}
