package com.pnsdev.betterhoppers.Manager;

import com.pnsdev.betterhoppers.BetterHoppers;
import com.pnsdev.betterhoppers.Constructors.HopperInfo;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class HopperManager {
    @Getter
    private final HashMap<Location, HopperInfo> hoppers = new HashMap<>();

    public HopperManager(BetterHoppers core) {
        //TODO: load from db
        Bukkit.getServer().getScheduler().runTaskTimer(core, () -> {
            if (hoppers.size() == 0) return;
            for (Map.Entry<Location, HopperInfo> entry : hoppers.entrySet()) {
                Location loc = entry.getValue().getDestination();
                if (loc == null || !loc.getWorld().getBlockAt(loc).getType().equals(Material.CHEST)) continue;
                for (Entity entity : entry.getValue().getHopperLocation().getNearbyEntities(10, 10, 10)) {
                    if (!entity.getType().equals(EntityType.DROPPED_ITEM)) continue;
                    Chest chest = (Chest) loc.getBlock().getState();
                    chest.getInventory().addItem(((Item) entity).getItemStack());
                    entity.getWorld().spawnParticle(Particle.SPELL_WITCH, entity.getLocation(), 1, 0, 0, 0, 0);
                    entity.remove();
                }
            }
        }, 0, 4);
    }

    public void addHopper(HopperInfo hopper) {
        hoppers.put(hopper.getHopperLocation(), hopper);
    }

    public void removeHopper(Location hopperId) {
        hoppers.remove(hopperId);
        // TODO: Remove hopper from database
    }
}
