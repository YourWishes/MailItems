package com.domsplace.Commands;

import com.domsplace.MailItemsBase;
import static com.domsplace.MailItemsBase.getOfflinePlayer;
import com.domsplace.Objects.MailItemBox;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class MailItemsCommandAddItem extends MailItemsBase implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("additem")) {
            if(args.length < 1) {
                sender.sendMessage(ChatDefault + "Add an item to a mailbox.");
                return false;
            }
            
            if(args.length < 2) {
                sender.sendMessage(ChatError + "Please enter an item ID.");
                return true;
            }
            
            List<MailItemBox> mailBoxes = new ArrayList<MailItemBox>();
            
            if(args[0].equalsIgnoreCase("all") && sender.hasPermission("MailItems.additem.all")) {
            } else {
                OfflinePlayer target = getOfflinePlayer(args[0], sender);
            
                if(target == null) {
                    sender.sendMessage(ChatError + args[0] + " isn't online.");
                    return true;
                }
                
                MailItemBox mailBox = getMailBox(target);
                if(mailBox == null) {
                    sender.sendMessage(ChatError + target.getName() + " doesn't have a mailbox.");
                    return true;
                }
                
                mailBoxes.add(mailBox);
            }
            
            if(mailBoxes.size() != 1) {
                mailBoxes = MailItemBox.mailBoxes;
            }
            
            int amount = 64;
            byte data = -1;
            int id = 0;
            
            String[] s = args[1].split(":");
            try {
                id = Integer.parseInt(s[0]);
                if(id < 0) {
                    sender.sendMessage(ChatError + "ID Must be one or more.");
                    return false;
                }
            } catch(NumberFormatException ex) {
                sender.sendMessage(ChatError + "ID Must be a number.");
                return false;
            }
            
            if(s.length > 1) {
                try {
                    data = Byte.parseByte(s[1]);
                    if(data < 0) {
                        sender.sendMessage(ChatError + "Data must be at least 0.");
                        return false;
                    }
                } catch(NumberFormatException ex) {
                    sender.sendMessage(ChatError + "Data must be a number!");
                    return false;
                }
            }
            
            if(args.length >= 3) {
                try {
                    amount = Integer.parseInt(args[2]);
                    if(amount < 1) {
                        sender.sendMessage(ChatError + "Amount must be at least 1.");
                        return false;
                    }
                    if(amount > 64) {
                        amount = 64;
                    }
                } catch(NumberFormatException ex) {
                    sender.sendMessage(ChatError + "Amount must be a number.");
                    return false;
                }
            }
            
            ItemStack sendItem = new ItemStack(id, amount);
            MaterialData md = sendItem.getData();
            md.setData(data);
            sendItem.setData(md);
            sendItem.setDurability(data);
                
            if(ShowFrom) {
                List<String> lores = sendItem.getItemMeta().getLore();
                if(lores == null) {
                    lores = new ArrayList<String>();
                }

                lores.add(ChatDefault + "Mailed from " + ChatImportant + sender.getName() + ChatDefault + ".");
                ItemMeta im = sendItem.getItemMeta();
                im.setLore(lores);

                sendItem.setItemMeta(im);
            }
            
            for(MailItemBox mailBox : mailBoxes) {
                if(mailBox.isFull()) {
                    continue;
                }
                
                OfflinePlayer target = mailBox.getPlayer();
                mailBox.addItem(sendItem);
            
                if(target.isOnline()) {
                    target.getPlayer().sendMessage(ChatImportant + sender.getName() + ChatDefault + " added something to your mailbox!");
                }
            }
            
            sender.sendMessage(ChatDefault + "Added items.");
            return true;
        }
        return false;
    }
}
