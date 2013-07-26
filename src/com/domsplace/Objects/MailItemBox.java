package com.domsplace.Objects;

import com.domsplace.DataManagers.MailItemsMailManager;
import com.domsplace.MailItemsBase;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MailItemBox extends MailItemsBase {
    /*** Static Methods ***/
    public static final List<MailItemBox> mailBoxes = new ArrayList<MailItemBox>();
    
    /*** Instance ***/
    private Chest chest;
    private OfflinePlayer player;
    
    public MailItemBox(Chest chest, OfflinePlayer player) {
        if(!MailItemBox.mailBoxes.add(this)) {
            msgConsole("Failed to add Chest to list. NPE");
        }
        
        this.chest = chest;
        this.player = player;
    }
    
    public OfflinePlayer getPlayer() {
        return this.player;
    }
    
    public Chest getChest() {
        return this.chest;
    }
    
    public boolean isEmpty() {
        for(ItemStack is : this.getChest().getInventory().getContents()) {
            if(is == null) {
                continue;
            }
            
            if(is.getType() == null) {
                continue;
            }
            
            if(is.getType().equals(Material.AIR)) {
                continue;
            }
            
            return true;
        }
        return false;
    }

    public boolean isFull() {
        int items = 0;
        for(ItemStack is : this.getChest().getBlockInventory().getContents()) {
            if(is == null) {
                continue;
            }
            
            if(is.getType() == null) {
                continue;
            }
            
            if(is.getType().equals(Material.AIR)) {
                continue;
            }
            
            items++;
        }
        
        if(items >= this.getChest().getInventory().getSize()) {
            return true;
        }
        
        return false;
    }
    
    public void addItem(ItemStack item) {
        Inventory s = this.getChest().getBlockInventory();
        s.addItem(item);
    }
}
