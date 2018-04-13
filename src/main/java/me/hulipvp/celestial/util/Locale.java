package me.hulipvp.celestial.util;

import lombok.Getter;
import me.hulipvp.celestial.storage.file.Configuration;
import me.hulipvp.celestial.storage.file.LocaleFile;
import org.bukkit.configuration.file.FileConfiguration;

public enum Locale {

    NO_PERMISSION("main.no-permission", "&cYou do not have permission to use this command."),

    DEATHBAN_MESSAGE("other.deathban-message", "&cYou have been death-banned until %time%.\nBuy lives at &c&n%store%&c."),

    COMMAND_FACTION_REQUIRES_RANK("command.faction.requires-rank", "&cYou must be a %rank% to use this command."),
    COMMAND_FACTION_REQUIRES_FACTION("command.faction.requires-faction", "&cThis command requires a faction");

    @Getter private final String path;
    @Getter private final String defaultValue;

    Locale(final String path, final String defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return StringUtils.color(config.getString(path));
    }

    private static Configuration configuration;
    private static FileConfiguration config;

    public static void load(final boolean override) {
        configuration = new LocaleFile(override);

        config = configuration.getConfig();
    }
}
