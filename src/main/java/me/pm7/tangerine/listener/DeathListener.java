package me.pm7.tangerine.listener;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DeathListener implements Listener {
    private final Random random = new Random();
    private final ItemStack tangerine;

    private static final List<EntityType> entities = Arrays.asList(
//            EntityType.ALLAY,
//            EntityType.ARMADILLO,
//            EntityType.AXOLOTL,
//            EntityType.BAT,
//            EntityType.BEE,
//            EntityType.BLAZE,
//            EntityType.BOGGED,
//            EntityType.BREEZE,
//            EntityType.CAMEL,
//            EntityType.CAT,
//            EntityType.CAVE_SPIDER,
//            EntityType.CHICKEN,
//            EntityType.COD,
//            EntityType.COW,
//            EntityType.CREAKING,
//            EntityType.CREEPER,
//            EntityType.DOLPHIN,
//            EntityType.DONKEY,
//            EntityType.DROWNED,
//            EntityType.ELDER_GUARDIAN,
//            EntityType.ENDERMAN,
//            EntityType.ENDERMITE,
//            EntityType.EVOKER,
//            EntityType.FOX,
//            EntityType.FROG,
//            EntityType.GHAST,
//            EntityType.GLOW_SQUID,
//            EntityType.GOAT,
//            EntityType.GUARDIAN,
//            EntityType.HOGLIN,
//            EntityType.HORSE,
//            EntityType.HUSK,
//            EntityType.ILLUSIONER,
//            EntityType.LLAMA,
//            EntityType.MAGMA_CUBE,
//            EntityType.MOOSHROOM,
//            EntityType.MULE,
//            EntityType.OCELOT,
//            EntityType.PANDA,
//            EntityType.PARROT,
//            EntityType.PHANTOM,
//            EntityType.PIG,
//            EntityType.PIGLIN,
//            EntityType.PIGLIN_BRUTE,
//            EntityType.PILLAGER,
//            EntityType.POLAR_BEAR,
//            EntityType.PUFFERFISH,
//            EntityType.RABBIT,
//            EntityType.RAVAGER,
//            EntityType.SALMON,
//            EntityType.SHEEP,
//            EntityType.SHULKER,
//            EntityType.SILVERFISH,
//            EntityType.SKELETON,
//            EntityType.SKELETON_HORSE,
//            EntityType.SLIME,
//            EntityType.SNIFFER,
//            EntityType.SNOW_GOLEM,
//            EntityType.SPIDER,
//            EntityType.SQUID,
//            EntityType.STRAY,
//            EntityType.STRIDER,
//            EntityType.TADPOLE,
//            EntityType.TRADER_LLAMA,
//            EntityType.TROPICAL_FISH,
//            EntityType.TURTLE,
//            EntityType.VEX,
//            EntityType.VILLAGER,
//            EntityType.VINDICATOR,
//            EntityType.WANDERING_TRADER,
//            EntityType.WITCH,
//            EntityType.WITHER_SKELETON,
//            EntityType.WOLF,
//            EntityType.ZOGLIN,
//            EntityType.ZOMBIE,
//            EntityType.ZOMBIE_HORSE,
//            EntityType.ZOMBIE_VILLAGER,
//            EntityType.ZOMBIFIED_PIGLIN

            EntityType.PIG,
            EntityType.COW,
            EntityType.CHICKEN,
            EntityType.SHEEP

    );

    public DeathListener() {
        tangerine = new ItemStack(Material.SNOWBALL, 1);
        ItemMeta meta = tangerine.getItemMeta();
        CustomModelDataComponent cmdp = meta.getCustomModelDataComponent();
        cmdp.setStrings(Collections.singletonList("tangerine"));
        meta.setItemName("Tangerine");
        meta.setRarity(ItemRarity.UNCOMMON);
        meta.setMaxStackSize(64);
        meta.setCustomModelDataComponent(cmdp);
        tangerine.setItemMeta(meta);
    }

    @EventHandler
    public void onEntityDie(EntityDeathEvent e ) {
        if(!entities.contains(e.getEntityType())) return;

        if(random.nextInt(3) != 0) return;

        ItemStack yay = tangerine.clone();
        yay.setAmount(random.nextInt(1, 6));
        e.getDrops().add(yay);
    }
}
