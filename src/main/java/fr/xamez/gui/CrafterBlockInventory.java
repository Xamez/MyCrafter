package fr.xamez.gui;

import fr.xamez.CrafterBlock;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CrafterBlockInventory {

    public static HashMap<Player, CrafterBlockInventory> PLAYER_CRAFT_BLOCK_INVENTORIES = new HashMap<>();

    private final Inventory inventory;
    private final CrafterBlock crafterBlock;

    public CrafterBlockInventory(CrafterBlock crafterBlock) {
        this.crafterBlock = crafterBlock;
        this.inventory = Bukkit.createInventory(null, InventoryType.WORKBENCH, "§6MyCrafter");
        if (crafterBlock.getResultItem() != null)
            this.inventory.setItem(0, new ItemStack(crafterBlock.getResultItem()));
        for (int i = 0; i < 9; i++)
            this.inventory.setItem(i + 1, crafterBlock.getInventory()[i]);
    }

    public void open(Player p) {
        p.openInventory(this.inventory);
        PLAYER_CRAFT_BLOCK_INVENTORIES.put(p, this);
    }

    public CrafterBlock getCrafterBlock() {
        return crafterBlock;
    }

}
