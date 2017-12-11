package me.dirolgaming.shub;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener
        implements Listener
{
    private main plugin;

    public MoveListener(main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        if ((player.getLocation().getWorld().equals(Bukkit.getWorld(this.plugin.getConfig().getString("world")))) &&
                (player.getGameMode() != GameMode.CREATIVE) &&
                (player.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() != Material.AIR) &&
                (!player.isFlying())) {
            player.setAllowFlight(true);
        }
    }

    @EventHandler
    public void onMove2(PlayerMoveEvent t)
    {
        Player p = t.getPlayer();
        if ((this.plugin.getConfig().getBoolean("enable-movementYtp")) && (p.getLocation().getWorld().equals(Bukkit.getWorld(this.plugin.getConfig().getString("world")))) &&
                (p.getLocation().getY() == this.plugin.getConfig().getInt("cordinate-y"))) {
            p.teleport(this.plugin.spawnpoint.getSpawnpoint(Bukkit.getWorld(this.plugin.getConfig().getString("world"))));
        }
    }
}