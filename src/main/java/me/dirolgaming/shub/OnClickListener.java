package me.dirolgaming.shub;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnClickListener implements Listener {
    private main plugin;

    public OnClickListener(main plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onclickBLAZEROD(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        Player player = e.getPlayer();
        if (player.getLocation().getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world")))) {
            if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) &&
                    p.getInventory().getItemInMainHand().getType().equals(Material.BLAZE_ROD)) {
                if (plugin.cooldownTime.containsKey(p)) {
                    return;
                }

                plugin.cooldownTime.put(p, 1);
                p.launchProjectile(Snowball.class);
                p.launchProjectile(Snowball.class);
                p.launchProjectile(Snowball.class);
                plugin.cooldownTask.put(p, new BukkitRunnable() {
                    public void run() {
                        plugin.cooldownTime.put(p, plugin.cooldownTime.get(p) - 1);
                        if (plugin.cooldownTime.get(p) == 0) {
                            plugin.cooldownTime.remove(p);
                            plugin.cooldownTask.remove(p);
                            this.cancel();
                        }

                    }
                });
                plugin.cooldownTask.get(p).runTaskTimer(plugin, 20L, 20L);
            }

        }
    }

    @EventHandler
    public void onclickMagic(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getLocation().getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world")))) {
            if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) &&
                    player.getInventory().getItemInMainHand().getType().equals(Material.WATCH)) {
                if (plugin.clock.contains(player.getName())) {
                    plugin.clock.remove(player.getName());
                    plugin.getServer().getOnlinePlayers().forEach(p -> {
                        if (p != e.getPlayer()) {
                            e.getPlayer().showPlayer(plugin, p);
                        }
                    });
                    player.sendMessage(plugin.getConfig().getString("show-players-message").replaceAll("&", "§"));
                } else {
                    plugin.clock.add(player.getName());
                    plugin.getServer().getOnlinePlayers().forEach(p -> {
                        if (p != e.getPlayer()) {
                            e.getPlayer().hidePlayer(plugin, p);
                        }
                    });
                    player.sendMessage(plugin.getConfig().getString("hide-players-message").replaceAll("&", "§"));
                }
            }
        }
    }
    @EventHandler
    public void onClickDiamond(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getLocation().getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world")))) {
            if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) &&
                    player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND)) {
                if (plugin.chat.contains(player.getName())) {
                    plugin.chat.remove(player.getName());
                    player.sendMessage(plugin.getConfig().getString("chat-unmute").replaceAll("&", "§"));
                } else {
                    plugin.chat.add(player.getName());
                    player.sendMessage(plugin.getConfig().getString("chat-mute").replaceAll("&", "§"));
                }
            }
        }
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (plugin.chat.contains(player.getName())) {
            if (player.getLocation().getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world")))) {
                e.setCancelled(true);
            }
        }
    }
}