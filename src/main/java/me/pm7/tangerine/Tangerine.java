package me.pm7.tangerine;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Tangerine extends JavaPlugin {

    private static Tangerine plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        PluginManager pm7 = getServer().getPluginManager();

        pm7.registerEvents(new HitListener(), this); //tangerine
        pm7.registerEvents(new DeathListener(), this); //tangerine

        ScoreMarker.startloop();
    }

    public static Tangerine getPlugin() {return plugin;}
}
