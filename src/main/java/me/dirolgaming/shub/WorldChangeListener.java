package me.dirolgaming.shub;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChangeListener
        implements Listener
{
    private main plugin;

    public WorldChangeListener(main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void world(PlayerChangedWorldEvent e)
    {
        Player player = e.getPlayer();
        player.setAllowFlight(
                (player.getGameMode().equals(GameMode.CREATIVE)) ||
                        (player.getGameMode().equals(GameMode.SPECTATOR)));
    }
}