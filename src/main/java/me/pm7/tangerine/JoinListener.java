package me.pm7.tangerine;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {
    private static final Tangerine plugin = Tangerine.getPlugin();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if(plugin.getConfig().getBoolean("automaticallyApplyResourcePack")) {
            e.getPlayer().addResourcePack(
                    UUID.randomUUID(),
                    "",
                    new byte[4],
                    "the tangerines require this.",
                    true
            );
        }
    }
}
