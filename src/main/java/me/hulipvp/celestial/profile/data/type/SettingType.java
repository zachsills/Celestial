package me.hulipvp.celestial.profile.data.type;

import lombok.Getter;

import java.util.Arrays;

public enum SettingType {

    PUBLIC_CHAT("publicChat", "Public Chat"),
    SCOREBOARD("showScoreboard", "Show Scoreboard"),
    DEATH_MESSAGES("deathMessages", "Death Messages"),
    DEATH_LIGHTNING("deathLightning", "Death Lightning"),
    FOUND_DIAMONDS("foundDiamonds", "Found Diamond Alerts");

    @Getter private final String key, friendlyName;
    @Getter private final boolean defaultValue;

    SettingType(final String key, final String friendlyName) {
        this.key = key;
        this.friendlyName = friendlyName;
        this.defaultValue = true;
    }

    public static SettingType getByKey(final String key) {
        return Arrays.stream(values())
                .filter(type -> type.getKey().equals(key))
                .findFirst()
                .orElse(null);
    }
}
