package me.dirolgaming.shub;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
        if (plugin.getConfig().getBoolean("teleport-on-join")) {
            player.teleport(plugin.spawnpoint.getSpawnpoint(Bukkit.getWorld(plugin.getConfig().getString("world"))));
        }
        String title = plugin.getConfig().getString("jointitle.title").replaceAll("&", "§").replaceAll("%player%", player.getName());
        String subtitle = plugin.getConfig().getString("jointitle.subtitle").replaceAll("&", "§").replaceAll("%player%", player.getName());
        int fadein = plugin.getConfig().getInt("jointitle.fadein", 20);
        int fadeout = plugin.getConfig().getInt("jointitle.fadeout", 20);
        int stay = plugin.getConfig().getInt("jointitle.stay", 20);
        int actionbartime = plugin.getConfig().getInt("actionbar.stay", 20);
        if (Bukkit.getWorld(plugin.getConfig().getString("world")).getTime() != plugin.getConfig().getInt("time") && plugin.getConfig().getBoolean("lock-time")) {
            Bukkit.getWorld(plugin.getConfig().getString("world")).setTime(plugin.getConfig().getInt("time"));
            }





        plugin.getServer().getOnlinePlayers().forEach(p->{
            if(p != player) {
                if(plugin.clock.contains(p.getName())) {
                    p.hidePlayer(plugin, player);
                } else {
                    p.showPlayer(plugin, player);
                }
            }
        });
        if(plugin.getConfig().getBoolean("disable-hunger")) {
            if (player.getLocation().getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world")))) {
                player.setFoodLevel(20);
            }
        }

        if(plugin.getConfig().getBoolean("enable-join-quit-messages")) {
            event.setJoinMessage(plugin.getConfig().getString("join-message")
                    .replaceAll("&", "§")
                    .replace("%player%", player.getName())
            );

        }

        if (plugin.getConfig().getBoolean("jointitle.enable")) {
            plugin.title.sendTitle(player, fadein, stay, fadeout, title, subtitle);
        }
        if (plugin.getConfig().getBoolean("actionbar.enable")) {
            String actionbarmsg = plugin.getConfig().getString("actionbar.message").replaceAll("&", "§").replaceAll("%player%", player.getName()).replaceAll("%online%", ""+org.bukkit.Bukkit.getOnlinePlayers().size());
            plugin.actionbar.sendActionbar(player, actionbarmsg, actionbartime);
        }

        if (player.getLocation().getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world")))) {
            if (event.getPlayer().hasPermission("safehub.receive")) {

                if (plugin.getConfig().getBoolean("item-1.enable")) {
                    ItemStack ese1 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-1.item", "AIR").toUpperCase()), 1);
                    ItemMeta im = ese1.getItemMeta();
                    im.setDisplayName(plugin.getConfig().getString("item-1.name", "").replaceAll("&", "§"));
                    im.setLore(Arrays.asList(plugin.getConfig().getString("item-1.lore", "").replaceAll("&", "§")));
                    ese1.setItemMeta(im);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-1.slot") - 1, ese1);
                    event.getPlayer().updateInventory();
                }
                if (plugin.getConfig().getBoolean("item-2.enable")) {
                    ItemStack ese2 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-2.item", "AIR").toUpperCase()), 1);
                    ItemMeta pm = ese2.getItemMeta();
                    pm.setDisplayName(plugin.getConfig().getString("item-2.name", "").replaceAll("&", "§"));
                    pm.setLore(Arrays.asList(plugin.getConfig().getString("item-2.lore", "").replaceAll("&", "§")));
                    ese2.setItemMeta(pm);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-2.slot") - 1, ese2);
                    event.getPlayer().updateInventory();
                }
                if (plugin.getConfig().getBoolean("item-3.enable")) {
                    ItemStack ese3 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-3.item", "AIR").toUpperCase()), 1);
                    ItemMeta cm = ese3.getItemMeta();
                    cm.setDisplayName(plugin.getConfig().getString("item-3.name", "").replaceAll("&", "§"));
                    cm.setLore(Arrays.asList(plugin.getConfig().getString("item-3.lore", "").replaceAll("&", "§")));
                    ese3.setItemMeta(cm);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-3.slot") - 1, ese3);
                    event.getPlayer().updateInventory();
                }
                if (plugin.getConfig().getBoolean("item-4.enable")) {
                    ItemStack ese4 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-4.item", "AIR").toUpperCase()), 1);
                    ItemMeta om = ese4.getItemMeta();
                    om.setDisplayName(plugin.getConfig().getString("item-4.name", "").replaceAll("&", "§"));
                    om.setLore(Arrays.asList(plugin.getConfig().getString("item-4.lore", "").replaceAll("&", "§")));
                    ese4.setItemMeta(om);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-4.slot") - 1, ese4);
                    event.getPlayer().updateInventory();
                }
                if (plugin.getConfig().getBoolean("item-5.enable")) {
                    ItemStack ese5 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-5.item", "AIR").toUpperCase()), 1);
                    ItemMeta um = ese5.getItemMeta();
                    um.setDisplayName(plugin.getConfig().getString("item-5.name", "").replaceAll("&", "§"));
                    um.setLore(Arrays.asList(plugin.getConfig().getString("item-5.lore", "").replaceAll("&", "§")));
                    ese5.setItemMeta(um);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-5.slot") - 1, ese5);
                    event.getPlayer().updateInventory();
                }
                if (plugin.getConfig().getBoolean("item-6.enable")) {
                    ItemStack ese6 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-6.item", "AIR").toUpperCase()), 1);
                    ItemMeta um = ese6.getItemMeta();
                    um.setDisplayName(plugin.getConfig().getString("item-6.name", "").replaceAll("&", "§"));
                    um.setLore(Arrays.asList(plugin.getConfig().getString("item-6.lore", "").replaceAll("&", "§")));
                    ese6.setItemMeta(um);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-6.slot") - 1, ese6);
                    event.getPlayer().updateInventory();
                }
                if (plugin.getConfig().getBoolean("item-7.enable")) {
                    ItemStack ese7 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-7.item", "AIR").toUpperCase()), 1);
                    ItemMeta um = ese7.getItemMeta();
                    um.setDisplayName(plugin.getConfig().getString("item-7.name", "").replaceAll("&", "§"));
                    um.setLore(Arrays.asList(plugin.getConfig().getString("item-7.lore", "").replaceAll("&", "§")));
                    ese7.setItemMeta(um);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-7.slot") - 1, ese7);
                    event.getPlayer().updateInventory();
                }
                if (plugin.getConfig().getBoolean("item-8.enable")) {
                    ItemStack ese8 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-8.item", "AIR").toUpperCase()), 1);
                    ItemMeta um = ese8.getItemMeta();
                    um.setDisplayName(plugin.getConfig().getString("item-8.name", "").replaceAll("&", "§"));
                    um.setLore(Arrays.asList(plugin.getConfig().getString("item-8.lore", "").replaceAll("&", "§")));
                    ese8.setItemMeta(um);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-8.slot") - 1, ese8);
                    event.getPlayer().updateInventory();
                }
                if (plugin.getConfig().getBoolean("item-9.enable")) {
                    ItemStack ese9 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-9.item", "AIR").toUpperCase()), 1);
                    ItemMeta um = ese9.getItemMeta();
                    um.setDisplayName(plugin.getConfig().getString("item-9.name", "").replaceAll("&", "§"));
                    um.setLore(Arrays.asList(plugin.getConfig().getString("item-9.lore", "").replaceAll("&", "§")));
                    ese9.setItemMeta(um);
                    event.getPlayer().getInventory().setItem(plugin.getConfig().getInt("item-9.slot") - 1, ese9);
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