package me.dirolgaming.shub;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnFlightListener
        implements Listener
{
    private main plugin;

    public OnFlightListener(main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFlight(PlayerToggleFlightEvent event)
    {
        Effect effect = Effect.valueOf(this.plugin.getConfig().getString("Effect").toUpperCase());
        final Player player = event.getPlayer();
        if (player.getLocation().getWorld().equals(Bukkit.getWorld(this.plugin.getConfig().getString("world")))) {
            if (plugin.getConfig().getBoolean("enable-doublejump"))
            {
                if (player.getGameMode() == GameMode.CREATIVE) {
                    return;
                }
                player.setAllowFlight(false);
                event.setCancelled(true);
                player.setFlying(false);
                if (plugin.doublejumpcooldownTime.containsKey(player))
                {
                    int time = ((Integer)plugin.doublejumpcooldownTime.get(player)).intValue();
                    String cooldowntime = Integer.toString(time);
                    if (plugin.getConfig().getBoolean("enable-doublejump-messages")) {
                        player.sendMessage(plugin.getConfig().getString("doublejump-cooldownleft").replaceAll("&", "§").replaceAll("%time%", cooldowntime));
                    }
                    return;
                }
                plugin.doublejumpcooldownTime.put(player, Integer.valueOf(3));

                player.setVelocity(player.getEyeLocation().getDirection().multiply(this.plugin.getConfig().getInt("velocity")).setY(1));

                this.plugin.doublejumpcooldownTask.put(player, new BukkitRunnable()
                {
                    public void run()
                    {
                        OnFlightListener.this.plugin.doublejumpcooldownTime.put(player, Integer.valueOf(((Integer)OnFlightListener.this.plugin.doublejumpcooldownTime.get(player)).intValue() - 1));
                        if (((Integer)OnFlightListener.this.plugin.doublejumpcooldownTime.get(player)).intValue() == 0)
                        {
                            if (plugin.getConfig().getBoolean("enable-doublejump-messages")) {
                                player.sendMessage(OnFlightListener.this.plugin.getConfig().getString("doublejump-cooldownmessage").replaceAll("&", "§"));
                                player.sendMessage(OnFlightListener.this.plugin.getConfig().getString("doublejump-cooldownleft").replaceAll("&", "§"));
                                player.sendMessage(OnFlightListener.this.plugin.getConfig().getString("doublejump-message").replaceAll("&", "§"));
                            }
                            plugin.doublejumpcooldownTime.remove(player);
                            plugin.doublejumpcooldownTask.remove(player);
                            cancel();
                        }
                    }
                });
                ((BukkitRunnable)plugin.doublejumpcooldownTask.get(player)).runTaskTimer(plugin, 20L, 20L);
                if (plugin.getConfig().getBoolean("enable-effect"))
                {
                    player.playEffect(player.getLocation(), effect, null);
                    player.playEffect(player.getLocation(), effect, null);
                }
            }
            else if (player.getGameMode() != GameMode.CREATIVE)
            {
                player.setAllowFlight(false);
            }
        }
    }
}