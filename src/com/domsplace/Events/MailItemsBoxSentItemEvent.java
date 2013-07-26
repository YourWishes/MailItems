package com.domsplace.Events;

import com.domsplace.Objects.MailItemBox;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public class MailItemsBoxSentItemEvent extends MailItemsCustomEventBase {
    
    private OfflinePlayer player;
    private ItemStack item;
    private MailItemBox mailBox;
    
    public MailItemsBoxSentItemEvent (OfflinePlayer player, ItemStack item, MailItemBox mailBox) {
        this.player = player;
        this.item = item;
        this.mailBox = mailBox;
    }
    
    public OfflinePlayer getPlayer() {
        return this.player;
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    public MailItemBox getMailBox() {
        return this.mailBox;
    }
}
