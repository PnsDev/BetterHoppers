package com.pnsdev.betterhoppers;

import com.pnsdev.betterhoppers.Events.HopperActions;
import com.pnsdev.betterhoppers.Manager.HopperManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterHoppers extends JavaPlugin {

    private HopperManager hopperManager;

    @Override
    public void onEnable() { // Plugin startup logic
        Bukkit.getLogger().info("Better Hoppers is now enabled!");
        hopperManager = new HopperManager(this);
        getServer().getPluginManager().registerEvents(new HopperActions(hopperManager), this);

    }

    @Override
    public void onDisable() { // Plugin shutdown logic
        Bukkit.getLogger().info("Better Hoppers is now disabled!");
        hopperManager = null;
    }
}
