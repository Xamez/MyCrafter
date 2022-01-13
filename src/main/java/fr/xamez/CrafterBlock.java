package fr.xamez;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.*;

public class CrafterBlock {

    public static HashMap<Block, CrafterBlock> CRAFTER_BLOCKS = new HashMap<>();


    private final Block block;
    private final ItemStack[] inventory;
    private final HashMap<Material, Integer> internalInventory;
    private Material resultItem;

    public CrafterBlock(Block block) {
        this.block = block;
        this.inventory = new ItemStack[9];
        this.internalInventory = new HashMap<>();
    }

    public Material getResultItem() {
        return resultItem;
    }

    public void setResultItem(Material resultItem) {
        this.resultItem = resultItem;
    }

    public Block getBlock() {
        return block;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public void addItemToInternalInventory(Material material, int amount) {
        if (!internalInventory.containsKey(material))
            internalInventory.put(material, amount);
        else
            internalInventory.put(material, internalInventory.get(material) + amount);
    }

    public HashMap<Material, Integer> getInternalInventory() {
        return internalInventory;
    }

    public boolean canCraft() {
        if (resultItem == null) return false;
        for (Recipe recipe : Bukkit.getRecipesFor(new ItemStack(resultItem))) {
            ItemStack[] ingredients;
            if (recipe instanceof ShapedRecipe shapedRecipe) {
                ingredients = shapedRecipe.getIngredientMap().values().toArray(ItemStack[]::new);
                ItemStack[] recipeInventory = inventory.clone();
                for (ItemStack itemStack : recipeInventory)
                    if (itemStack != null) itemStack.setAmount(1);
                /*System.out.println(Arrays.toString(recipeInventory));
                System.out.println(Arrays.toString(ingredients));
                System.out.println(Arrays.deepEquals(recipeInventory, ingredients) + " // " + containsItem(ingredients));
                System.out.println(internalInventory);*/
                if (ingredients.length != 9 && containsItem(ingredients)) return true;
                else if (Arrays.deepEquals(recipeInventory, ingredients) && containsItem(ingredients)) return true;
            }
        }
        return false;
    }

    private boolean containsItem(ItemStack[] ingredients) {
        // TODO REMOVE ELEMENT IN COPY OF 'internalInventory' AFTER TESTING
        for (ItemStack ingredient : ingredients) {
            if (ingredient == null) continue;
            if (!internalInventory.containsKey(ingredient.getType())) return false;
            if (internalInventory.get(ingredient.getType()) < ingredient.getAmount()) return false;
        }
        return true;
    }

    public ItemStack craft() {
        for (Recipe recipe : Bukkit.getRecipesFor(new ItemStack(resultItem))) {
            ArrayList<ItemStack> ingredients = new ArrayList<>();
            if (recipe instanceof ShapedRecipe shapedRecipe)
                ingredients = new ArrayList<>(shapedRecipe.getIngredientMap().values());
            else if (recipe instanceof ShapelessRecipe shapelessRecipe)
                ingredients = new ArrayList<>(shapelessRecipe.getIngredientList());
            for (ItemStack ingredient : ingredients) {
                if (ingredient == null || internalInventory.get(ingredient.getType()) == null) continue;
                final int newAmount = internalInventory.get(ingredient.getType()) - ingredient.getAmount();
                if (newAmount == 0) // TODO MAY BE A PROBLEM
                    internalInventory.remove(ingredient.getType());
                else
                    internalInventory.put(ingredient.getType(), newAmount);
            }
        }
        return new ItemStack(resultItem);
    }

}