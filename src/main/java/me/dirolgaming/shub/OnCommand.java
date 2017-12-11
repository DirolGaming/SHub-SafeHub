package me.dirolgaming.shub;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OnCommand implements CommandExecutor {
    private final main plugin;
    public OnCommand(main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sethub")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("safehub.sethub")) {
                    if (player.getLocation().getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world")))) {
                        plugin.spawnpoint.setSpawnpoint(Bukkit.getWorld(plugin.getConfig().getString("world")), player.getLocation());
                        player.sendMessage(plugin.getConfig().getString("Set-hub").replaceAll("&", "§"));
                        return true;
                    }
                    else if (!player.getLocation().getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world")))) {
                        player.sendMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + "your world does not match the world in config.yml");
                    }
                    return true;
                }
                else {
                    if (!sender.hasPermission("safehub.sethub")) {
                        sender.sendMessage(plugin.getConfig().getString("No-Permission").replaceAll("&", "§"));
                        return true;
                    }
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("hub")){
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (sender.hasPermission("safehub.hub")) {
                    player.teleport(plugin.spawnpoint.getSpawnpoint(Bukkit.getWorld(plugin.getConfig().getString("world"))));
                    player.sendMessage(plugin.getConfig().getString("On-teleport-to-spawn").replaceAll("&", "§"));
                    return true;
                }
                else {
                    if (!sender.hasPermission("safehub.hub")){
                        sender.sendMessage(plugin.getConfig().getString("No-Permission").replaceAll("&", "§"));
                        return true;
                    }
                }
            }
        }
        if(cmd.getName().equalsIgnoreCase("shub")) {
            if(args.length == 0) {
                sender.sendMessage(ChatColor.GRAY + "Author: " + ChatColor.RED + "DirolGaming");
                sender.sendMessage(ChatColor.GRAY + "Version: " + ChatColor.RED + plugin.getDescription().getVersion());
                sender.sendMessage(ChatColor.GRAY + "/shub reload - " + ChatColor.RED + "reload the plugin");
                sender.sendMessage(ChatColor.GRAY + "/hub - " + ChatColor.RED + "teleport to hub");
                sender.sendMessage(ChatColor.GRAY + "/sethub - " + ChatColor.RED + "set hub");
                sender.sendMessage("&7/shub update - &cenable/disable update notifications on join".replaceAll("&", "§"));
                return true;
            } else if(args[0].equalsIgnoreCase("reload")) {
                if(sender.hasPermission("safehub.reload")) {
                    sender.sendMessage(plugin.getConfig().getString("Reload-Message").replaceAll("&", "§"));
                    plugin.reloadConfig();
                    return true;
                }
                if (!sender.hasPermission("safehub.reload")) {
                    sender.sendMessage(plugin.getConfig().getString("No-Permission").replaceAll("&", "§"));
                    return true;
                }
            } else if (sender.hasPermission("safehub.admin")) {
                if (args[0].equalsIgnoreCase("update")) {
                    if (plugin.getConfig().getBoolean("notify-update")) {
                        plugin.getConfig().set("notify-update", false);
                        plugin.saveConfig();
                        plugin.reloadConfig();
                        sender.sendMessage(plugin.getConfig().getString("disable-notifications").replaceAll("&", "§"));
                        return true;
                    }
                    if (!plugin.getConfig().getBoolean("notify-update")) {
                        plugin.getConfig().set("notify-update", true);
                        plugin.saveConfig();
                        plugin.reloadConfig();
                        sender.sendMessage(plugin.getConfig().getString("enable-notifications").replaceAll("&", "§"));
                        return true;
                    }
                    else {
                        sender.sendMessage(plugin.getConfig().getString("No-Permission").replaceAll("&", "§"));
                    }
                }
            } else {
                sender.sendMessage(plugin.getConfig().getString("Unknown-Command").replaceAll("&", "§"));
                return true;
            }
        }
        return true;
    }
}