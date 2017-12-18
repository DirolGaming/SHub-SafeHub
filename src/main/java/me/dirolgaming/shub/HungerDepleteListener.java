package me.dirolgaming.shub;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerDepleteListener
        implements Listener
{
    private main plugin;

    public HungerDepleteListener(main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent event)
    {
        if ((event.getEntity().getLocation().getWorld().equals(Bukkit.getWorld(this.plugin.getConfig().getString("world")))) &&
                ((event.getEntity() instanceof Player)) && (plugin.getConfig().getBoolean("disable-hunger"))) {
            event.setCancelled(true);
        }
    }
}