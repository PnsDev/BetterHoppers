package com.pnsdev.betterhoppers.Constructors;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

@Getter
public class HopperInfo {
    private final OfflinePlayer owner;
    private final UUID hopperId;
    private final Location hopperLocation;
    @Setter
    private int hopperLevel;
    @Setter
    private Location destination;

    public HopperInfo(OfflinePlayer owner, UUID hopperId, Location hopperLocation, int hopperLevel, Location destination) {
        this.owner = owner;
        this.hopperId = hopperId;
        this.hopperLocation = hopperLocation;
        this.hopperLevel = hopperLevel;
        this.destination = destination;
    }
}
