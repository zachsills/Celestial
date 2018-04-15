package me.hulipvp.celestial.storage.file;

import me.hulipvp.celestial.util.Locale;

public class LocaleFile extends Configuration {

    public LocaleFile(final boolean override) {
        super("locale");

        loadLocale(override);
    }

    private void loadLocale(final boolean override) {
        for(final Locale message : Locale.values()) {
            if(config.getString(message.getPath()) == null || override)
                config.set(message.getPath(), message.getDefaultValue());
        }

        save();
    }
}