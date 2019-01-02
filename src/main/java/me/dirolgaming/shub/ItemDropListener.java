package me.dirolgaming.shub;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropListener
        implements Listener
{
    private main plugin;

    public ItemDropListener(main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event)
    {
        if ((event.getPlayer().getLocation().getWorld().equals(Bukkit.getWorld(this.plugin.getConfig().getString("world.name")))) &&
                (this.plugin.getConfig().getString("item-drop").equals("false")) && (
                (event.getPlayer().getGameMode() == GameMode.SURVIVAL) ||
                        (event.getPlayer().getGameMode() == GameMode.ADVENTURE))) {
            event.setCancelled(true);
        }
    }
}