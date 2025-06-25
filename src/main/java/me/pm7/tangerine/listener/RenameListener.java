package me.pm7.tangerine.listener;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.PotionContents;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.potion.PotionType;

import java.util.List;

public class RenameListener implements Listener {
    @EventHandler
    public void onAnvilUse(PrepareAnvilEvent e) {

        // make sure the rename text is a color
        String renameText = e.getView().getRenameText();
        if(renameText == null) return;
        ChatColor color;
        try { color = ChatColor.of(renameText);}
        catch (IllegalArgumentException exception) {return;}

        // make sure it's a tangerine
        ItemStack item = e.getInventory().getItem(0);
        if(item==null) return;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return;
        CustomModelDataComponent cmdc = meta.getCustomModelDataComponent();
        if(cmdc == null || !cmdc.getStrings().contains("tangerine")) return;

        // make sure other slot is null
        if(e.getInventory().getItem(1) != null) return;

        ItemStack result = item.clone();

        Color potionColor = Color.fromRGB(
                Integer.parseInt(renameText.substring(1, 3), 16),
                Integer.parseInt(renameText.substring(3, 5), 16),
                Integer.parseInt(renameText.substring(5, 7), 16)
        );

        PotionContents.Builder builder = PotionContents.potionContents();
        builder.potion(PotionType.WATER);
        builder.customColor(potionColor);
        //builder.customColor(Color.fromRGB(color.getColor().getRGB()));
        result.setData(DataComponentTypes.POTION_CONTENTS, builder);


        ItemMeta resultMeta = result.getItemMeta();
        resultMeta.setLore(List.of(
                color + "Color: " + renameText
        ));

        result.setItemMeta(resultMeta);

        e.setResult(result);
    }
}
