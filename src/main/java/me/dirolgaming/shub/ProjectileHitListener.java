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

public class ProjectileHitListener
        implements Listener
{
    private main plugin;

    public ProjectileHitListener(main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e)
    {
        if ((e.getEntity().getLocation().getWorld().equals(Bukkit.getWorld(this.plugin.getConfig().getString("world")))) &&
                ((e.getEntity() instanceof Snowball)))
        {
            Location snowball = e.getEntity().getLocation();
            World world = e.getEntity().getWorld();
            world.playEffect(snowball, Effect.MOBSPAWNER_FLAMES, 5);
            world.playEffect(snowball, Effect.MOBSPAWNER_FLAMES, 5);
            world.playEffect(snowball, Effect.MOBSPAWNER_FLAMES, 5);
            world.playEffect(snowball, Effect.MOBSPAWNER_FLAMES, 5);
            world.playEffect(snowball, Effect.ENDER_SIGNAL, 5);
            world.playSound(snowball, Sound.ENTITY_CAT_AMBIENT, 10.0F, 1.0F);
            world.playSound(snowball, Sound.ENTITY_WOLF_AMBIENT, 10.0F, 1.0F);
            world.playSound(snowball, Sound.ENTITY_ENDERDRAGON_FLAP, 10.0F, 1.0F);
            world.playSound(snowball, Sound.BLOCK_WATER_AMBIENT, 10.0F, 1.0F);
        }
    }
}