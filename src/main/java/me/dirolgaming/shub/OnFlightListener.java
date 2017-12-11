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
            if (this.plugin.getConfig().getBoolean("Enable-Doublejump"))
            {
                if (player.getGameMode() == GameMode.CREATIVE) {
                    return;
                }
                player.setAllowFlight(false);
                event.setCancelled(true);
                player.setFlying(false);
                if (this.plugin.doublejumpcooldownTime.containsKey(player))
                {
                    int time = ((Integer)this.plugin.doublejumpcooldownTime.get(player)).intValue();
                    String cooldowntime = Integer.toString(time);
                    if (plugin.getConfig().getBoolean("Enable-Doublejump-Messages")) {
                        player.sendMessage(this.plugin.getConfig().getString("Doublejump-cooldownleft").replaceAll("&", "§").replaceAll("%time%", cooldowntime));
                    }
                    return;
                }
                this.plugin.doublejumpcooldownTime.put(player, Integer.valueOf(3));

                player.setVelocity(player.getEyeLocation().getDirection().multiply(this.plugin.getConfig().getInt("velocity")).setY(1));

                this.plugin.doublejumpcooldownTask.put(player, new BukkitRunnable()
                {
                    public void run()
                    {
                        OnFlightListener.this.plugin.doublejumpcooldownTime.put(player, Integer.valueOf(((Integer)OnFlightListener.this.plugin.doublejumpcooldownTime.get(player)).intValue() - 1));
                        if (((Integer)OnFlightListener.this.plugin.doublejumpcooldownTime.get(player)).intValue() == 0)
                        {
                            if (plugin.getConfig().getBoolean("Enable-Doublejump-Messages")) {
                                player.sendMessage(OnFlightListener.this.plugin.getConfig().getString("Doublejump-cooldownmessage").replaceAll("&", "§"));
                                player.sendMessage(OnFlightListener.this.plugin.getConfig().getString("Doublejump-cooldownleft").replaceAll("&", "§"));
                                player.sendMessage(OnFlightListener.this.plugin.getConfig().getString("Doublejump-Message").replaceAll("&", "§"));
                            }
                            OnFlightListener.this.plugin.doublejumpcooldownTime.remove(player);
                            OnFlightListener.this.plugin.doublejumpcooldownTask.remove(player);
                            cancel();
                        }
                    }
                });
                ((BukkitRunnable)this.plugin.doublejumpcooldownTask.get(player)).runTaskTimer(this.plugin, 20L, 20L);
                if (this.plugin.getConfig().getBoolean("Enable-Effect"))
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