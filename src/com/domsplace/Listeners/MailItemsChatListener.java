package com.domsplace.Listeners;

import com.domsplace.Objects.MailItemBox;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MailItemsChatListener extends MailItemsEventBase {
    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e) {
        if(!RemoveFrom) {
            return;
        }
        
        if(e.getCurrentItem() == null) {
            return;
        }
        
        if(e.getCurrentItem().getItemMeta() == null) {
            return;
        }
        
        if(e.getCurrentItem().getItemMeta().getLore() == null) {
            return;
        }
        
        ItemStack is = e.getCurrentItem();
        ItemMeta im = is.getItemMeta();
        
        List<String> lores = im.getLore();
        List<String> newLores = new ArrayList<String>();
        for(String s : lores) {
            if(s.contains("Mailed from")) {
                continue;
            }
            
            newLores.add(s);
        }
        
        if(newLores.isEmpty()) {
            ItemStack itemStack = new ItemStack(is.getType(), is.getAmount());
            im.setLore(itemStack.getItemMeta().getLore());
        } else {
            im.setLore(newLores);
        }
        is.setItemMeta(im);
        e.setCurrentItem(is);
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        MailItemBox mb = getMailBox(e.getPlayer());
        if(mb == null) {
            return;
        }
        
        if(mb.isEmpty()) {
            return;
        }
        
        e.getPlayer().sendMessage(ChatError + "You have mail!");
    }
}
