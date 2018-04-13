package me.hulipvp.celestial.profile.data.object;

import lombok.Getter;
import lombok.Setter;
import me.hulipvp.celestial.profile.data.type.SettingType;

public class ProfileSetting {

    @Getter private final SettingType type;

    @Getter @Setter private Boolean value;

    public ProfileSetting(final SettingType type, final boolean value) {
        this.type = type;
        this.value = true;
    }

    public ProfileSetting(final SettingType type) {
        this(type, true);
    }

    public void toggle() {
        value = !value;
    }
}
