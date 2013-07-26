package com.domsplace.Listeners;

import com.domsplace.DataManagers.MailItemsMailManager;
import com.domsplace.Events.MailItemsGriefEvent;
import com.domsplace.Objects.MailItemBox;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class MailItemsAntiBuildListener extends MailItemsEventBase {
    @EventHandler(ignoreCancelled=true)
    public void onGrief(MailItemsGriefEvent e) {
        if(e.isCancelled()) {
            return;
        }
        
        if(e.getPlayer().hasPermission(OpenOverride)) {
            return;
        }
        
        MailItemBox mailBox = getMailBox(e.getBlock());
        
        if(mailBox == null) {
            return;
        }
        
        if(e.getPlayer().getName().equalsIgnoreCase(mailBox.getPlayer().getName())) {
            return;
        }
        
        msgPlayer(e.getPlayer(), ChatError + "You cannot open this mailbox.");
        e.setCancelled(true);
    }
    
    @EventHandler(ignoreCancelled=true)
    public void blockDoubleChest(BlockPlaceEvent e) {
        if(e.isCancelled()) {
            return;
        }
        
        if(!isChest(e.getBlock())) {
            return;
        }
        
        Block b = e.getBlockPlaced();
        Block p = e.getBlockAgainst();
        
        if(!isMailbox(p)) {
            return;
        }
        
        if(!willMakeDoubleChest(b)) {
            return;
        }
        
        e.setCancelled(ShowFrom);
        e.getPlayer().sendMessage(ChatError + "Can't place a chest next to a mailbox.");
    }
    
    @EventHandler(ignoreCancelled=true)
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.isCancelled()) {
            return;
        }
        
        Player player = e.getPlayer();
        MailItemBox mailBox = getMailBox(e.getBlock());
        if(mailBox == null) {
            return;
        }
        
        MailItemBox.mailBoxes.remove(mailBox);
        player.sendMessage(ChatImportant + "Mailbox broken.");
        MailItemsMailManager.SaveMailBoxes();
    }
}
