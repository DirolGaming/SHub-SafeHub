package me.dirolgaming.shub;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener
        implements Listener {
    private main plugin;

    public BlockPlaceListener(main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event)
    {
        if ((event.getPlayer().getLocation().getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world.name")))) &&
                (plugin.getConfig().getString("on-place-block-deny").equals("true")) && (
                (event.getPlayer().getGameMode() == GameMode.ADVENTURE) ||
                        (event.getPlayer().getGameMode() == GameMode.SURVIVAL))) {
            event.getPlayer().sendMessage(plugin.getConfig().getString("block-place-denymsg"));
            event.setCancelled(true);
        }
    }
}