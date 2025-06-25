package me.pm7.tangerine;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Leaderboard implements CommandExecutor {
    private static final Tangerine plugin = Tangerine.getPlugin();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        HashMap<String, Long> scores = organize();



        return true;
    }

    private HashMap<String, Long> organize() {

        // Load from config
        HashMap<String, Long> values = new HashMap<>();
        for(String s : plugin.getScores().getKeys(true)) {
            UUID uuid = UUID.fromString(s);
            OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
            values.put(p.getName(), plugin.getScores().getLong(s));
        }

        // Create a list from elements of HashMap
        List<Map.Entry<String, Long> > list =
                new LinkedList<>(values.entrySet());

        // Sort the list
        list.sort(Map.Entry.comparingByValue());

        // put data from sorted list to hashmap
        HashMap<String, Long> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Long> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }

        return temp;
    }
}
