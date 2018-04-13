package me.hulipvp.celestial.storage.file;

import lombok.Getter;
import me.hulipvp.celestial.Celestial;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public abstract class Configuration {

    private final File file;

    @Getter public FileConfiguration config;

    public Configuration(final String name, final JavaPlugin plugin) {
        file = new File(plugin.getDataFolder() + File.separator + name + ".yml");
        if(!file.exists()) {
            try {
                plugin.getLogger().info(name + ".yml doesn't exist, now creating...");

                file.getParentFile().mkdirs();
                if(plugin.getResource(name) != null)
                    plugin.saveResource(name, false);
                else
                    file.createNewFile();

                plugin.getLogger().info(name + ".yml has successfully been created");
            } catch(final IOException ex) {
                plugin.getLogger().severe(name + ".yml wasn't able to be created: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        load(false);
    }

    public Configuration(final String name) {
        this(name, Celestial.getInstance());
    }

    public boolean save() {
        try {
            config.save(file);
            return true;
        } catch(final IOException ex) {
            Celestial.getInstance().getLogger().severe(  file.getName() + " wasn't able to be saved: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public void load(final boolean save) {
        if(save)
            save();

        config = YamlConfiguration.loadConfiguration(file);
    }
}
