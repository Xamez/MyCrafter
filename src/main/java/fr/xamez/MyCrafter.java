package fr.xamez;

import fr.xamez.gui.CrafterBlockInventoryListener;
import fr.xamez.listeners.CrafterBlockListener;
import fr.xamez.schedulers.CrafterBlockScheduler;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MyCrafter extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().addRecipe(new CrafterBlockRecipe());
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new CrafterBlockListener(), this);
        pm.registerEvents(new CrafterBlockInventoryListener(), this);
        new CrafterBlockScheduler().runTaskTimer(this, 0L, 8L);
    }

    @Override
    public void onDisable() {
        Bukkit.removeRecipe(NamespacedKey.fromString("mycrafter"));
    }
}
