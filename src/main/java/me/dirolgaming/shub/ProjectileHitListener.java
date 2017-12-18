package me.dirolgaming.shub;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class ProjectileHitListener
        implements Listener {
    private main plugin;

    public ProjectileHitListener(main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        if ((e.getEntity().getLocation().getWorld().equals(Bukkit.getWorld(plugin.getConfig().getString("world")))) &&
                ((e.getEntity() instanceof Snowball))) {
            Location snowball = e.getEntity().getLocation();
            World world = e.getEntity().getWorld();
            if (plugin.getConfig().getBoolean("enable-sound")) {
                List<String> sounds = plugin.getConfig().getStringList("sounds");
                sounds.forEach(soundString -> {
                    String[] soundSplitted = soundString.split(":");
                    Sound sound = Sound.valueOf(soundSplitted[0]);
                    int speed = Integer.parseInt(soundSplitted[1]);
                    int pitch = Integer.parseInt(soundSplitted[2]);
                    world.playSound(snowball, sound, speed, pitch);
                });
            }
            if(plugin.getConfig().getBoolean("enable-effect")) {
                List<String> effects = plugin.getConfig().getStringList("effects");
                effects.forEach(effectString -> {
                    String[] effectSplitted = effectString.split(":");
                    Effect effect = Effect.valueOf(effectSplitted[0]);
                    int radius = Integer.parseInt(effectSplitted[1]);
                    world.playEffect(snowball, effect, radius);
                });
            }

        }
    }
}