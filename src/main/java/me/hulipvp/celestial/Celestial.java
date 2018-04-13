package me.hulipvp.celestial;

import lombok.Getter;
import me.hulipvp.celestial.factions.FactionManager;
import me.hulipvp.celestial.listeners.ChatListener;
import me.hulipvp.celestial.listeners.PlayerListener;
import me.hulipvp.celestial.profile.ProfileManager;
import me.hulipvp.celestial.storage.backend.IBackend;
import me.hulipvp.celestial.storage.backend.MongoBackend;
import me.hulipvp.celestial.util.Locale;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * MIT License
 *
 * Copyright (c) 2018 Zach Sills
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author Zach Sills
 * @version 1.0.0-DEV
 * @since 2018-04-12
 */
public class Celestial extends JavaPlugin {

    /* Our single plugin instance */
    @Getter private static Celestial instance;

    /* Backend */
    @Getter private IBackend backend;

    /* Managers */
    @Getter private FactionManager factionManager;
    @Getter private ProfileManager profileManager;

    /**
     * This method is called by the server when the plugin is being enabled
     */
    @Override
    public void onEnable() {
        instance = this;

        /* Load Storage objs */
        Locale.load(false);

        backend = new MongoBackend(this);
        backend.loadFactions();

        /* Instantiate Managers */
        factionManager = new FactionManager();
        profileManager = new ProfileManager();

        /* Register Listeners */
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    /**
     * This method is called by the server when the plugin is being disabled
     */
    @Override
    public void onDisable() {
        /* Save all of our Celestial objs to the database */
        factionManager.forEach(backend::save);
        profileManager.forEach(backend::save);

        /* Close our database connection */
        backend.close();
    }
}