package me.apeiros.alchimiavitae;

import org.bukkit.plugin.java.JavaPlugin;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

import me.apeiros.alchimiavitae.setup.Setup;

/**
 * Main class
 */
public class AlchimiaVitae extends JavaPlugin implements SlimefunAddon {

    private static AlchimiaVitae instance;

    @Override
    public void onEnable() {
        // Set instance
        instance = this;

        // Save default config
        saveDefaultConfig();

        // Set up items and listeners
        Setup.setup(this);
    }

    @Override
    public void onDisable() {
        // Set instance to null
        instance = null;
    }

    public static AlchimiaVitae i() {
        return instance;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Apeiros-46B/AlchimiaVitae/issues";
    }

}
