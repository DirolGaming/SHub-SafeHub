package me.dirolgaming.shub;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class   OnCommand implements CommandExecutor {
    private final main plugin;
    public OnCommand(main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sethub")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                World world = player.getLocation().getWorld();
                Location loc = player.getLocation();
                if (player.hasPermission("safehub.sethub") || (player.hasPermission("safehub.admin"))) {
                    if (player.getLocation().getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world.name")))) {
                        plugin.getConfig().set("world.X", loc.getBlockX());
                        plugin.getConfig().set("world.Y", loc.getBlockY());
                        plugin.getConfig().set("world.Z", loc.getBlockZ());
                        plugin.getConfig().set("world.Yaw", loc.getYaw());
                        plugin.getConfig().set("world.Pitch", loc.getPitch());
                        plugin.getConfig().set("world.name", world.getName());
                        plugin.saveConfig();
                        plugin.reloadConfig();
                        player.sendMessage(plugin.getConfig().getString("set-hub").replaceAll("&", "§"));
                        return true;
                    }
                    else if (!player.getLocation().getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world.name")))) {
                        player.sendMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + "your world does not match the world in config.yml");
                    }
                    return true;
                }
                else {
                    if (!sender.hasPermission("safehub.sethub")) {
                        sender.sendMessage(plugin.getConfig().getString("no-permission").replaceAll("&", "§"));
                        return true;
                    }
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("hub")){
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Integer yaw = plugin.getConfig().getInt("world.Yaw");
                Integer pitch = plugin.getConfig().getInt("world.pitch");
                Location loc = new Location(Bukkit.getWorld(plugin.getConfig().getString("world.name")),
                        plugin.getConfig().getDouble("world.X"),
                        plugin.getConfig().getDouble("world.Y"),
                        plugin.getConfig().getDouble("world.Z"),
                        yaw,
                        pitch);

                if (sender.hasPermission("safehub.hub")) {
                    player.teleport(loc);
                    player.sendMessage(plugin.getConfig().getString("on-teleport-to-spawn").replaceAll("&", "§"));
                    return true;
                }
                else {
                    if (!sender.hasPermission("safehub.hub")){
                        sender.sendMessage(plugin.getConfig().getString("no-permission").replaceAll("&", "§"));
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
                    sender.sendMessage(plugin.getConfig().getString("reload-message").replaceAll("&", "§"));
                    plugin.reloadConfig();
                    return true;
                }
                if (!sender.hasPermission("safehub.reload")) {
                    sender.sendMessage(plugin.getConfig().getString("no-permission").replaceAll("&", "§"));
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
                        sender.sendMessage(plugin.getConfig().getString("no-permission").replaceAll("&", "§"));
                    }
                }
            } else {
                sender.sendMessage(plugin.getConfig().getString("unknown-command").replaceAll("&", "§"));
                return true;
            }
        }
        return true;
    }
}