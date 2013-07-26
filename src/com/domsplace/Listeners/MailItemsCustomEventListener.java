package com.domsplace.Listeners;

import com.domsplace.Events.MailItemsGriefEvent;
import com.domsplace.Objects.MailItemsGriefType;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MailItemsCustomEventListener extends MailItemsEventBase {
    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent e) {
        
        List<Block> involvedBlocks = new ArrayList<Block>();
        MailItemsGriefEvent event = new MailItemsGriefEvent(e.getPlayer(), e.getClickedBlock(), involvedBlocks, MailItemsGriefType.INTERACT);
        Bukkit.getPluginManager().callEvent(event);
        
        if(event.isCancelled()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        List<Block> involvedBlocks = new ArrayList<Block>();
        MailItemsGriefEvent event = new MailItemsGriefEvent(e.getPlayer(), e.getBlock(), involvedBlocks, MailItemsGriefType.BREAK);
        Bukkit.getPluginManager().callEvent(event);
        
        if(event.isCancelled()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        List<Block> involvedBlocks = new ArrayList<Block>();
        involvedBlocks.add(e.getBlockAgainst());
        MailItemsGriefEvent event = new MailItemsGriefEvent(e.getPlayer(), e.getBlockPlaced(), involvedBlocks, MailItemsGriefType.PLACE);
        Bukkit.getPluginManager().callEvent(event);
        
        if(event.isCancelled()) {
            e.setCancelled(true);
        }
    }
}
