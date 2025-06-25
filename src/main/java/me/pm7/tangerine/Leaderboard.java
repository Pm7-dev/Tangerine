package me.pm7.tangerine;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Leaderboard implements CommandExecutor {
    private static final Tangerine plugin = Tangerine.getPlugin();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        List<Map.Entry<String, Long>> scores = organize();

        TextComponent.Builder builder = Component.text();

        builder.append(Component.text("TANGERINE LEADERBOARD:").color(NamedTextColor.GOLD)).decorate(TextDecoration.BOLD);
        for(int i=0; i<5; i++) {
            if(scores.size() > i) {
                appendScore(builder, i, scores);
            } else {
                break;
            }
        }

        if(sender instanceof Player p) {
            boolean contains = false;
            int index = Integer.MIN_VALUE;
            for(Map.Entry<String, Long> entry : scores) {
                if(entry.getKey().equals(p.getName())) {
                    contains=true;
                    index = scores.indexOf(entry);
                    break;
                }
            }

            if(contains) {
                if(index == 5) {
                    appendScore(builder, 5, scores);
                } else if (index == 6) {
                    appendScore(builder, 5, scores);
                    appendScore(builder, 6, scores);
                    if(scores.size() >= 8) {
                        appendScore(builder, 7, scores);
                    }
                } else if(index > 6) {
                    builder.append(Component.newline())
                           .append(Component.text("...").color(NamedTextColor.GRAY));

                    appendScore(builder, index-1, scores);
                    appendScore(builder, index, scores);
                    if(scores.size() > index + 1) {
                        appendScore(builder, index+1, scores);
                    }
                }
            }
        }

        builder.append(Component.newline());

       sender.sendMessage(builder.build());

        return true;
    }

    void appendScore(TextComponent.Builder builder, int index, List<Map.Entry<String, Long>> scores) {
        builder
                .append(Component.newline())
                .append(Component.text("(" + (index + 1) + ") ").color(NamedTextColor.YELLOW))
                .append(Component.text(scores.get(index).getKey()).color(NamedTextColor.YELLOW))
                .append(Component.text(": ").color(NamedTextColor.YELLOW))
                .append(Component.text(scores.get(index).getValue()).color(NamedTextColor.YELLOW));
    }

    private List<Map.Entry<String, Long>> organize() {

        List<Map.Entry<String, Long>> list = new ArrayList<>();

        for(Map.Entry<String, Long> entry : plugin.getScores()) {
            String name = Bukkit.getOfflinePlayer(UUID.fromString(entry.getKey())).getName();
            list.add(new AbstractMap.SimpleEntry<>(name, entry.getValue()));
        }

        list.sort(Map.Entry.comparingByValue());

        return list;
    }
}
