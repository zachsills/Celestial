package me.hulipvp.celestial;

import lombok.Getter;
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
 */
public class Celestial extends JavaPlugin {

    /* Our single plugin instance */
    @Getter private static Celestial instance;

    /**
     * Method is called by the server when the plugin is being enabled
     */
    @Override
    public void onEnable() {
        instance = this;
    }

    /**
     * Method is called by the server when the plugin is being disabled
     */
    @Override
    public void onDisable() {

    }
}