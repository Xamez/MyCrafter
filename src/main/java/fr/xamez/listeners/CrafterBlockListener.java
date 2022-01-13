package fr.xamez.listeners;

import fr.xamez.CrafterBlock;
import fr.xamez.CrafterBlockRecipe;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class CrafterBlockListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getItemInHand().equals(CrafterBlockRecipe.getCrafterBlock())) {
            CrafterBlock.CRAFTER_BLOCKS.put(e.getBlock(), new CrafterBlock(e.getBlockPlaced()));
            e.getPlayer().sendMessage("§aVous avez placé un CrafterBlock !");
        }
    }

    @EventHandler
    public void onPlace(BlockBreakEvent e) {
        if (CrafterBlock.CRAFTER_BLOCKS.containsKey(e.getBlock())) {
            CrafterBlock.CRAFTER_BLOCKS.remove(e.getBlock());
            e.getPlayer().sendMessage("§aVous avez cassé un CrafterBlock !");
        }
    }

}
