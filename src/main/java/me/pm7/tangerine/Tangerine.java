package me.pm7.tangerine;

import org.bukkit.plugin.java.JavaPlugin;

public final class Tangerine extends JavaPlugin {

    private static Tangerine plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getServer().getPluginManager().registerEvents(new HitListener(), this); //tangerine
        ScoreMarker.startloop();
    }

    @Override
    public void onDisable() {
    }

    public static Tangerine getPlugin() {return plugin;}
}
