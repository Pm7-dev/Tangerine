package me.pm7.tangerine.listener;

import me.pm7.tangerine.Tangerine;
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
                    "https://github.com/Pm7-dev/Tangerine/raw/refs/heads/master/resourcepack.zip",
                    new byte[] {
                            (byte) 0xf7, (byte) 0x6b, (byte) 0xa3, (byte) 0x16,
                            (byte) 0x7e, (byte) 0xc0, (byte) 0xf6, (byte) 0x8a,
                            (byte) 0xc9, (byte) 0xae, (byte) 0x90, (byte) 0xbe,
                            (byte) 0x13, (byte) 0x84, (byte) 0x8a, (byte) 0x80,
                            (byte) 0x3a, (byte) 0x59, (byte) 0x21, (byte) 0x9e
                    },
                    "the tangerines require acknowledgement.",
                    true
            );
        }
    }
}
