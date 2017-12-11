package me.dirolgaming.shub;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener
        implements Listener
{
    private main plugin;

    public WeatherChangeListener(main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event)
    {
        if ((event.getWorld().equals(Bukkit.getWorld(this.plugin.getConfig().getString("world")))) &&
                (this.plugin.getConfig().getBoolean("Disable-Weather")) && (event.toWeatherState())) {
            event.setCancelled(true);
        }
    }
}