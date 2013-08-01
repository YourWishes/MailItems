package com.domsplace.Commands;

import com.domsplace.Events.MailItemsBoxSentItemEvent;
import com.domsplace.MailItemsBase;
import com.domsplace.Objects.MailItemBox;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MailItemsCommandSendItem extends MailItemsBase implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("senditem")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(ChatError + "Only players can send items!");
                return true;
            }
            
            if(args.length < 1) {
                sender.sendMessage(ChatDefault + "Sends the item(s) you're holding to another players mailbox.");
                return false;
            }
            
            Player player = (Player) sender;
            
            if(player.getGameMode().equals(GameMode.CREATIVE) && BlockCreative) {
                sender.sendMessage(ChatError + "Can't send items while in creative mode.");
                return true;
            }
            
            if(args[0].equalsIgnoreCase("all") && sender.hasPermission("MailItems.senditem.all")) {
                ItemStack heldItem = player.getItemInHand();
                if(heldItem == null || heldItem.getType() == null || heldItem.getType().equals(Material.AIR)) {
                    sender.sendMessage(ChatError + "Must be holding something to send.");
                    return true;
                }

                ItemStack sentItem = new ItemStack(heldItem);
                if(ShowFrom) {
                    List<String> lores = heldItem.getItemMeta().getLore();
                    if(lores == null) {
                        lores = new ArrayList<String>();
                    }

                    lores.add(ChatDefault + "Mailed from " + ChatImportant + player.getName() + ChatDefault + ".");
                    ItemMeta im = sentItem.getItemMeta();
                    im.setLore(lores);

                    sentItem.setItemMeta(im);
                }
                
                for(MailItemBox mailBox : MailItemBox.mailBoxes) {
                    if(mailBox == null) {
                        continue;
                    }
                    
                    if(mailBox.isFull()) {
                        continue;
                    }
            
                    /*** Fire the event ***/
                    MailItemsBoxSentItemEvent event = new MailItemsBoxSentItemEvent(Bukkit.getOfflinePlayer(player.getName()), heldItem, mailBox);
                    Bukkit.getPluginManager().callEvent(event);
                    if(event.isCancelled()) {
                        continue;
                    }
                    
            
                    if(mailBox.getPlayer().isOnline()) {
                        mailBox.getPlayer().getPlayer().sendMessage(ChatImportant + sender.getName() + ChatDefault + " sent you something!");
                    }
                    mailBox.addItem(sentItem);
                }
                
                player.getInventory().remove(heldItem);
                
                sender.sendMessage(ChatDefault + "Sent item to all mailboxes.");
                return true;
            }
            
            OfflinePlayer target = getOfflinePlayer(args[0], sender);
            
            if(target == null) {
                sender.sendMessage(ChatError + args[0] + " isn't online.");
                return true;
            }
            
            if(player.getName().equalsIgnoreCase(target.getName())) {
                sender.sendMessage(ChatError + "Can't send an item to yourself.");
                return true;
            }
            
            MailItemBox mailBox = getMailBox(target);
            if(mailBox == null) {
                sender.sendMessage(ChatError + target.getName() + " doesn't have a mailbox.");
                return true;
            }
            
            if(mailBox.isFull()) {
                sender.sendMessage(ChatError + target.getName() + "'s mailbox is full.");
                return true;
            }
            
            ItemStack heldItem = player.getItemInHand();
            if(heldItem == null || heldItem.getType() == null || heldItem.getType().equals(Material.AIR)) {
                sender.sendMessage(ChatError + "Must be holding something to send.");
                return true;
            }
            
            /*** Fire the event ***/
            MailItemsBoxSentItemEvent event = new MailItemsBoxSentItemEvent(Bukkit.getOfflinePlayer(player.getName()), heldItem, mailBox);
            Bukkit.getPluginManager().callEvent(event);
            if(event.isCancelled()) {
                return true;
            }
            
            ItemStack sentItem = new ItemStack(heldItem);
            
            if(ShowFrom) {
                List<String> lores = heldItem.getItemMeta().getLore();
                if(lores == null) {
                    lores = new ArrayList<String>();
                }
                
                lores.add(ChatDefault + "Mailed from " + ChatImportant + player.getName() + ChatDefault + ".");
                ItemMeta im = sentItem.getItemMeta();
                im.setLore(lores);
                
                sentItem.setItemMeta(im);
            }
            
            player.getInventory().remove(heldItem);
            mailBox.addItem(sentItem);
            
            if(target.isOnline()) {
                target.getPlayer().sendMessage(ChatImportant + sender.getName() + ChatDefault + " sent you something!");
            }
            
            sender.sendMessage(ChatDefault + "Sent item to " + ChatImportant + target.getName() + ChatDefault + ".");
            return true;
        }
        return false;
    }
}
