package fr.xamez;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CrafterBlockRecipe extends ShapedRecipe {

    private static final ItemStack CRAFTER_BLOCK;

    static {
        CRAFTER_BLOCK = new ItemStack(Material.CRAFTING_TABLE, 1);
        final ItemMeta meta = CRAFTER_BLOCK.getItemMeta();
        meta.setDisplayName("§6MyCrafter");
        meta.setLore(List.of("§8» §7Permet de crafter n'importe quoi automatiquement"));
        CRAFTER_BLOCK.setItemMeta(meta);
    }

    public CrafterBlockRecipe() {
        super(NamespacedKey.fromString("mycrafter"), getCrafterBlock());
        this.shape(" D ",
                   "SCS",
                   " D ");
        this.setIngredient('D', Material.DIAMOND);
        this.setIngredient('S', Material.STICK);
        this.setIngredient('C', Material.CRAFTING_TABLE);
    }

    public static ItemStack getCrafterBlock() {
        return CRAFTER_BLOCK;
    }

}
