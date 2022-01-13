package fr.xamez.gui;

import fr.xamez.CrafterBlock;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CrafterBlockInventoryListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        final CrafterBlock crafterBlock = CrafterBlock.CRAFTER_BLOCKS.get(e.getClickedBlock());
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && crafterBlock != null) {
            e.setCancelled(true);
            if (e.getPlayer().isSneaking()) e.getPlayer().sendMessage(""+crafterBlock.getInternalInventory());
            new CrafterBlockInventory(crafterBlock).open(e.getPlayer());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e ) {
        if(e.getView().getTitle().equals("§6MyCrafter")) {
            final CrafterBlockInventory crafterBlockInventory = CrafterBlockInventory.PLAYER_CRAFT_BLOCK_INVENTORIES.get(e.getPlayer());
            final CrafterBlock crafterBlock = crafterBlockInventory.getCrafterBlock();
            for (int i = 0; i < 9; i++)
                crafterBlock.getInventory()[i] = e.getInventory().getItem(i + 1);
            final ItemStack resultItem = e.getInventory().getItem(0);
            if (resultItem != null)
                crafterBlock.setResultItem(resultItem.getType());
            else
                crafterBlock.setResultItem(Material.AIR);
            e.getPlayer().sendMessage("§aVous avez bien modifié votre crafter !");
        }
    }

}
