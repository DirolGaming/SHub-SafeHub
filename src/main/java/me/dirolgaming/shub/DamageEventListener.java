package me.dirolgaming.shub;

import org.bukkit.Bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEventListener
        implements Listener
{
    private main plugin;

    public DamageEventListener(main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFall(EntityDamageEvent event)
    {
        if ((event.getEntity().getLocation().getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world")))) &&
                (plugin.getConfig().getBoolean("disable-fall-damage")) && ((event.getEntity() instanceof Player)) && (event.getCause() == EntityDamageEvent.DamageCause.FALL)) {
            event.setCancelled(true);
        }
    }
}