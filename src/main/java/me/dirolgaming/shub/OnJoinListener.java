package me.dirolgaming.shub;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static org.bukkit.event.EventPriority.MONITOR;


public class OnJoinListener implements Listener {
    private final main plugin;
    public OnJoinListener(main plugin){
        this.plugin= plugin;
    }

    @EventHandler(priority = MONITOR, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = Bukkit.getWorld(plugin.getConfig().getString("world.name"));
        TextComponent motd1 = new TextComponent(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("motd.motd")));
        // Join message
        if(plugin.getConfig().getBoolean("enable-join-quit-messages")) {
            event.setJoinMessage(plugin.getConfig().getString("join-message")
                    .replaceAll("&", "§")
                    .replace("%player%", player.getName())
            );

        }
        // MOTD
        if (plugin.getConfig().getBoolean("motd.enable")) {
            if (plugin.getConfig().getBoolean("motd.enable-hover")) {
            motd1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder (ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("motd.motd-hover"))).create()));
            }
            if (plugin.getConfig().getBoolean("motd.enable-click")) {
                motd1.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, plugin.getConfig().getString("motd.click-url")));
            }
            player.spigot().sendMessage(motd1);
        }
        // Join teleport
        if (plugin.getConfig().getBoolean("teleport-on-join")) {
            // Get TP location
            Integer yaw = plugin.getConfig().getInt("world.Yaw");
            Integer pitch = plugin.getConfig().getInt("world.pitch");
            Location loc = new Location(Bukkit.getWorld(plugin.getConfig().getString("world.name")),
                    plugin.getConfig().getDouble("world.X"),
                    plugin.getConfig().getDouble("world.Y"),
                    plugin.getConfig().getDouble("world.Z"),
                    yaw,
                    pitch);
            // Default location if config is empty
            Location dloc = Bukkit.getWorld(plugin.getConfig().getString("world.name")).getSpawnLocation();
            // Determine if set location is not empty and is safe
            Block feet = loc.getBlock();
            Block head = feet.getRelative(BlockFace.UP);
            if (plugin.getConfig().getString("world.usespawnpointinstead").equals("true"))  {
                plugin.getLogger().warning("Hub is not set or is not safe for players, using world spawnpoint instead");
                player.teleport(dloc);
            }
            else {
                plugin.getLogger().info("Location is safe");
                player.teleport(loc);
            }
        }
        // Join title & ActionBar
        if (Bukkit.getWorld(plugin.getConfig().getString("world.name")).getTime() != plugin.getConfig().getInt("time") && plugin.getConfig().getBoolean("lock-time")) {
            Bukkit.getWorld(plugin.getConfig().getString("world.name")).setTime(plugin.getConfig().getInt("time"));
            }
            // Hunger
        if(plugin.getConfig().getBoolean("disable-hunger")) {
            if (player.getLocation().getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world.name")))) {
                player.setFoodLevel(20);
            }
        }
        // Clean inventory on join
        if (plugin.getConfig().getBoolean("clear-on-join") && (player.getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world.name"))))) {
            player.getInventory().clear();
            plugin.getLogger().info("SHub - Player inventory cleared on join");
        }
        // Item spawner
        if (player.getLocation().getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world.name")))) {
            if (event.getPlayer().hasPermission("safehub.receive")) {
                if (!plugin.getConfig().getString("item-1.item").equals("-")) {
                    ItemStack itm1 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-1.item", "AIR").toUpperCase()), 1);
                    ItemMeta im = itm1.getItemMeta();
                    im.setDisplayName(plugin.getConfig().getString("item-1.name", "").replaceAll("&", "§"));
                    im.setLore(Arrays.asList(plugin.getConfig().getString("item-1.lore", "").replaceAll("&", "§")));
                    itm1.setItemMeta(im);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-1.slot") - 1, itm1);
                    event.getPlayer().updateInventory();
                }
                if (!plugin.getConfig().getString("item-2.item").equals("-")) {
                    ItemStack itm2 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-2.item", "AIR").toUpperCase()), 1);
                    ItemMeta pm = itm2.getItemMeta();
                    pm.setDisplayName(plugin.getConfig().getString("item-2.name", "").replaceAll("&", "§"));
                    pm.setLore(Arrays.asList(plugin.getConfig().getString("item-2.lore", "").replaceAll("&", "§")));
                    itm2.setItemMeta(pm);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-2.slot") - 1, itm2);
                    event.getPlayer().updateInventory();
                }
                if (!plugin.getConfig().getString("item-3.item").equals("-")) {
                    ItemStack itm3 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-3.item", "AIR").toUpperCase()), 1);
                    ItemMeta cm = itm3.getItemMeta();
                    cm.setDisplayName(plugin.getConfig().getString("item-3.name", "").replaceAll("&", "§"));
                    cm.setLore(Arrays.asList(plugin.getConfig().getString("item-3.lore", "").replaceAll("&", "§")));
                    itm3.setItemMeta(cm);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-3.slot") - 1, itm3);
                    event.getPlayer().updateInventory();
                }
                if (!plugin.getConfig().getString("item-4.item").equals("-")) {
                    ItemStack itm4 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-4.item", "AIR").toUpperCase()), 1);
                    ItemMeta om = itm4.getItemMeta();
                    om.setDisplayName(plugin.getConfig().getString("item-4.name", "").replaceAll("&", "§"));
                    om.setLore(Arrays.asList(plugin.getConfig().getString("item-4.lore", "").replaceAll("&", "§")));
                    itm4.setItemMeta(om);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-4.slot") - 1, itm4);
                    event.getPlayer().updateInventory();
                }
                if (!plugin.getConfig().getString("item-5.item").equals("-")) {
                    ItemStack itm5 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-5.item", "AIR").toUpperCase()), 1);
                    ItemMeta um = itm5.getItemMeta();
                    um.setDisplayName(plugin.getConfig().getString("item-5.name", "").replaceAll("&", "§"));
                    um.setLore(Arrays.asList(plugin.getConfig().getString("item-5.lore", "").replaceAll("&", "§")));
                    itm5.setItemMeta(um);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-5.slot") - 1, itm5);
                    event.getPlayer().updateInventory();
                }
                if (!plugin.getConfig().getString("item-6.item").equals("-")) {
                    ItemStack itm6 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-6.item", "AIR").toUpperCase()), 1);
                    ItemMeta ugm = itm6.getItemMeta();
                    ugm.setDisplayName(plugin.getConfig().getString("item-6.name", "").replaceAll("&", "§"));
                    ugm.setLore(Arrays.asList(plugin.getConfig().getString("item-6.lore", "").replaceAll("&", "§")));
                    itm6.setItemMeta(ugm);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-6.slot") - 1, itm6);
                    event.getPlayer().updateInventory();
                }
                if (!plugin.getConfig().getString("item-7.item").equals("-")) {
                    ItemStack itm7 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-7.item", "AIR").toUpperCase()), 1);
                    ItemMeta uum = itm7.getItemMeta();
                    uum.setDisplayName(plugin.getConfig().getString("item-7.name", "").replaceAll("&", "§"));
                    uum.setLore(Arrays.asList(plugin.getConfig().getString("item-7.lore", "").replaceAll("&", "§")));
                    itm7.setItemMeta(uum);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-7.slot") - 1, itm7);
                    event.getPlayer().updateInventory();
                }
                if (!plugin.getConfig().getString("item-8.item").equals("-")) {
                    ItemStack itm8 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-8.item", "AIR").toUpperCase()), 1);
                    ItemMeta ufm = itm8.getItemMeta();
                    ufm.setDisplayName(plugin.getConfig().getString("item-8.name", "").replaceAll("&", "§"));
                    ufm.setLore(Arrays.asList(plugin.getConfig().getString("item-8.lore", "").replaceAll("&", "§")));
                    itm8.setItemMeta(ufm);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-8.slot") - 1, itm8);
                    event.getPlayer().updateInventory();
                }
                if (!plugin.getConfig().getString("item-9.item").equals("-")) {
                    ItemStack itm9 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-9.item", "AIR").toUpperCase()), 1);
                    ItemMeta uhm = itm9.getItemMeta();
                    uhm.setDisplayName(plugin.getConfig().getString("item-9.name", "").replaceAll("&", "§"));
                    uhm.setLore(Arrays.asList(plugin.getConfig().getString("item-9.lore", "").replaceAll("&", "§")));
                    itm9.setItemMeta(uhm);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-9.slot") - 1, itm9);
                    event.getPlayer().updateInventory();
                }
            }
        }
        if (player.hasPermission("safehub.admin")) {
            if (plugin.getConfig().getBoolean("check-update") && plugin.getConfig().getBoolean("notify-update")) {
                plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> plugin.checkUpdate());
            }
        }
    }
}