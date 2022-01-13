package fr.xamez.schedulers;

import fr.xamez.CrafterBlock;
import fr.xamez.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class CrafterBlockScheduler extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (CrafterBlock crafterBlock : CrafterBlock.CRAFTER_BLOCKS.values()) {
                for (Pair<Block, Hopper> hopper : getConnectedHoppers(crafterBlock.getBlock())) {
                    final Block hopperBlock = hopper.getKey();
                    if (crafterBlock.getBlock().getRelative(hopper.getValue().getFacing().getOppositeFace()).equals(hopperBlock)) {
                        final org.bukkit.block.Hopper hopperBukkit = (org.bukkit.block.Hopper) hopperBlock.getState();
                        if (!hopperBlock.isBlockPowered() && !hopperBlock.isBlockIndirectlyPowered()) {
                            final ItemStack[] hopperItems = hopperBukkit.getInventory().getContents();
                            for (ItemStack hopperItem : hopperItems) {
                                if (hopperItem != null) {
                                    System.out.println("BEFORE: " + crafterBlock.getInternalInventory().get(hopperItem.getType()));
                                    crafterBlock.addItemToInternalInventory(hopperItem.getType(), 1);
                                    System.out.println("Item added to internal inventory (" + hopperItem.getType() + ")");
                                    System.out.println("AFTER: " + crafterBlock.getInternalInventory().get(hopperItem.getType()));
                                    hopperBukkit.getInventory().removeItem(new ItemStack(hopperItem.getType(), 1));
                                    break;
                                }
                            }
                        }
                    }
                    if (hopper.getValue().getFacing().equals(BlockFace.DOWN)) {
                        final org.bukkit.block.Hopper hopperBukkit = (org.bukkit.block.Hopper) hopperBlock.getState();
                        if (!hopperBlock.isBlockPowered() && !hopperBlock.isBlockIndirectlyPowered()) {
                            if (crafterBlock.canCraft()) {
                                hopperBukkit.getInventory().addItem(crafterBlock.craft());
                            }
                        }
                    }
                }
            }
        }
    }

    private ArrayList<Pair<Block, Hopper>> getConnectedHoppers(Block block) {
        final ArrayList<Pair<Block, Hopper>> connectedHoppers = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            final BlockFace blockFace = BlockFace.values()[i];
            final Block blockRelative = block.getRelative(blockFace);
            if (blockRelative.getType().equals(Material.HOPPER)) connectedHoppers.add(new Pair<>(blockRelative, (Hopper) blockRelative.getBlockData()));
        }
        return connectedHoppers;
    }
}
