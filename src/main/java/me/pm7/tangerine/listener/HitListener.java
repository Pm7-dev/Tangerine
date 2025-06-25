package me.pm7.tangerine.listener;

import me.pm7.tangerine.ScoreMarker;
import me.pm7.tangerine.Tangerine;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.*;

public class HitListener implements Listener {
    private static final Tangerine plugin = Tangerine.getPlugin();

    @EventHandler
    public void onPlayerHit(ProjectileHitEvent e) {
        Projectile p = e.getEntity();
        if(p.getType() != EntityType.SNOWBALL) return;

        Snowball s = (Snowball) p;
        ItemStack item = s.getItem();
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return;

        CustomModelDataComponent cmdc = meta.getCustomModelDataComponent();
        if(cmdc == null || !cmdc.getStrings().contains("tangerine")) return;
        if(!(e.getHitEntity() instanceof Player hit)) {return;}

        ProjectileSource ps = p.getShooter();
        if(!(ps instanceof Player shooter)) return;

        // get a custom color if there is one
        String color = "#FE9309";
        List<String> lore = meta.getLore();
        if(lore != null) {
            String potentialColor = lore.getLast();
            potentialColor = potentialColor.substring(potentialColor.length() - 7);
            if(potentialColor.startsWith("#")) color = potentialColor;
        }

        ChatColor finalColor;
        try {
            finalColor = ChatColor.of(color);
        } catch (IllegalArgumentException exception) {
            finalColor = ChatColor.of("#FE9309");
        }

        // Orange-ify the screen of the hit player
        TextComponent tc = new TextComponent();
        tc.setText(finalColor + "\uE000");
        hit.sendTitle(tc.getText(), null, 0, 5, 10);

        // Score tracker & score animation stuff
        if(plugin.getConfig().getBoolean("scoreTracker")) {

            // add score to shooter
            long score = 0;
            int index = -1;
            for(Map.Entry<String, Long> entry : plugin.getScores()) {
                if(entry.getKey().equals(shooter.getUniqueId().toString())) {
                    score = entry.getValue();
                    index = plugin.getScores().indexOf(entry);
                    break;
                }
            }

            if(index >= 0) {
                plugin.getScores().set(index, new AbstractMap.SimpleEntry<>(shooter.getUniqueId().toString(), score + 1));
            } else {
                plugin.getScores().add(new AbstractMap.SimpleEntry<>(shooter.getUniqueId().toString(), 1L));
            }

            // score "+1" thingy animation
            new ScoreMarker(shooter, hit.getLocation());
        }

        // sounds
        hit.playSound(hit.getLocation(), "tangerine:splat", SoundCategory.RECORDS, 1, 1);
        shooter.playSound(shooter.getLocation(), "tangerine:hit", SoundCategory.RECORDS, 1, 1);
    }

    @EventHandler
    public void onThrow(ProjectileLaunchEvent e) {
        if(e.getEntity().getType() == EntityType.SNOWBALL) {
            if(e.getEntity().getShooter() instanceof Player p) {
                p.setCooldown(Material.SNOWBALL, plugin.getConfig().getInt("throwCooldownTicks"));
            }
        }
    }

    // also throwing this in there
    @EventHandler
    public void onCraft(CraftItemEvent e) {
        ItemStack tangerine = new ItemStack(Material.SNOWBALL, 1);
        ItemMeta meta = tangerine.getItemMeta();
        CustomModelDataComponent cmdp = meta.getCustomModelDataComponent();
        cmdp.setStrings(Collections.singletonList("tangerine"));
        meta.setItemName("Tangerine");
        meta.setRarity(ItemRarity.UNCOMMON);
        meta.setMaxStackSize(11);
        meta.setCustomModelDataComponent(cmdp);
        tangerine.setItemMeta(meta);

        if(e.getInventory().containsAtLeast(tangerine, 1)) {
            e.setCancelled(true);
        }
    }
}