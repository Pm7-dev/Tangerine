package me.pm7.tangerine;

import me.pm7.tangerine.listener.DeathListener;
import me.pm7.tangerine.listener.HitListener;
import me.pm7.tangerine.listener.JoinListener;
import me.pm7.tangerine.listener.RenameListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Tangerine extends JavaPlugin {

    private static Tangerine plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        saveConfig();

        PluginManager pm7 = getServer().getPluginManager();

        pm7.registerEvents(new HitListener(), this); //tangerine
        pm7.registerEvents(new DeathListener(), this); //tangerine
        pm7.registerEvents(new RenameListener(), this); //tangerine
        pm7.registerEvents(new JoinListener(), this); //tangerine

        if(getConfig().getBoolean("scoreTracker")) {
            ScoreMarker.startloop();
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::autosave, 0L, 1200L);
        }

    }

    @Override
    public void onDisable() {
        for(ScoreMarker sm : ScoreMarker.getMarkers()) {
            sm.kill();
        }

        if(getConfig().getBoolean("scoreTracker")) {
            autosave();
        }
    }

    // this was going to do more at some point I think :/
    private void autosave() {
        saveConfig();
    }

    public ConfigurationSection getScores() {
        return getConfig().createSection("tangerinePoints");
    }

    public static Tangerine getPlugin() {return plugin;}
}
