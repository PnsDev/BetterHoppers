package com.pnsdev.betterhoppers.Events;

import com.pnsdev.betterhoppers.Constructors.HopperInfo;
import com.pnsdev.betterhoppers.Manager.HopperManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

public class HopperActions implements Listener {

    private final HopperManager hopperManager;

    // Saves state to link hoppers and chests
    private final HashMap<Player, HopperInfo> hopperPlacing = new HashMap<>();

    public HopperActions(HopperManager hopperManager) {
        this.hopperManager = hopperManager;
    }

    /*
     * This method is called when a player places a hopper.
     * In the future it will also save data to the database
     * and will also require a special hopper item.
     */
    @EventHandler (priority = EventPriority.MONITOR)
    public void onHopperPlace(BlockPlaceEvent e) {
        if (e.isCancelled() || !e.getBlock().getType().equals(org.bukkit.Material.HOPPER)) return;
        hopperManager.addHopper(new HopperInfo(e.getPlayer(), UUID.randomUUID(), e.getBlock().getLocation(), 1, null));
    }

    /*
     * This method is called when a player interacts with a hopper
     * or a chest. It's used to link the two together (and will be
     * used in the future for a GUI).
     */
    @EventHandler (priority = EventPriority.MONITOR)
    public void onBlockClick(PlayerInteractEvent e){
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        Block block = e.getClickedBlock();
        switch (block.getType()){
            case HOPPER:
                if (hopperManager.getHoppers().containsKey(block.getLocation())) {
                    hopperPlacing.put(e.getPlayer(), hopperManager.getHoppers().get(block.getLocation()));
                    e.getPlayer().sendMessage("§a§lHopper time!");
                    e.setCancelled(true);
                }
                break;
            case CHEST:
                if (hopperPlacing.containsKey(e.getPlayer())) {
                    hopperManager.getHoppers().get(hopperPlacing.get(e.getPlayer()).getHopperLocation()).setDestination(block.getLocation());
                    hopperPlacing.remove(e.getPlayer());
                    e.getPlayer().sendMessage("§c§lSelected!");
                    e.setCancelled(true);
                }
                break;
        }

    }
}
