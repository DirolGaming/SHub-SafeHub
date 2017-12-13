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
                String soundscnd = plugin.getConfig().getString("sound-second");
                String soundthrd = plugin.getConfig().getString("sound-third");
                String soundfrth = plugin.getConfig().getString("sound-forth");
                String soundfirst = plugin.getConfig().getString("sound-first");
                // a really retarded way to do this
                // need to change this asap
                if (plugin.getConfig().getString("sound-first").contentEquals("-")) {
                    world.playSound(snowball, soundscnd, 10.0F, 1.0F);
                    world.playSound(snowball, soundthrd, 10.0F, 1.0F);
                    world.playSound(snowball, soundfrth, 10.0F, 1.0F);
                }
                if (plugin.getConfig().getString("sound-second").contentEquals("-")){
                    world.playSound(snowball, soundfirst, 10.0F, 1.0F);
                    world.playSound(snowball, soundthrd, 10.0F, 1.0F);
                    world.playSound(snowball, soundfrth, 10.0F, 1.0F);
                }
                if (plugin.getConfig().getString("sound-third").contentEquals("-")){
                    world.playSound(snowball, soundfirst, 10.0F, 1.0F);
                    world.playSound(snowball, soundscnd, 10.0F, 1.0F);
                    world.playSound(snowball, soundfrth, 10.0F, 1.0F);
                }


            }
            String effectfrst = plugin.getConfig().getString("effect");
            world.playEffect(snowball, Effect.MOBSPAWNER_FLAMES, 5);
            world.playEffect(snowball, Effect.MOBSPAWNER_FLAMES, 5);
            world.playEffect(snowball, Effect.MOBSPAWNER_FLAMES, 5);
            world.playEffect(snowball, Effect.MOBSPAWNER_FLAMES, 5);
            world.playEffect(snowball, Effect.ENDER_SIGNAL, 5);

        }
    }
}