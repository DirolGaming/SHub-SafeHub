package me.dirolgaming.shub;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuitListener implements Listener {
    private final main plugin;

    public OnQuitListener(main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getWorld().equals(plugin.getConfig().getString("world")) && (plugin.getConfig().getBoolean("clear-on-quit"))) {
            player.getInventory().clear();
            plugin.getLogger().info("SHub - Player inventory cleared on quit.");
        }
        if (plugin.getConfig().getBoolean("enable-join-quit-messages")) {
            event.setQuitMessage(plugin.getConfig().getString("quit-message")
                    .replaceAll("&", "§").replace("%player%", player.getName()));
        }
        if (plugin.chat.contains(player.getName())) {
            plugin.chat.remove(player.getName());
        }
        if (plugin.clock.contains(player.getName())) {
            plugin.clock.remove(player.getName());
        }
    }
}