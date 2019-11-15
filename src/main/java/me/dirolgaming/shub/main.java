package me.dirolgaming.shub;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class main extends JavaPlugin implements Listener {
    HashMap<Player, Integer> cooldownTime = new HashMap();
    HashMap<Player, BukkitRunnable> cooldownTask = new HashMap();
    HashMap<Player, Integer> doublejumpcooldownTime = new HashMap();
    HashMap<Player, BukkitRunnable> doublejumpcooldownTask = new HashMap();
    ArrayList<String> chat;
    ArrayList<String> clock;
    public void onEnable() {
        // Register classes & commands
        OnCommand onCommand = new OnCommand(this);
        getCommand("shub").setExecutor(onCommand);
        getCommand("hub").setExecutor(onCommand);
        getCommand("sethub").setExecutor(onCommand);
        getServer().getPluginManager().registerEvents(new OnJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        getServer().getPluginManager().registerEvents(new DamageEventListener(this), this);
        getServer().getPluginManager().registerEvents(new HungerDepleteListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new ItemDropListener(this), this);
        getServer().getPluginManager().registerEvents(new OnQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new ProjectileHitListener(this), this);
        getServer().getPluginManager().registerEvents(new OnFlightListener(this), this);
        getServer().getPluginManager().registerEvents(new OnClickListener(this), this);
        getServer().getPluginManager().registerEvents(new MoveListener(this), this);
        getServer().getPluginManager().registerEvents(new WorldChangeListener(this), this);
        getServer().getPluginManager().registerEvents(new WeatherChangeListener(this), this);
        getLogger().info("SafeHub " + getDescription().getVersion() + " has been activated.");
        saveDefaultConfig();
        this.clock = new ArrayList();
        this.chat = new ArrayList();
        // Add Metrics
        getServer().getScheduler().runTaskAsynchronously(this, () -> new MetricsLite(this));
        getServer().getScheduler().runTaskAsynchronously(this, () -> checkUpdate());

        if (!getConfig().getString("config-ver").equals(getDescription().getVersion())) {
            File file1 = new File(getDataFolder(), "config.yml");
            File file2 = new File(getDataFolder(), "old_config.yml");
            if (file2.exists()) {
                getLogger().severe("Old config already exists, removed old config.");
                file2.delete();
                file1.renameTo(new File(getDataFolder(),"old_config.yml"));
                YamlConfiguration.loadConfiguration(file1);
                saveDefaultConfig();
                reloadConfig();
            }
            else if (!file2.exists()){
                file1.renameTo(new File(getDataFolder(),"old_config.yml"));
                YamlConfiguration.loadConfiguration(file1);
                saveDefaultConfig();
                reloadConfig();
                getLogger().severe("Your SafeHub config is outdated, your current config has been renamed to old_config.yml");
            }
            else {
                saveDefaultConfig();
            }
        }

    }
    public void checkUpdate()
    {
        if (getConfig().getBoolean("Check-Update")) {
            try
            {
                HttpURLConnection con = (HttpURLConnection)new URL("http://www.spigotmc.org/api/general.php").openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.getOutputStream().write("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=29284".getBytes("UTF-8"));
                String version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
                con.disconnect();
                if (!version.equals(getDescription().getVersion())) {
                    getServer().getLogger().warning( "An update for SafeHub is available, new version: " + version + ChatColor.GREEN + "| Your version: " +
                            getDescription().getVersion());
                }
            }
            catch (Exception localException) {}
        }
    }
}