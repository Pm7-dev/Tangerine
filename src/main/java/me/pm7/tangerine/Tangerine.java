package me.pm7.tangerine;

import me.pm7.tangerine.listener.DeathListener;
import me.pm7.tangerine.listener.HitListener;
import me.pm7.tangerine.listener.JoinListener;
import me.pm7.tangerine.listener.RenameListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Tangerine extends JavaPlugin {

    private static Tangerine plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        saveConfig();

        // load leaderboard from config
        scores = new ArrayList<>();
        ConfigurationSection scoreSection = getScoreSection();
        for(String key : scoreSection.getKeys(true)) {
            scores.add(new AbstractMap.SimpleEntry<>(key, scoreSection.getLong(key)));
        }

        PluginManager pm7 = getServer().getPluginManager();

        pm7.registerEvents(new HitListener(), this); //tangerine
        pm7.registerEvents(new DeathListener(), this); //tangerine
        pm7.registerEvents(new RenameListener(), this); //tangerine
        pm7.registerEvents(new JoinListener(), this); //tangerine

        getCommand("tangerineleaderboard").setExecutor(new Leaderboard());

        if(getConfig().getBoolean("scoreTracker")) {
            ScoreMarker.startloop();
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::autosave, 1200L, 1200L);
        }

    }

    @Override
    public void onDisable() {
        if(getConfig().getBoolean("scoreTracker")) {
            for(ScoreMarker sm : ScoreMarker.getMarkers()) {
                sm.kill();
            }
            autosave();
        }
    }

    private void autosave() {
        ConfigurationSection section = getScoreSection();
        for(Map.Entry<String, Long> entry : scores) {
            section.set(entry.getKey(), entry.getValue());
        }
        saveConfig();
    }

    private static List<Map.Entry<String, Long>> scores;
    public List<Map.Entry<String, Long>> getScores() {return scores;}
    public ConfigurationSection getScoreSection() {
        return getConfig().createSection("tangerinePoints");
    }

    public static Tangerine getPlugin() {return plugin;}
}
