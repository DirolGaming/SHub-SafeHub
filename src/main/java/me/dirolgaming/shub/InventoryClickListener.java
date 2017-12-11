package me.dirolgaming.shub;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener
        implements Listener
{
    private main plugin;

    public InventoryClickListener(main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMoveItem(InventoryClickEvent event)
    {
        if ((event.getWhoClicked().getLocation().getWorld().equals(Bukkit.getWorld(this.plugin.getConfig().getString("world")))) &&
                (this.plugin.getConfig().getString("inventory-interaction").equals("false")) && (
                (event.getWhoClicked().getGameMode() == GameMode.SURVIVAL) ||
                        (event.getWhoClicked().getGameMode() == GameMode.ADVENTURE))) {
            event.setCancelled(true);
        }
    }
}